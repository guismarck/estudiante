package app.estudiante.servicio.Servicios;


import app.estudiante.api.GradoControlador;
import app.estudiante.modelo.Estudiante;
import app.estudiante.exception.recurosNoEncontradoException;
import app.estudiante.modelo.EstudianteTutor;
import app.estudiante.modelo.Tutor;
import app.estudiante.repositorio.EstudianteRepositorio;
import app.estudiante.repositorio.EstudianteTutorRepositorio;
import app.estudiante.repositorio.TutorRepositorio;
import app.estudiante.servicio.InterfacesServicios.IEstudianteServicio;
import app.estudiante.utils.EstudianteRequestDTO;
import app.estudiante.utils.EstudianteTutorDTO;
import app.estudiante.utils.NumerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EstudianteServicio implements IEstudianteServicio {

   final Logger logger = LoggerFactory.getLogger(EstudianteServicio.class);

 @Autowired
 private EstudianteRepositorio estudianteRepositorio;
    @Autowired
    private EstudianteTutorRepositorio estudianteTutorRepositorio;
    @Autowired
    private TutorRepositorio tutorRepositorio;

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
                .orElseThrow(() -> new recurosNoEncontradoException(
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

         /*if (estudiante.getCedula() != null && !estudiante.getCedula().trim().isEmpty()) {
            LocalDate fechaCalculada = ValidacionCedula.extraerFechaNacimiento(estudiante.getCedula());
            if (fechaCalculada != null) {
                // Sobrescribir/asegurar la fecha derivada de la cédula
                estudiante.setFecha_nacimiento(Date.valueOf(fechaCalculada));
            }
        }

        if (estudiante.getFecha_nacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no es válida ni pudo ser derivada de la Cédula.");
        }

        Estudiante entidad = new Estudiante();

        entidad.setNombre_completo(estudiante.getNombre_completo());
        entidad.setApellido_completo(estudiante.getApellido_completo());
        entidad.setFecha_nacimiento(estudiante.getFecha_nacimiento());
        entidad.setCedula(estudiante.getCedula());
        entidad.setSexo(estudiante.getSexo());
        entidad.setDireccion(estudiante.getDireccion());
        entidad.setPartida_nacimiento(estudiante.getPartida_nacimiento());
        entidad.setCodEstudiante(estudiante.getCodEstudiante());
        entidad.setCodigoMined(estudiante.getCodigoMined());
        entidad.setEstado(estudiante.getEstado());

        Estudiante guardado = estudianteRepositorio.save(entidad);//agregar o modificar
        estudiante.setIdpersona(guardado.getIdpersona());*/
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

        // Evitar conservar relaciones anteriores del objeto recibido.
        estudiante.getTutores().clear();

        for (EstudianteTutorDTO dto : solicitud.getTutores()) {

            if (dto == null || dto.tutor() == null) {
                throw new IllegalArgumentException(
                        "La información de cada tutor es obligatoria."
                );
            }

            Tutor tutorEntity;

            if (dto.tutor().getIdpersona() == null) {
                tutorEntity = tutorRepositorio.save(dto.tutor());
            } else {
                tutorEntity = tutorRepositorio
                        .findById(dto.tutor().getIdpersona())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "El tutor no existe."
                                )
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

    @Override
    public void eliminarEstudinate(Estudiante estudiante) {
      estudianteRepositorio.delete(estudiante);
    }
}
