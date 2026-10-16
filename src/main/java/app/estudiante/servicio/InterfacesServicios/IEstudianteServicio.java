package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.Estudiante;
import app.estudiante.modelo.EstudianteTutor;
import app.estudiante.modelo.Tutor;
import app.estudiante.utils.EstudianteRequestDTO;
import app.estudiante.utils.EstudianteTutorDTO;

import java.util.List;

public interface IEstudianteServicio {
    Estudiante buscarEstudinatePorId(Integer idEstudiante);
    List<Estudiante> busquedaGeneral(String search);
    Estudiante guardarEstudiante (Estudiante estudiante);
    Estudiante registrarEstudianteConTutores (EstudianteRequestDTO solicitud);
    void eliminarEstudinate(Estudiante estudiante);
    List<Estudiante> ListarEstudiantes();


}
