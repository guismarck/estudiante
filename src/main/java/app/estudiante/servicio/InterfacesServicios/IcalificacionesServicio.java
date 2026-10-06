package app.estudiante.servicio.InterfacesServicios;

import java.util.List;

import app.estudiante.utils.CalificacionBatchRequestDTO;
import app.estudiante.utils.ComboItemDTO;
import app.estudiante.utils.NominaEstudianteResponseDTO;

public interface IcalificacionesServicio {
    List<NominaEstudianteResponseDTO> obtenerNominaEstudiantes(Integer idDetallePlanDeEstudio, Integer idPeriodoEvaluativo);
    void guardarCalificacionesBatch(CalificacionBatchRequestDTO batchDTO, String usuario);
    List<ComboItemDTO> obtenerGradosCombo();
    List<ComboItemDTO> obtenerAsignaturasSeccionesCombo(Integer idGrado);
    List<ComboItemDTO> obtenerPeriodosEvaluativosCombo(Integer anioLectivo);
    byte[] generarActaCalificacionesPdf(Integer idDetallePlanDeEstudio, Integer anioLectivo);

}
