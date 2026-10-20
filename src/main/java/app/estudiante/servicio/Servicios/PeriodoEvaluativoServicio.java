package app.estudiante.servicio.Servicios;


import app.estudiante.exception.recursosNoEncontradoException;
import app.estudiante.modelo.PeriodoEvaluativo;
import app.estudiante.repositorio.PeriodoEvaluativoRepositorio;
import app.estudiante.servicio.InterfacesServicios.IPeriodoEvaluativoServicio;
import app.estudiante.utils.PeriodoEvaluativoRequestDTO;
import app.estudiante.utils.PeriodoEvaluativoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeriodoEvaluativoServicio implements IPeriodoEvaluativoServicio {

    private final PeriodoEvaluativoRepositorio periodoEvaluativoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PeriodoEvaluativoResponseDTO> listarPeriodosEvaluativos() {
        return periodoEvaluativoRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PeriodoEvaluativoResponseDTO buscarPeriodoEvaluativoPorId(Integer idPeriodoEvaluativo) {
        PeriodoEvaluativo periodo = periodoEvaluativoRepository.findById(idPeriodoEvaluativo)
                .orElseThrow(() -> new recursosNoEncontradoException(
                        "No se encontró el período evaluativo con ID: " + idPeriodoEvaluativo));

        return mapToResponseDTO(periodo);
    }

    @Override
    @Transactional
    public PeriodoEvaluativoResponseDTO crearPeriodoEvaluativo(PeriodoEvaluativoRequestDTO dto) {
        PeriodoEvaluativo periodo = mapToEntity(dto);
        periodo.setCreadoPor(dto.getUsuarioAuditoria());
        
        PeriodoEvaluativo guardado = periodoEvaluativoRepository.save(periodo);
        return mapToResponseDTO(guardado);
    }

    @Override
    @Transactional
    public PeriodoEvaluativoResponseDTO actualizarPeriodoEvaluativo(Integer idPeriodoEvaluativo, PeriodoEvaluativoRequestDTO dto) {
        PeriodoEvaluativo periodoExistente = periodoEvaluativoRepository.findById(idPeriodoEvaluativo)
                .orElseThrow(() -> new recursosNoEncontradoException(
                        "No se puede actualizar. No existe el período evaluativo con ID: " + idPeriodoEvaluativo));

        periodoExistente.setNombrePeriodo(dto.getNombrePeriodo());
        periodoExistente.setNumeroPeriodo(dto.getNumeroPeriodo());
        periodoExistente.setAnioEscolar(dto.getAnioEscolar());
        periodoExistente.setFechaInicio(dto.getFechaInicio());
        periodoExistente.setFechaFin(dto.getFechaFin());
        periodoExistente.setFechaCierreDocente(dto.getFechaCierreDocente());
        periodoExistente.setEstado(dto.getEstado());
        periodoExistente.setActualizadoPor(dto.getUsuarioAuditoria());

        PeriodoEvaluativo actualizado = periodoEvaluativoRepository.save(periodoExistente);
        return mapToResponseDTO(actualizado);
    }

    @Override
    @Transactional
    public void eliminarPeriodoEvaluativo(Integer idPeriodoEvaluativo) {
        if (!periodoEvaluativoRepository.existsById(idPeriodoEvaluativo)) {
            throw new recursosNoEncontradoException(
                    "No se puede eliminar. No existe el período evaluativo con ID: " + idPeriodoEvaluativo);
        }
        periodoEvaluativoRepository.deleteById(idPeriodoEvaluativo);
    }

    // --- Métodos de Mapeo Interno ---

    private PeriodoEvaluativoResponseDTO mapToResponseDTO(PeriodoEvaluativo entity) {
        return PeriodoEvaluativoResponseDTO.builder()
                .idPeriodoEvaluativo(entity.getIdPeriodoEvaluativo())
                .nombrePeriodo(entity.getNombrePeriodo())
                .numeroPeriodo(entity.getNumeroPeriodo())
                .anioEscolar(entity.getAnioEscolar())
                .fechaInicio(entity.getFechaInicio())
                .fechaFin(entity.getFechaFin())
                .fechaCierreDocente(entity.getFechaCierreDocente())
                .estado(entity.getEstado())
                .creadoPor(entity.getCreadoPor())
                .creadoEl(entity.getCreadoEl())
                .actualizadoPor(entity.getActualizadoPor())
                .actualizadoEl(entity.getActualizadoEl())
                .build();
    }

    private PeriodoEvaluativo mapToEntity(PeriodoEvaluativoRequestDTO dto) {
        return PeriodoEvaluativo.builder()
                .nombrePeriodo(dto.getNombrePeriodo())
                .numeroPeriodo(dto.getNumeroPeriodo())
                .anioEscolar(dto.getAnioEscolar())
                .fechaInicio(dto.getFechaInicio())
                .fechaFin(dto.getFechaFin())
                .fechaCierreDocente(dto.getFechaCierreDocente())
                .estado(dto.getEstado() != null ? dto.getEstado() : true)
                .build();
    }
}
