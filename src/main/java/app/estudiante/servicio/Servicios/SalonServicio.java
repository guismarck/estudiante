package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Salon;
import app.estudiante.repositorio.SalonRepositorio;
import app.estudiante.servicio.InterfacesServicios.ISalonServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SalonServicio implements ISalonServicio {
   @Autowired
   private SalonRepositorio salonRepositorio;
    @Override
    public List<Salon> ListarSalon() {
        List<Salon> salons = salonRepositorio.findAll();
        return salons;
    }

    @Override
    public Salon buscarSalonPorId(Integer idSalon) {
        Salon salon = salonRepositorio.findById(idSalon).orElse(null);
        return salon;
    }

    @Override
    public void guardarSalon(Salon salon) {
        salonRepositorio.save(salon);
    }

    @Override
    public void eliminarSalon(Salon salon) {
        salonRepositorio.delete(salon);
    }
}
