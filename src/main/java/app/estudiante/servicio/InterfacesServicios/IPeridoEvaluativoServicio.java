package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.utils.PeriodoEvaluativoRequestDTO;
import app.estudiante.utils.PeriodoEvaluativoResponseDTO;

import java.util.List;

public interface IPeridoEvaluativoServicio {
   List<PeriodoEvaluativoResponseDTO> listarPeriodosEvaluativos();

    PeriodoEvaluativoResponseDTO buscarPeriodoEvaluativoPorId(Integer idPeriodoEvaluativo);

    PeriodoEvaluativoResponseDTO crearPeriodoEvaluativo(PeriodoEvaluativoRequestDTO dto);

    PeriodoEvaluativoResponseDTO actualizarPeriodoEvaluativo(Integer idPeriodoEvaluativo, PeriodoEvaluativoRequestDTO dto);

    void eliminarPeriodoEvaluativo(Integer idPeriodoEvaluativo);

}
