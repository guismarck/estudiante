package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.utils.PeriodoEvaluativoRequestDTO;
import app.estudiante.utils.PeriodoEvaluativoResponseDTO;

import java.util.List;

public interface IPeriodoEvaluativoServicio {
    
    List<PeriodoEvaluativoResponseDTO> listarPeriodosEvaluativos();

    PeriodoEvaluativoResponseDTO buscarPeriodoEvaluativoPorId(Integer id);

    PeriodoEvaluativoResponseDTO crearPeriodoEvaluativo(PeriodoEvaluativoRequestDTO dto);

    PeriodoEvaluativoResponseDTO actualizarPeriodoEvaluativo(Integer id, PeriodoEvaluativoRequestDTO dto);

    void eliminarPeriodoEvaluativo(Integer id);

}
