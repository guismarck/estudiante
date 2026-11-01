package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Docente;
import app.estudiante.modelo.Asignatura;
import app.estudiante.modelo.DetallePlanDeEstudio;
import app.estudiante.modelo.PlandeEstudio;
import app.estudiante.modelo.Salon;
import app.estudiante.exception.recursosNoEncontradoException;
import app.estudiante.repositorio.AsignaturaRepositorio;
import app.estudiante.repositorio.CalificacionesRepositorio;
import app.estudiante.repositorio.DetallePlandeEstudioRepositorio;
import app.estudiante.repositorio.DocenteRepositorio;
import app.estudiante.repositorio.PlandeEstudioRepositorio;
import app.estudiante.repositorio.SalonRepositorio;
import app.estudiante.servicio.InterfacesServicios.IDocenteServicio;
import app.estudiante.utils.DocenteAsignacionResponseDTO;
import app.estudiante.utils.DocenteAsignacionRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class DocenteServicio implements IDocenteServicio {

    @Autowired
    private DocenteRepositorio docenteRepositorio;

    @Autowired
    private DetallePlandeEstudioRepositorio detallePlanDeEstudioRepositorio;

    @Autowired
    private PlandeEstudioRepositorio planDeEstudioRepositorio;

    @Autowired
    private AsignaturaRepositorio asignaturaRepositorio;

    @Autowired
    private SalonRepositorio salonRepositorio;

    @Autowired
    private CalificacionesRepositorio calificacionesRepositorio;

    @Override
    public List<Docente> ListarDocente() {
        List<Docente> docentes = docenteRepositorio.findAll();
        return docentes;
    }

    @Override
    public List<Docente> busquedaGeneral(String search) {
        return docenteRepositorio.busquedaGeneral(search.trim());
    }

    @Override
    public Docente buscarDocentePorId(Integer idDocente) {
        Docente docente = docenteRepositorio.findById(idDocente).orElse(null);
        return docente;
    }

    @Override
    public void guardarDocente(Docente docente) {
    docenteRepositorio.save(docente);
    }

    @Override
    public void eliminarDocente(Docente docente) {
        docenteRepositorio.delete(docente);
    }

        @Override
        public List<DocenteAsignacionResponseDTO> obtenerMisAsignaciones(
                Integer idDocente, String codigoDocente) {
            Docente docente = idDocente != null
                    ? docenteRepositorio.findById(idDocente).orElse(null)
                    : docenteRepositorio.findByCodDocente(codigoDocente).orElse(null);

            if (docente == null) {
                throw new recursosNoEncontradoException("No se encontró el docente solicitado.");
            }

            return detallePlanDeEstudioRepositorio.findAsignacionesActivasPorDocente(
                    docente.getIdpersona());
        }

    @Override
    @Transactional
    public DocenteAsignacionResponseDTO asignarCarga(DocenteAsignacionRequestDTO request) {
            Docente docente = docenteRepositorio.findById(request.idDocente())
                    .orElseThrow(() -> new recursosNoEncontradoException(
                            "No se encontró el docente solicitado."));
            PlandeEstudio plan = planDeEstudioRepositorio.findById(request.idPlanDeEstudio())
                    .orElseThrow(() -> new recursosNoEncontradoException(
                            "No se encontró el plan de estudio solicitado."));
            Asignatura asignatura = asignaturaRepositorio.findById(request.idAsignatura())
                    .orElseThrow(() -> new recursosNoEncontradoException(
                            "No se encontró la asignatura solicitada."));
            Salon salon = salonRepositorio.findById(request.idSalon())
                    .orElseThrow(() -> new recursosNoEncontradoException(
                            "No se encontró el salón solicitado."));

            if (!Boolean.TRUE.equals(plan.getEstado())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "No se puede asignar carga a un plan de estudio inactivo.");
            }
            if (!plan.getGrado().getIdGrado().equals(salon.getGrado().getIdGrado())
                    || !plan.getAnioLectivo().equals(salon.getAnioLectivo().intValue())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "El salón debe pertenecer al mismo grado y año lectivo del plan de estudio.");
            }
            if (detallePlanDeEstudioRepositorio
                    .existsByPlanDeEstudio_IdPlanDeEstudioAndAsignatura_IdAsignaturaAndSalon_IdSalon(
                            plan.getIdPlanDeEstudio(), asignatura.getIdAsignatura(), salon.getIdSalon())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Ya existe esa asignatura asignada para el salón y plan indicados.");
            }

            DetallePlanDeEstudio detalle = new DetallePlanDeEstudio();
            detalle.setPlanDeEstudio(plan);
            detalle.setAsignatura(asignatura);
            detalle.setDocente(docente);
            detalle.setSalon(salon);
            detalle.setCreadoPor(request.usuarioCreacion().trim());
            detalle.setCreadoEl(LocalDateTime.now());
            detalle = detallePlanDeEstudioRepositorio.save(detalle);

            return new DocenteAsignacionResponseDTO(
                    detalle.getIdDetallePlanDeEstudio(),
                    plan.getIdPlanDeEstudio(),
                    asignatura.getIdAsignatura(),
                    asignatura.getNombre(),
                    plan.getGrado().getIdGrado(),
                    plan.getGrado().getNombre(),
                    plan.getAnioLectivo(),
                    salon.getIdSalon(),
                    salon.getSeccion(),
                    salon.getTurno(),
                    salon.getCatalogoSalon().getNombreSalon()
            );
    }

    @Override
    @Transactional
    public void eliminarCargaAcademica(Integer idDocente, Long idDetallePlanDeEstudio) {
            DetallePlanDeEstudio detalle = detallePlanDeEstudioRepositorio
                    .findByIdDetallePlanDeEstudioAndDocente_Idpersona(idDetallePlanDeEstudio, idDocente)
                    .orElseThrow(() -> new recursosNoEncontradoException(
                            "No se encontró la carga académica para el docente indicado."));

            if (calificacionesRepositorio.existsByDetallePlanDeEstudio_IdDetallePlanDeEstudio(
                    idDetallePlanDeEstudio)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "No se puede eliminar la carga porque ya tiene calificaciones registradas.");
            }

            detallePlanDeEstudioRepositorio.delete(detalle);
    }
}
