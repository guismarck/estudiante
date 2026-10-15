package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.Estudiante;

import java.util.List;

public interface IEstudianteServicio {
    Estudiante buscarEstudinatePorId(Integer idEstudiante);
    List<Estudiante> busquedaGeneral(String search);
    Estudiante guardarEstudiante (Estudiante estudiante);
    void eliminarEstudinate(Estudiante estudiante);
    List<Estudiante> ListarEstudiantes();


}
