package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.Salon;
import app.estudiante.utils.SalonRequestDTO;

import java.util.List;

public interface ISalonServicio {
    public List<Salon> ListarSalon();
    List<Salon> listarSalones();
    public Salon buscarSalonPorId(Integer idSalon);
    Salon obtenerSalonPorId(Integer idSalon);
    public void guardarSalon(Salon salon);
    public  void eliminarSalon(Salon salon);
    Salon crearSalon(SalonRequestDTO request);
    Salon actualizarSalon(Integer idSalon, SalonRequestDTO request);
    void eliminarSalonPorId(Integer idSalon);
    List<Salon> obtenerSalonesDisponiblesPorGrado(Integer idGrado);
}
