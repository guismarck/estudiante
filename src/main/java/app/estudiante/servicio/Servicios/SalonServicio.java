package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Salon;
import app.estudiante.repositorio.SalonRepositorio;
import app.estudiante.servicio.InterfacesServicios.ISalonServicio;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SalonServicio implements ISalonServicio {

    private final SalonRepositorio salonRepositorio;

    public SalonServicio(SalonRepositorio salonRepositorio) {
        this.salonRepositorio = salonRepositorio;
    }

    @Override
    public List<Salon> ListarSalon() {
        return salonRepositorio.findAll();
    }

    @Override
    public Salon buscarSalonPorId(Integer idSalon) {
        return salonRepositorio.findById(idSalon).orElse(null);
    }

    @Override
    public void guardarSalon(Salon salon) {
        salonRepositorio.save(salon);
    }

    @Override
    public void eliminarSalon(Salon salon) {
        salonRepositorio.delete(salon);
    }

    @Override
    public List<Salon> obtenerSalonesDisponiblesPorGrado(
            Integer idGrado) {
        return salonRepositorio
                .obtenerSalonesDisponiblesPorGrado(idGrado);
    }
}