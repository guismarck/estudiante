package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.Calificaciones;

import java.util.List;

public interface IcalificacionesServicio {
    public List<Calificaciones> ListarCalificaciones();
    public Calificaciones buscarCalificacionesPorId(Integer idcalificaciones);
    public void guardarCalificaciones(Calificaciones calificaciones);
    public  void eliminarCalificaciones(Calificaciones calificaciones);
}
