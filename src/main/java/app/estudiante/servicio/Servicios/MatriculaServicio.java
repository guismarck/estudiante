package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Estudiante;
import app.estudiante.modelo.Matricula;
import app.estudiante.modelo.Salon;
import app.estudiante.repositorio.CatalogoSalonRepositorio;
import app.estudiante.repositorio.EstudianteRepositorio;
import app.estudiante.repositorio.MatriculaRepositorio;
import app.estudiante.repositorio.SalonRepositorio;
import app.estudiante.servicio.InterfacesServicios.IMatriculaServicio;
import app.estudiante.utils.MatriculaDuplicadaException;
import app.estudiante.utils.MatriculaRequestDTO;
import app.estudiante.utils.MatriculaResponseDTO;
import app.estudiante.utils.NumerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MatriculaServicio implements IMatriculaServicio {
    @Autowired
    private MatriculaRepositorio matriculaRepositorio;
    @Autowired
    private SalonRepositorio salonRepositorio;
    @Autowired
    private CatalogoSalonRepositorio catalogoSalonRepositorio;
    @Autowired
    private EstudianteRepositorio estudianteRepositorio;

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
    public void eliminarMatricula(Matricula matricula) {
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
        Integer idCatalogoSalon = salon.getCatalogoSalon().getIdCatalogoSalon();
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
                .estudianteId(guardada.getEstudiante().getIdPersona())
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
}
