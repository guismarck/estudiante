package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.PeriodoEvaluativo;
import app.estudiante.repositorio.PeriodoEvaluativoRepositorio;
import app.estudiante.servicio.InterfacesServicios.IPeridoEvaluativoServicio;
import app.estudiante.utils.PeriodoEvaluativoRequestDTO;
import app.estudiante.utils.PeriodoEvaluativoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
@Service
public class PeriodoEvaluativoServicio implements IPeridoEvaluativoServicio {
  @Autowired
  private PeriodoEvaluativoRepositorio periodoEvaluativoRepositorio;
   @Override
    @Transactional(readOnly = true)
    public List<PeriodoEvaluativoResponseDTO> listarPeriodosEvaluativos() {
       return periodoEvaluativoRepositorio.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PeriodoEvaluativoResponseDTO buscarPeriodoEvaluativoPorId(Integer idPeriodoEvaluativo) {
        PeriodoEvaluativo periodo = periodoEvaluativoRepositorio.findById(idPeriodoEvaluativo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se encontró el período evaluativo con ID: " + idPeriodoEvaluativo));

        return mapToResponseDTO(periodo);
    }

    @Override
    @Transactional
    public PeriodoEvaluativoResponseDTO crearPeriodoEvaluativo(PeriodoEvaluativoRequestDTO dto) {
        PeriodoEvaluativo periodo = mapToEntity(dto);
        periodo.setCreadoPor(dto.getUsuarioAuditoria());

        PeriodoEvaluativo guardado = periodoEvaluativoRepositorio.save(periodo);
        return mapToResponseDTO(guardado);
    }

    @Override
    @Transactional
    public PeriodoEvaluativoResponseDTO actualizarPeriodoEvaluativo(Integer idPeriodoEvaluativo, PeriodoEvaluativoRequestDTO dto) {
        PeriodoEvaluativo periodoExistente = periodoEvaluativoRepositorio.findById(idPeriodoEvaluativo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se puede actualizar. No existe el período evaluativo con ID: " + idPeriodoEvaluativo));

        periodoExistente.setNombrePeriodo(dto.getNombrePeriodo());
        periodoExistente.setNumeroPeriodo(dto.getNumeroPeriodo());
        periodoExistente.setAnioEscolar(dto.getAnioEscolar());
        periodoExistente.setFechaInicio(dto.getFechaInicio());
        periodoExistente.setFechaFin(dto.getFechaFin());
        periodoExistente.setFechaCierreDocente(dto.getFechaCierreDocente());
        periodoExistente.setEstado(dto.getEstado());
        periodoExistente.setActualizadoPor(dto.getUsuarioAuditoria());

        PeriodoEvaluativo actualizado = periodoEvaluativoRepositorio.save(periodoExistente);
        return mapToResponseDTO(actualizado);
    }

    @Override
    @Transactional
    public void eliminarPeriodoEvaluativo(Integer idPeriodoEvaluativo) {
        if (!periodoEvaluativoRepositorio.existsById(idPeriodoEvaluativo)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No se puede eliminar. No existe el período evaluativo con ID: " + idPeriodoEvaluativo);
        }
        periodoEvaluativoRepositorio.deleteById(idPeriodoEvaluativo);
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
