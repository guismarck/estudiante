package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Estudiante;
import app.estudiante.exception.recursosNoEncontradoException;
import app.estudiante.modelo.EstudianteTutor;
import app.estudiante.modelo.Tutor;
import app.estudiante.repositorio.EstudianteRepositorio;
import app.estudiante.repositorio.EstudianteTutorRepositorio;
import app.estudiante.repositorio.TutorRepositorio;
import app.estudiante.servicio.InterfacesServicios.IEstudianteServicio;
import app.estudiante.utils.EstudianteRequestDTO;
import app.estudiante.utils.EstudianteTutorDTO;
import app.estudiante.utils.NumerUtils;
import app.estudiante.utils.ValidacionEdad;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.util.Set;
import app.estudiante.modelo.*;

@Service
public class EstudianteServicio implements IEstudianteServicio {

    final Logger logger = LoggerFactory.getLogger(EstudianteServicio.class);

    @Autowired
    private EstudianteRepositorio estudianteRepositorio;
    @Autowired
    private EstudianteTutorRepositorio estudianteTutorRepositorio;
    @Autowired
    private TutorRepositorio tutorRepositorio;

    // NUEVO: se usa para buscar por cédula en toda la tabla persona,
    // sin necesidad de agregar métodos a los repositorios.
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Estudiante> ListarEstudiantes() {
        List<Estudiante> estudiantes = estudianteRepositorio.findAll();
        return estudiantes;
    }

    @Override
    public Estudiante buscarEstudinatePorId(Integer idEstudiante) {
        Estudiante estudiante = estudianteRepositorio.findById(idEstudiante).orElse(null);
        return estudiante;
    }

    @Override
    public Estudiante buscarEstudiantePorCodigo(String codEstudiante) {
        return estudianteRepositorio.findByCodEstudiante(codEstudiante)
                .orElseThrow(() -> new recursosNoEncontradoException(
                        "No se encontró un estudiante con código " + codEstudiante));
    }


    public List<Estudiante> busquedaGeneral(String search){
        List<Estudiante> estudiantes = new ArrayList<>(estudianteRepositorio.busquedaGeneral(search));
        if (NumerUtils.isInt(search)) {
            estudianteRepositorio.findById(Integer.parseInt(search)).ifPresent(estudiante -> {
                boolean yaIncluido = estudiantes.stream()
                        .anyMatch(resultado -> resultado.getIdpersona().equals(estudiante.getIdpersona()));
                if (!yaIncluido) {
                    estudiantes.add(0, estudiante);
                }
            });
        }
        return estudiantes;
    }

    @Override
    public Estudiante guardarEstudiante(Estudiante estudiante) {

        return  estudianteRepositorio.save(estudiante);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Estudiante registrarEstudianteConTutores(
            EstudianteRequestDTO solicitud) {

        if (solicitud == null || solicitud.getEstudiante() == null) {
            throw new IllegalArgumentException(
                    "Los datos del estudiante son obligatorios."
            );
        }

        if (solicitud.getTutores() == null
                || solicitud.getTutores().isEmpty()) {
            throw new IllegalArgumentException(
                    "Debe asignar al menos un tutor."
            );
        }

        Estudiante estudiante = solicitud.getEstudiante();

        // NUEVO: el estudiante debe tener al menos 5 años cumplidos.
        ValidacionEdad.validarEdadEstudiante(estudiante.getFecha_nacimiento());

        // Evitar conservar relaciones anteriores del objeto recibido.
        estudiante.getTutores().clear();

        // NUEVO: evita asignar el mismo tutor dos veces al mismo estudiante.
        Set<Integer> tutoresAsignados = new HashSet<>();

        for (EstudianteTutorDTO dto : solicitud.getTutores()) {

            if (dto == null || dto.tutor() == null) {
                throw new IllegalArgumentException(
                        "La información de cada tutor es obligatoria."
                );
            }

            Tutor tutorEntity;

            if (dto.tutor().getIdpersona() == null) {
                // NUEVO: tutor nuevo -> comprobar que no exista ya (por cédula)
                // antes de insertarlo.
                // NUEVO: el tutor nuevo debe tener al menos 18 años cumplidos.
                // (Un tutor ya registrado se reutiliza tal cual, sin revalidar.)
                ValidacionEdad.validarEdadTutor(dto.tutor().getFecha_nacimiento());

                validarTutorNoDuplicado(dto.tutor());

                tutorEntity = tutorRepositorio.save(dto.tutor());
            } else {
                // Tutor ya registrado: se reutiliza, no se inserta ni se modifica.
                tutorEntity = tutorRepositorio
                        .findById(dto.tutor().getIdpersona())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "El tutor no existe."
                                )
                        );
            }

            // NUEVO: el mismo tutor no puede aparecer dos veces en el registro.
            if (!tutoresAsignados.add(tutorEntity.getIdpersona())) {
                throw new IllegalArgumentException(
                        "El mismo tutor no puede asignarse dos veces al estudiante."
                );
            }

            EstudianteTutor relacion = new EstudianteTutor();

            relacion.setTutor(tutorEntity);
            relacion.setPersona(estudiante);
            relacion.setParentesco(dto.parentesco());
            relacion.setEstado(
                    dto.activo() != null ? dto.activo() : true
            );
            relacion.setCreadoPor("SISTEMA_API");
            relacion.setActualizadoPor("SISTEMA_API");

            estudiante.getTutores().add(relacion);
        }

        return estudianteRepositorio.saveAndFlush(estudiante);
    }

    /**
     * NUEVO. Valida que un tutor NUEVO no repita una cédula ya registrada.
     *
     * - Sin cédula (es opcional): no hay forma segura de detectar el duplicado
     *   en el servidor; el frontend avisa por nombre y teléfono.
     * - Con cédula: si ya existe una persona con esa cédula se rechaza el
     *   registro con un mensaje claro, en lugar del error SQL de la restricción
     *   única uk_persona_cedula.
     *
     * Usa IllegalArgumentException, igual que el resto de validaciones de este
     * método, para que el controlador la trate como hasta ahora.
     */
    private void validarTutorNoDuplicado(Tutor tutor) {
        String cedula = tutor.getCedula() == null
                ? null
                : tutor.getCedula().trim();

        if (cedula == null || cedula.isEmpty()) {
            // Nunca guardar "" en una columna única.
            tutor.setCedula(null);
            return;
        }

        tutor.setCedula(cedula);

        List<Persona> encontradas = entityManager
                .createQuery(
                        "SELECT p FROM Persona p WHERE p.cedula = :cedula",
                        Persona.class)
                .setParameter("cedula", cedula)
                .setMaxResults(1)
                .getResultList();

        if (encontradas.isEmpty()) {
            return;
        }

        Persona existente = encontradas.get(0);

        String nombre = ((existente.getNombre_completo() == null ? "" : existente.getNombre_completo())
                + " "
                + (existente.getApellido_completo() == null ? "" : existente.getApellido_completo())).trim();

        boolean esTutor = tutorRepositorio.existsById(existente.getIdpersona());

        if (esTutor) {
            throw new IllegalArgumentException(
                    "Ya existe un tutor registrado con la cédula " + cedula
                            + " (" + nombre + "). Selecciónelo en el buscador de tutores "
                            + "para reutilizarlo."
            );
        }

        throw new IllegalArgumentException(
                "La cédula " + cedula + " ya está registrada a nombre de "
                        + nombre + ", que no es tutor. Verifique el dato."
        );
    }

    @Override
    public void eliminarEstudinate(Estudiante estudiante) {
        estudianteRepositorio.delete(estudiante);
    }
}