package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.Estudiante;

import java.util.List;

public interface IEstudianteServicio {
    public List<Estudiante> ListarEstudiantes();
    public  Estudiante buscarEstudinatePorId(Integer idEstudiante);
    public void guardarEstudiante (Estudiante estudiante);
    public  void eliminarEstudinate(Estudiante estudiante);

}
