package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.Salon;

import java.util.List;

public interface ISalonServicio {
    List<Salon> ListarSalon();

    Salon buscarSalonPorId(Integer idSalon);

    void guardarSalon(Salon salon);

    void eliminarSalon(Salon salon);

    List<Salon> obtenerSalonesDisponiblesPorGrado(Integer idGrado);

}
