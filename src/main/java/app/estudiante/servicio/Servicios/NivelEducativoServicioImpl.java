package app.estudiante.servicio.Servicios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.estudiante.modelo.NivelEducativo;
import app.estudiante.repositorio.NivelEducativoRepositorio;
import app.estudiante.servicio.InterfacesServicios.INivelEducativoServicio;
import app.estudiante.utils.NivelEducativoRequestDTO;
import app.estudiante.utils.NivelEducativoResponseDTO;
import jakarta.transaction.Transactional;

@Service 
public class NivelEducativoServicioImpl implements INivelEducativoServicio{
 @Autowired   
 private NivelEducativoRepositorio repositorio;

    @Override
    @Transactional()
    public List<NivelEducativoResponseDTO> listarTodos() {
        return repositorio.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional()
    public NivelEducativoResponseDTO obtenerPorId(Integer id) {
        NivelEducativo nivel = repositorio.findById(id)
                .orElse(null);
        return mapToDTO(nivel);
    }

    @Override
    @Transactional
    public NivelEducativoResponseDTO crear(NivelEducativoRequestDTO dto) {
        NivelEducativo entidad = NivelEducativo.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .build();
        
        if (dto.getUsuario() != null) {
            entidad.setCreadoPor(dto.getUsuario());
        }

        return mapToDTO(repositorio.save(entidad));
    }

    @Override
    @Transactional
    public NivelEducativoResponseDTO actualizar(Integer id, NivelEducativoRequestDTO dto) {
        NivelEducativo nivel = repositorio.findById(id)
                .orElse(null);

        nivel.setNombre(dto.getNombre());
        nivel.setDescripcion(dto.getDescripcion());
        if (dto.getUsuario() != null) {
            nivel.setActualizadoPor(dto.getUsuario());
        }

        return mapToDTO(repositorio.save(nivel));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        if (!repositorio.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Nivel educativo no encontrado con ID: " + id);
        }
        repositorio.deleteById(id);
    }

    @Override
    @Transactional()
    public byte[] generarReporteJasper(Integer idNivel) {
        obtenerPorId(idNivel); // Valida existencia previa
        
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ID_NIVEL", idNivel);
        
        // Integración con el motor de JasperReports / exportador a PDF
        return new byte[0]; 
    }

    private NivelEducativoResponseDTO mapToDTO(NivelEducativo entidad) {
        return NivelEducativoResponseDTO.builder()
                .idnivel(entidad.getIdnivel())
                .nombre(entidad.getNombre())
                .descripcion(entidad.getDescripcion())
                .creadoPor(entidad.getCreadoPor())
                .creadoEl(entidad.getCreadoEl())
                .actualizadoPor(entidad.getActualizadoPor())
                .actualizadoEl(entidad.getActualizadoEl())
                .build();
    }
}
