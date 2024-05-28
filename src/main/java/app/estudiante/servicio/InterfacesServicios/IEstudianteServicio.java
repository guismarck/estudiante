package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.Estudiante;

import java.util.List;

public interface IEstudianteServicio {
    List<Estudiante> ListarEstudiantes();
     Estudiante buscarEstudinatePorId(Integer idEstudiante);
    List<Estudiante> busquedaGeneral(String search);
    void guardarEstudiante (Estudiante estudiante);
     void eliminarEstudinate(Estudiante estudiante);

}
