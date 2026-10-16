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
import app.estudiante.repositorio.NivelEducativoRepositorio;
import app.estudiante.repositorio.PeriodoEvaluativoRepositorio;
import app.estudiante.repositorio.SalonRepositorio;
import app.estudiante.servicio.InterfacesServicios.IcalificacionesServicio;
import app.estudiante.utils.CalificacionBatchRequestDTO;
import app.estudiante.utils.CalificacionItemDTO;
import app.estudiante.utils.ComboItemDTO;
import app.estudiante.utils.EstadoPeriodoEvaluativoResponseDTO;
import app.estudiante.utils.NominaEstudianteResponseDTO;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.Year;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalificacionServicioImpl implements IcalificacionesServicio {

    private final CalificacionesRepositorio calificacionRepository;
    private final DetallePlandeEstudioRepositorio detallePlanDeEstudioRepository;
    private final PeriodoEvaluativoRepositorio periodoEvaluativoRepository;
    private final MatriculaRepositorio matriculaRepository;
    private final GradoRepositorio gradoRepository;
    private final NivelEducativoRepositorio nivelEducativoRepository;
    private final SalonRepositorio salonRepository;
    private final DataSource dataSource;

    @Transactional(readOnly = true)
    @Override
    public List<NominaEstudianteResponseDTO> obtenerNominaEstudiantes(
            Long idDetallePlanDeEstudio, Integer idPeriodoEvaluativo, Integer idSalon, Integer idDocente) {
        log.info("Obteniendo nómina desde la BD para DetallePlan: {} y Periodo: {}", idDetallePlanDeEstudio, idPeriodoEvaluativo);

        DetallePlanDeEstudio detalle = detallePlanDeEstudioRepository.findById(idDetallePlanDeEstudio)
                .orElseThrow(() -> new RuntimeException("No existe el detalle de plan de estudio con ID: " + idDetallePlanDeEstudio));
        validarDocenteAsignado(detalle, idDocente);
        if (idSalon != null && (detalle.getSalon() == null
                || !idSalon.equals(detalle.getSalon().getIdSalon()))) {
            throw new IllegalArgumentException("El salón seleccionado no corresponde al detalle del plan de estudio.");
        }
        if (!periodoEvaluativoRepository.existsById(idPeriodoEvaluativo)) {
            throw new RuntimeException("No existe el período evaluativo con ID: " + idPeriodoEvaluativo);
        }

        return calificacionRepository.obtenerNominaEstudiantes(
                idDetallePlanDeEstudio, idPeriodoEvaluativo, idSalon);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NominaEstudianteResponseDTO> obtenerEstudiantesActivosPorSalon(
            Integer idGrado, Integer idSalon, Long idDetallePlanDeEstudio,
            Integer idPeriodoEvaluativo, Integer idDocente) {
        if (idDetallePlanDeEstudio == null) {
            throw new IllegalArgumentException("Debe seleccionar una asignatura para cargar sus estudiantes.");
        }
        var salon = salonRepository.findById(idSalon)
                .orElseThrow(() -> new RuntimeException("No existe el salón con ID: " + idSalon));
        if (!idGrado.equals(salon.getGrado().getIdGrado())) {
            throw new IllegalArgumentException("El salón seleccionado no corresponde al grado indicado.");
        }
        DetallePlanDeEstudio detalle = detallePlanDeEstudioRepository.findById(idDetallePlanDeEstudio)
                .orElseThrow(() -> new RuntimeException(
                        "No existe el detalle de plan de estudio con ID: " + idDetallePlanDeEstudio));
        validarDocenteAsignado(detalle, idDocente);
        if (detalle.getSalon() == null || !idSalon.equals(detalle.getSalon().getIdSalon())) {
            throw new IllegalArgumentException(
                    "La asignatura seleccionada no corresponde al salón indicado.");
        }
        if (idPeriodoEvaluativo != null && !periodoEvaluativoRepository.existsById(idPeriodoEvaluativo)) {
            throw new RuntimeException("No existe el período evaluativo con ID: " + idPeriodoEvaluativo);
        }
        return calificacionRepository.obtenerNominaActivaPorSalon(
                idSalon, idDetallePlanDeEstudio, idPeriodoEvaluativo);
    }

    @Override
    @Transactional
    public void guardarCalificacionesBatch(CalificacionBatchRequestDTO batchDTO, String usuario) {
        log.info("Iniciando guardado en lote de {} calificaciones por el usuario: {}", batchDTO.calificaciones().size(), usuario);

        DetallePlanDeEstudio detallePlan = detallePlanDeEstudioRepository.findById(batchDTO.idDetallePlanDeEstudio())
                .orElseThrow(() -> new RuntimeException("Detalle de plan de estudio no encontrado ID: " + batchDTO.idDetallePlanDeEstudio()));
        validarDocenteAsignado(detallePlan, batchDTO.idDocente());

        PeriodoEvaluativo periodoEvaluativo = periodoEvaluativoRepository.findById(batchDTO.idPeriodoEvaluativo())
                .orElseThrow(() -> new RuntimeException("Período evaluativo no encontrado ID: " + batchDTO.idPeriodoEvaluativo()));

        EstadoPeriodoEvaluativoResponseDTO estadoEdicion = obtenerEstadoEdicion(
                batchDTO.idPeriodoEvaluativo(), detallePlan.getIdDetallePlanDeEstudio());
        if (!estadoEdicion.permiteEdicion()) {
            throw new IllegalStateException(estadoEdicion.mensajePantalla());
        }

        List<Calificacion> calificacionesAGuardar = new ArrayList<>();

        for (CalificacionItemDTO item : batchDTO.calificaciones()) {
            Matricula matricula = matriculaRepository.findById(item.idMatricula())
                    .orElseThrow(() -> new RuntimeException("Matrícula no encontrada ID: " + item.idMatricula()));
            if (!"ACTIVA".equalsIgnoreCase(matricula.getEstadoMatricula())
                    || detallePlan.getSalon() == null
                    || !detallePlan.getSalon().getIdSalon().equals(matricula.getSalon().getIdSalon())) {
                throw new IllegalArgumentException(
                        "La matrícula " + item.idMatricula() + " no está activa en el salón del detalle seleccionado.");
            }

            Calificacion calificacion = calificacionRepository
                    .findByMatricula_IdMatriculaAndDetallePlanDeEstudio_IdDetallePlanDeEstudioAndPeriodoEvaluativo_IdPeriodoEvaluativo(
                            item.idMatricula(),
                            detallePlan.getIdDetallePlanDeEstudio(),
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
    public List<ComboItemDTO> obtenerNivelesCombo() {
        return nivelEducativoRepository.findAll().stream()
                .map(nivel -> new ComboItemDTO(nivel.getIdNivel(), nivel.getNombre()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComboItemDTO> obtenerGradosCombo(Integer idNivel) {
        var grados = idNivel == null
                ? gradoRepository.findAll()
                : gradoRepository.findByNivelEducativoIdNivel(idNivel);
        return grados.stream()
                .map(grado -> new ComboItemDTO(grado.getIdGrado(), grado.getNombre()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComboItemDTO> obtenerSalonesCombo(Integer idGrado, Integer anioLectivo) {
        int anio = anioLectivo == null ? Year.now().getValue() : anioLectivo;
        if (anio < 1901 || anio > 2155) {
            throw new IllegalArgumentException("El año lectivo debe estar entre 1901 y 2155.");
        }
        return salonRepository.obtenerSalonesPorGradoYAnio(idGrado, (short) anio).stream()
                .map(salon -> new ComboItemDTO(
                        salon.getIdSalon(),
                        String.format("%s - Sección %s (%s)",
                                salon.getCatalogoSalon().getNombreSalon(),
                                salon.getSeccion(),
                                salon.getTurno())))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComboItemDTO> obtenerAsignaturasSeccionesCombo(
            Integer idGrado, Integer idSalon, Integer idDocente) {
        log.info("Cargando asignaturas para grado {}, salón {} y docente {}", idGrado, idSalon, idDocente);
        var detalles = idSalon == null
                ? detallePlanDeEstudioRepository.findAsignaturasConSalonPorGrado(idGrado, idDocente)
                : detallePlanDeEstudioRepository.findAsignaturasConSalonPorGrado(idGrado, idSalon, idDocente);
        return detalles.stream()
                .map(det -> new ComboItemDTO(
                        det.getIdDetallePlanDeEstudio(),
                        String.format("%s - Sec. \"%s\" (%s)",
                                det.getAsignatura().getNombre(),
                                det.getSalon().getSeccion(),
                                det.getSalon().getTurno())))
                .toList();
    }

    private void validarDocenteAsignado(DetallePlanDeEstudio detalle, Integer idDocente) {
        if (idDocente == null || detalle.getDocente() == null
                || !idDocente.equals(detalle.getDocente().getIdpersona())) {
            throw new IllegalArgumentException(
                    "La asignatura no está asignada al docente indicado.");
        }
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

    @Override
    @Transactional(readOnly = true)
    public EstadoPeriodoEvaluativoResponseDTO obtenerEstadoEdicion(
            Integer idPeriodoEvaluativo, Long idDetallePlanDeEstudio) {
        var estadoBase = periodoEvaluativoRepository.obtenerEstadoEdicion(idPeriodoEvaluativo)
                .orElseThrow(() -> new RuntimeException(
                        "No se encontró el estado del período evaluativo con ID: " + idPeriodoEvaluativo));
        boolean permiteEdicion = Boolean.TRUE.equals(estadoBase.getPermiteEdicion());
        String mensaje = estadoBase.getMensajePantalla() == null
                ? "El período evaluativo no permite edición."
                : estadoBase.getMensajePantalla();

        if (!permiteEdicion && idDetallePlanDeEstudio != null
                && periodoEvaluativoRepository.existeAutorizacionExtemporanea(
                        idDetallePlanDeEstudio, idPeriodoEvaluativo)) {
            permiteEdicion = true;
            mensaje = "Edición autorizada de forma extemporánea.";
        }

        return new EstadoPeriodoEvaluativoResponseDTO(
                idPeriodoEvaluativo, permiteEdicion, mensaje, estadoBase.getFechaCierreDocente());
    }

    @Transactional(readOnly = true)
    @Override
    public byte[] generarActaCalificacionesPdf(Long idDetallePlanDeEstudio, Integer idPeriodoEvaluativo) {
        log.info("Generando Acta de Calificaciones en PDF vía Jaspersoft para Plan: {} y Periodo: {}",
                idDetallePlanDeEstudio, idPeriodoEvaluativo);

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

}