package app.estudiante.servicio.Servicios;


import app.estudiante.api.GradoControlador;
import app.estudiante.modelo.Estudiante;
import app.estudiante.modelo.EstudianteTutor;
import app.estudiante.modelo.EstudianteTutorId;
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
    public List<Estudiante> busquedaGeneral(String search){
        var isInt = NumerUtils.isInt(search);
        if (isInt) return List.of(buscarEstudinatePorId(Integer.parseInt(search)));
        return estudianteRepositorio.busquedaGeneral(search);
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
    public Estudiante registrarEstudianteConTutores(EstudianteRequestDTO solicitud) {

        if (Objects.isNull(solicitud) || Objects.isNull(solicitud.getEstudiante())) {
            throw new IllegalArgumentException("Los datos del estudiante son obligatorios.");
        }

        if (Objects.isNull(solicitud.getTutores()) || solicitud.getTutores().isEmpty()) {
            throw new IllegalArgumentException("Debe asignar al menos un tutor.");
        }

        // 1. Obtener la referencia del estudiante del DTO
        Estudiante estudiante = solicitud.getEstudiante();

        // Aseguramos que la lista de tutores de la entidad esté limpia
        estudiante.getTutores().clear();

        // 2. Procesar la lista de tutores del DTO
        for (EstudianteTutorDTO dto : solicitud.getTutores()) {
            if (Objects.isNull(dto.tutor())) {
                throw new IllegalArgumentException("La información de cada tutor es obligatoria.");
            }

            Tutor tutorEntity;
            if (Objects.isNull(dto.tutor().getIdpersona())) {
                // Guardamos el tutor si es nuevo para asegurarnos de que herede de Persona correctamente
                tutorEntity = tutorRepositorio.saveAndFlush(dto.tutor());
            } else {
                tutorEntity = tutorRepositorio.findById(dto.tutor().getIdpersona())
                        .orElseThrow(() -> new IllegalArgumentException("El tutor no existe."));
            }

            // 3. Crear el objeto intermedio pivote
            EstudianteTutor relacion = new EstudianteTutor();
            relacion.setId(new EstudianteTutorId(estudiante.getIdpersona(), tutorEntity.getIdpersona()));
            relacion.setTutor(tutorEntity);
            relacion.setPersona(estudiante); // Enlace bidireccional obligatorio
            relacion.setParentesco(dto.parentesco());
            relacion.setEstado(Objects.nonNull(dto.activo()) ? dto.activo() : true);
            relacion.setCreadoPor("SISTEMA_API");
            relacion.setActualizadoPor("SISTEMA_API");

            // 4. Configurar la llave compuesta intermedia implícitamente si es necesario
            // Nota: Si usas @MapsId en EstudianteTutor, Hibernate llenará estos IDs automáticamente al guardar el padre,
            // pero asignarlos manualmente evita problemas con algunas versiones de MariaDB/MySQL:
            if (Objects.nonNull(estudiante.getIdpersona())) {
                relacion.setId(new EstudianteTutorId(estudiante.getIdpersona(), tutorEntity.getIdpersona()));
            }

            // 5. ¡ESTA ES LA CLAVE! Añadimos la relación directamente a la lista del estudiante
            estudiante.getTutores().add(relacion);
        }

        // 6. GUARDAR ÚNICAMENTE AL ESTUDIANTE
        // Gracias a cascade = CascadeType.ALL, Hibernate guardará primero el Estudiante,
        // resolverá su ID, y luego guardará automáticamente cada elemento de la lista 'tutores' en la tabla puente.
        return estudianteRepositorio.saveAndFlush(estudiante);
    }




    @Override
    public void eliminarEstudinate(Estudiante estudiante) {
      estudianteRepositorio.delete(estudiante);
    }
}
