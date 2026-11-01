package app.estudiante.servicio.Servicios;

import app.estudiante.exception.recursosNoEncontradoException;
import app.estudiante.modelo.Estudiante;
import app.estudiante.modelo.Grado;
import app.estudiante.modelo.Matricula;
import app.estudiante.modelo.PlandeEstudio;
import app.estudiante.modelo.Salon;
import app.estudiante.repositorio.CatalogoSalonRepositorio;
import app.estudiante.repositorio.CalificacionesRepositorio;
import app.estudiante.repositorio.EstudianteRepositorio;
import app.estudiante.repositorio.MatriculaRepositorio;
import app.estudiante.repositorio.PlandeEstudioRepositorio;
import app.estudiante.repositorio.SalonRepositorio;
import app.estudiante.servicio.InterfacesServicios.IMatriculaServicio;
import app.estudiante.utils.MatriculaDuplicadaException;
import app.estudiante.utils.MatriculaDetalleDTO;
import app.estudiante.utils.MatriculaRequestDTO;
import app.estudiante.utils.MatriculaResponseDTO;
import app.estudiante.utils.NumerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MatriculaServicio implements IMatriculaServicio {
    @Autowired
    private MatriculaRepositorio matriculaRepositorio;
    @Autowired
    private PlandeEstudioRepositorio planDeEstudioRepositorio;
    @Autowired
    private SalonRepositorio salonRepositorio;
    @Autowired
    private CatalogoSalonRepositorio catalogoSalonRepositorio;
    @Autowired
    private EstudianteRepositorio estudianteRepositorio;
    @Autowired
    private CalificacionesRepositorio calificacionesRepositorio;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Matricula> ListarMatricula() {
        List<Matricula>  matriculas= matriculaRepositorio.findAll();
        return matriculas;
    }

    @Override
    public Matricula buscarMatriculaPorId(Integer idmatricula) {
        Matricula matricula = matriculaRepositorio.findById(idmatricula).orElse(null);
        return matricula;
    }

    @Override
    @Transactional(readOnly = true)
    public MatriculaDetalleDTO obtenerDetalleMatricula(Integer idmatricula) {
        Matricula matricula = matriculaRepositorio.buscarDetallePorId(idmatricula)
                .orElseThrow(() -> new recursosNoEncontradoException(
                        "Matrícula no encontrada con ID: " + idmatricula));

        Estudiante estudiante = matricula.getEstudiante();
        Salon salon = matricula.getSalon();
        Grado grado = salon == null ? null : salon.getGrado();

        PlandeEstudio plan = grado == null
                ? null
                : planDeEstudioRepositorio.findByGrado_IdGradoAndAnioLectivo(
                        grado.getIdGrado(), matricula.getAnioLectivo().intValue())
                        .orElse(null);

        String nombreCompleto = estudiante == null
                ? null
                : java.util.stream.Stream.of(
                                estudiante.getNombre_completo(),
                                estudiante.getApellido_completo())
                        .filter(nombre -> nombre != null && !nombre.isBlank())
                        .collect(java.util.stream.Collectors.joining(" "));
        String nombrePlan = plan == null || grado == null
                ? null
                : grado.getNombre() + " - " + plan.getAnioLectivo();

        return new MatriculaDetalleDTO(
                matricula.getIdMatricula(),
                estudiante == null ? null : estudiante.getIdpersona(),
                estudiante == null ? null : estudiante.getNombre_completo(),
                estudiante == null ? null : estudiante.getApellido_completo(),
                nombreCompleto,
                grado == null ? null : grado.getNombre(),
                grado == null || grado.getNivelEducativo() == null
                        ? null
                        : grado.getNivelEducativo().getNombre(),
                plan == null ? null : plan.getIdPlanDeEstudio(),
                plan == null ? null : plan.getAnioLectivo(),
                nombrePlan,
                matricula.getAnioLectivo(),
                matricula.getCostoMatricula(),
                matricula.getEstadoMatricula(),
                matricula.getFechaMatricula(),
                salon == null || salon.getCatalogoSalon() == null
                        ? null
                        : salon.getCatalogoSalon().getNombreSalon(),
                salon == null ? null : salon.getTurno(),
                salon == null ? null : salon.getSeccion()
        );
    }

    @Override
    public List<Matricula> busquedaGeneral(String search){
        var isInt = NumerUtils.isInt(search);
        if (isInt) return List.of(buscarMatriculaPorId(Integer.parseInt(search)));
        return matriculaRepositorio.busquedaGeneral(search);
    }

    @Override
    public void guardarMatricula(Matricula matricula) {
        matricula.setFechaMatricula(LocalDateTime.now());
        matricula.setEstadoMatricula("AC");
     matriculaRepositorio.save(matricula);
    }

    @Override
    @Transactional
    public void eliminarMatricula(Matricula matricula) {
        if (matricula == null) {
            throw new recursosNoEncontradoException("Matrícula no encontrada.");
        }
        if (calificacionesRepositorio.existsByMatricula_IdMatricula(matricula.getIdMatricula())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar esta matrícula porque tiene calificaciones asociadas. Puede cambiar su estado en lugar de eliminarla.");
        }
        matriculaRepositorio.delete(matricula);
    }

    @Override
    @Transactional
    public MatriculaResponseDTO registrarMatriculaSinPago(MatriculaRequestDTO dto) {
        Short anioLectivo = Short.valueOf(dto.getAnioLectivo());

        //  Validación de Unicidad: Verificar si el estudiante ya tiene matrícula en el mismo año lectivo
        boolean yaMatriculado = matriculaRepositorio.existsByEstudianteIdAndAnioLectivo(dto.getIdPersona(), anioLectivo);
        if (yaMatriculado) {
            throw new MatriculaDuplicadaException(
                    String.format("El estudiante con ID %d ya cuenta con una matrícula registrada para el año lectivo %d", dto.getIdPersona(), anioLectivo)
            );
//            throw new RuntimeException(
//                    "El estudiante con ID " + dto.getIdPersona() + " ya cuenta con una matrícula registrada para el año lectivo " + anioLectivo
//            );
        }

        //  Búsqueda y Validación de Existencia de Entidades
        Estudiante estudiante = estudianteRepositorio.findById(Math.toIntExact(dto.getIdPersona()))
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + dto.getIdPersona()));

        Salon salon = salonRepositorio.findById(Math.toIntExact(dto.getIdSalon()))
                .orElseThrow(() -> new RuntimeException("Salón no encontrado con ID: " + dto.getIdSalon()));

        //  Decrementar Capacidad Atómicamente
        Long idCatalogoSalon = salon.getCatalogoSalon().getIdCatalogoSalon();
        int filasActualizadas = catalogoSalonRepositorio.decrementarCapacidadSiDisponible(idCatalogoSalon);

        if (filasActualizadas == 0) {
            throw new RuntimeException(
                    "El salón '" + salon.getCatalogoSalon().getNombreSalon() + "' ha alcanzado su capacidad máxima."
            );
        }

        //  Construcción y Guardado de la Matrícula
        Matricula matricula = Matricula.builder()
                .estudiante(estudiante)
                .salon(salon)
                .anioLectivo(anioLectivo)
                .estadoMatricula("ACTIVA")
                .fechaMatricula(LocalDateTime.now())
                .creadoEl(LocalDateTime.now())
                .creadoPor("SYSTEM")
                .build();

        Matricula guardada = matriculaRepositorio.save(matricula);

        //  Retorno de DTO de Respuesta
        return MatriculaResponseDTO.builder()
                .idMatricula(Long.valueOf(guardada.getIdMatricula()))
                .estudianteId(Long.valueOf(guardada.getEstudiante().getIdpersona()))
                .nombreSalon(salon.getCatalogoSalon().getNombreSalon())
                .anioLectivo(String.valueOf(guardada.getAnioLectivo()))
                .estadoMatricula(guardada.getEstadoMatricula())
                .fechaRegistro(guardada.getFechaMatricula())
                .build();
        //        Salon salon = salonRepositorio.findById(Math.toIntExact(dto.getIdSalon()))
