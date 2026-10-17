package app.estudiante.servicio.InterfacesServicios;
import app.estudiante.modelo.Estudiante;
import app.estudiante.utils.EstudianteRequestDTO;
import java.util.List;

public interface IEstudianteServicio {
    Estudiante buscarEstudinatePorId(Integer idEstudiante);
    List<Estudiante> busquedaGeneral(String search);
    Estudiante guardarEstudiante (Estudiante estudiante);
    Estudiante registrarEstudianteConTutores (EstudianteRequestDTO solicitud);
    void eliminarEstudinate(Estudiante estudiante);
    List<Estudiante> ListarEstudiantes();


}
