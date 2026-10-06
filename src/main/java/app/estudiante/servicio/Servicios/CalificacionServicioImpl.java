package app.estudiante.servicio.Servicios;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperRunManager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import app.estudiante.modelo.Calificacion;
import app.estudiante.modelo.DetallePlanDeEstudio;
import app.estudiante.modelo.Matricula;
import app.estudiante.modelo.PeriodoEvaluativo;
import app.estudiante.repositorio.CalificacionesRepositorio;
import app.estudiante.repositorio.DetallePlandeEstudioRepositorio;
import app.estudiante.repositorio.GradoRepositorio;
import app.estudiante.repositorio.MatriculaRepositorio;
import app.estudiante.repositorio.PeriodoEvaluativoRepositorio;
import app.estudiante.servicio.InterfacesServicios.IcalificacionesServicio;
import app.estudiante.utils.CalificacionBatchRequestDTO;
import app.estudiante.utils.CalificacionItemDTO;
import app.estudiante.utils.ComboItemDTO;
import app.estudiante.utils.NominaEstudianteResponseDTO;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalificacionServicioImpl implements IcalificacionesServicio {

    private final CalificacionesRepositorio calificacionRepository;
    private final DetallePlandeEstudioRepositorio detallePlanDeEstudioRepository;
    private final PeriodoEvaluativoRepositorio periodoEvaluativoRepository;
    private final MatriculaRepositorio matriculaRepository;
    private final GradoRepositorio gradoRepository;
    private final DataSource dataSource;

    @Transactional(readOnly = true)
    public List<NominaEstudianteResponseDTO> obtenerNominaEstudiantes(Integer idDetallePlanDeEstudio, Integer idPeriodoEvaluativo) {
        log.info("Obteniendo nómina desde la BD para DetallePlan: {} y Periodo: {}", idDetallePlanDeEstudio, idPeriodoEvaluativo);

        if (!detallePlanDeEstudioRepository.existsById(idDetallePlanDeEstudio)) {
            throw new RuntimeException("No existe el detalle de plan de estudio con ID: " + idDetallePlanDeEstudio);
        }
        if (!periodoEvaluativoRepository.existsById(idPeriodoEvaluativo)) {
            throw new RuntimeException("No existe el período evaluativo con ID: " + idPeriodoEvaluativo);
        }

        return calificacionRepository.obtenerNominaEstudiantes(idDetallePlanDeEstudio, idPeriodoEvaluativo);
    }

    @Override
    @Transactional
    public void guardarCalificacionesBatch(CalificacionBatchRequestDTO batchDTO, String usuario) {
        log.info("Iniciando guardado en lote de {} calificaciones por el usuario: {}", batchDTO.calificaciones().size(), usuario);

        DetallePlanDeEstudio detallePlan = detallePlanDeEstudioRepository.findById(batchDTO.idDetallePlanDeEstudio())
                .orElseThrow(() -> new RuntimeException("Detalle de plan de estudio no encontrado ID: " + batchDTO.idDetallePlanDeEstudio()));

        PeriodoEvaluativo periodoEvaluativo = periodoEvaluativoRepository.findById(batchDTO.idPeriodoEvaluativo())
                .orElseThrow(() -> new RuntimeException("Período evaluativo no encontrado ID: " + batchDTO.idPeriodoEvaluativo()));

        List<Calificacion> calificacionesAGuardar = new ArrayList<>();

        for (CalificacionItemDTO item : batchDTO.calificaciones()) {
            Matricula matricula = matriculaRepository.findById(item.idMatricula())
                    .orElseThrow(() -> new RuntimeException("Matrícula no encontrada ID: " + item.idMatricula()));

            Calificacion calificacion = calificacionRepository
                    .findByMatricula_IdMatriculaAndDetallePlanDeEstudio_IdDetallePlanDeEstudioAndPeriodoEvaluativo_IdPeriodoEvaluativo(
                            item.idMatricula(),
                            batchDTO.idDetallePlanDeEstudio(),
                            batchDTO.idPeriodoEvaluativo()
                    )
                    .orElseGet(() -> {
                        Calificacion nueva = new Calificacion();
                        nueva.setMatricula(matricula);
                        nueva.setDetallePlanDeEstudio(detallePlan);
                        nueva.setPeriodoEvaluativo(periodoEvaluativo);
                        nueva.setCreadoPor(usuario);
                        return nueva;
                    });

            calificacion.setAcumulado(item.acumulado());
            calificacion.setExamen(item.examen());
            calificacion.setActualizadoPor(usuario);

            calificacionesAGuardar.add(calificacion);
        }

        calificacionRepository.saveAll(calificacionesAGuardar);
        log.info("Proceso en lote finalizado exitosamente. {} registros procesados.", calificacionesAGuardar.size());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComboItemDTO> obtenerGradosCombo() {
        log.info("Cargando catálogo de Grados Académicos desde la BD");
        return gradoRepository.findAll()
                .stream()
                .map(g -> new ComboItemDTO(g.getIdGrado(), g.getNombre()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComboItemDTO> obtenerAsignaturasSeccionesCombo(Integer idGrado) {
        log.info("Cargando catálogo de Asignaturas y Secciones para el Grado ID: {}", idGrado);
        return detallePlanDeEstudioRepository.findAsignaturasConSalonPorGrado(idGrado)
                .stream()
                .map(det -> new ComboItemDTO(
                        det.getIdDetallePlanDeEstudio(),
                        String.format("%s - Sec \"%s\" (%s)",
                                det.getAsignatura().getNombre(),
                                det.getSalon().getSeccion(),
                                det.getSalon().getTurno())
                ))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComboItemDTO> obtenerPeriodosEvaluativosCombo(Integer anioLectivo) {
        log.info("Cargando catálogo de Períodos Evaluativos para el año lectivo: {}", anioLectivo);
        return periodoEvaluativoRepository.findByAnioEscolarAndEstadoTrueOrderByNumeroPeriodoAsc(anioLectivo)
                .stream()
                .map(p -> new ComboItemDTO(p.getIdPeriodoEvaluativo(), p.getNombrePeriodo()))
                .toList();
    }

    @Transactional(readOnly = true)
    public byte[] generarActaCalificacionesPdf(Long idDetallePlanDeEstudio, Long idPeriodoEvaluativo) {
        log.info("Generando Acta de Calificaciones en PDF vía Jaspersoft para Plan: {} y Periodo: {}", idDetallePlanDeEstudio, idPeriodoEvaluativo);

        try (Connection connection = dataSource.getConnection()) {
            InputStream reportInputStream = getClass().getResourceAsStream("/reportes/acta_calificaciones.jasper");

            if (reportInputStream == null) {
                throw new RuntimeException("No se encontró el archivo de reporte jaspersoft: /reportes/acta_calificaciones.jasper");
            }

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ID_DETALLE_PLAN", idDetallePlanDeEstudio);
            parametros.put("ID_PERIODO", idPeriodoEvaluativo);

            return JasperRunManager.runReportToPdf(reportInputStream, parametros, connection);
        } catch (Exception e) {
            log.error("Error al generar el acta de calificaciones en Jaspersoft", e);
            throw new RuntimeException("Error en el servidor al generar el reporte PDF", e);
        }
    }

    @Override
    public byte[] generarActaCalificacionesPdf(Integer idDetallePlanDeEstudio, Integer anioLectivo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generarActaCalificacionesPdf'");
    }
}