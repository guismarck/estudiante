package app.estudiante.servicio.InterfacesServicios;

import java.util.List;

import app.estudiante.utils.NivelEducativoRequestDTO;
import app.estudiante.utils.NivelEducativoResponseDTO;

public interface INivelEducativoServicio {
    List<NivelEducativoResponseDTO> listarTodos();
    NivelEducativoResponseDTO obtenerPorId(Integer id);
    NivelEducativoResponseDTO crear(NivelEducativoRequestDTO dto);
    NivelEducativoResponseDTO actualizar(Integer id, NivelEducativoRequestDTO dto);
    void eliminar(Integer id);
    byte[] generarReporteJasper(Integer idNivel);
}