//                .orElse(null);
//
//        int filasActualizadas = 0;
//        if (salon != null) {
//            filasActualizadas = catalogoSalonRepositorio.decrementarCapacidadSiDisponible(salon.getCatalogoSalon().getIdCatalogoSalon());
//        }
//
//        if (filasActualizadas == 0) {
//            if (salon != null) {
//                throw new RuntimeException("El salón '" + salon.getCatalogoSalon().getNombreSalon() + "' ha alcanzado su capacidad máxima.");
//            }
//        }
//
//        Optional<Estudiante> estudiante = estudianteRepositorio.findById(Integer.parseInt(String.valueOf(dto.getIdPersona())));
//
//        Matricula matricula = Matricula.builder()
//                .estudiante(estudiante.get())
//                .salon(salon)
//                .anioLectivo(Short.valueOf(dto.getAnioLectivo()))
//                .estadoMatricula("ACTIVA")
//                .fechaMatricula(LocalDateTime.now())
//                .creadoEl(LocalDateTime.now())
//                .creadoPor("ADMIN")
//                .build();
//
//        Matricula guardada = matriculaRepositorio.save(matricula);
//
//        return MatriculaResponseDTO.builder()
//                .idMatricula(Long.valueOf(guardada.getIdMatricula()))
//                .estudianteId(Long.valueOf(guardada.getEstudiante().getIdpersona()))
//                .nombreSalon(Objects.requireNonNull(salon).getCatalogoSalon().getNombreSalon())
//                .anioLectivo(String.valueOf(guardada.getAnioLectivo()))
//                .estadoMatricula(guardada.getEstadoMatricula())
//                .fechaRegistro(guardada.getFechaMatricula())
//                .build();
    }

    @Override
    @Transactional
    public MatriculaResponseDTO registrarMatriculaCompleta(MatriculaRequestDTO dto) {
        Long idPersonaEstudiante = dto.getIdPersonaEstudiante() != null
                ? dto.getIdPersonaEstudiante()
                : dto.getIdPersona();

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_guardar_matricula_completa");
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(5, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(8, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(9, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(10, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(11, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(12, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(13, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(14, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(15, BigDecimal.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(16, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(17, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(18, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(19, Integer.class, ParameterMode.OUT);

        query.setParameter(1, toInteger(idPersonaEstudiante));
        query.setParameter(2, dto.getNombresEstudiante());
        query.setParameter(3, dto.getApellidosEstudiante());
        query.setParameter(4, dto.getCodigoMined());
        query.setParameter(5, toInteger(dto.getIdPersonaTutor()));
        query.setParameter(6, dto.getNombreCompletoTutor());
        query.setParameter(7, dto.getCedulaTutor());
        query.setParameter(8, dto.getTelefonoTutor());
        query.setParameter(9, dto.getCorreoTutor());
        query.setParameter(10, dto.getParentesco());
        query.setParameter(11, dto.getRepresentanteLegalFinanciero());
        query.setParameter(12, toInteger(dto.getIdSalon()));
        query.setParameter(13, Integer.valueOf(dto.getAnioLectivo()));
        query.setParameter(14, dto.getTipoIngreso());
        query.setParameter(15, dto.getCostoMatricula());
        query.setParameter(16, dto.getEstado());
        query.setParameter(17, serializarRequisitos(dto.getRequisitosPresentados()));
        query.setParameter(18, dto.getUsuario());

        query.execute();

        Integer idMatricula = (Integer) query.getOutputParameterValue(19);
        if (idMatricula == null) {
            throw new IllegalStateException(
                    "El procedimiento sp_guardar_matricula_completa no devolvió el ID de la matrícula."
            );
        }

        return MatriculaResponseDTO.builder()
                .idMatricula(idMatricula.longValue())
                .estudianteId(idPersonaEstudiante)
                .anioLectivo(dto.getAnioLectivo())
                .estadoMatricula(dto.getEstado())
                .fechaRegistro(LocalDateTime.now())
                .build();
    }

    private Integer toInteger(Long value) {
        return value == null ? null : Math.toIntExact(value);
    }

    private String serializarRequisitos(List<Integer> requisitos) {
        if (requisitos == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(requisitos);
        } catch (JsonProcessingException exception) {
            throw new IllegalArgumentException("No se pudieron serializar los requisitos de matrícula.", exception);
        }
    }
}
