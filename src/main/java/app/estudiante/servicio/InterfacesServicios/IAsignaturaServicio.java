package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.Asignatura;

import java.util.List;

public interface IAsignaturaServicio {
    public List<Asignatura> ListarAsignatura();
    public Asignatura buscarAsignaturaPorId(Integer idAsignatura);
    public void guardarAsignatura  (Asignatura  asignatura);
    public  void eliminarAsignatura (Asignatura  asignatura);

}
