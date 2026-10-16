package app.estudiante.servicio.InterfacesServicios;

import java.util.List;

import app.estudiante.utils.CalificacionBatchRequestDTO;
import app.estudiante.utils.ComboItemDTO;
import app.estudiante.utils.EstadoPeriodoEvaluativoResponseDTO;
import app.estudiante.utils.NominaEstudianteResponseDTO;

public interface IcalificacionesServicio {
    List<NominaEstudianteResponseDTO> obtenerNominaEstudiantes(
            Long idDetallePlanDeEstudio, Integer idPeriodoEvaluativo, Integer idSalon, Integer idDocente);
    List<NominaEstudianteResponseDTO> obtenerEstudiantesActivosPorSalon(
            Integer idGrado, Integer idSalon, Long idDetallePlanDeEstudio,
            Integer idPeriodoEvaluativo, Integer idDocente);
    void guardarCalificacionesBatch(CalificacionBatchRequestDTO batchDTO, String usuario);
    List<ComboItemDTO> obtenerNivelesCombo();
    List<ComboItemDTO> obtenerGradosCombo(Integer idNivel);
    List<ComboItemDTO> obtenerSalonesCombo(Integer idGrado, Integer anioLectivo);
    List<ComboItemDTO> obtenerAsignaturasSeccionesCombo(Integer idGrado, Integer idSalon, Integer idDocente);
    List<ComboItemDTO> obtenerPeriodosEvaluativosCombo(Integer anioLectivo);
    EstadoPeriodoEvaluativoResponseDTO obtenerEstadoEdicion(Integer idPeriodoEvaluativo, Long idDetallePlanDeEstudio);
    byte[] generarActaCalificacionesPdf(Long idDetallePlanDeEstudio, Integer idPeriodoEvaluativo);

}
