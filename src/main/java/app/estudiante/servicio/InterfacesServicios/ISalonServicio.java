package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.Salon;

import java.util.List;

public interface ISalonServicio {
    public List<Salon> ListarSalon();
    public Salon buscarSalonPorId(Integer idSalon);
    public void guardarSalon(Salon salon);
    public  void eliminarSalon(Salon salon);
}
