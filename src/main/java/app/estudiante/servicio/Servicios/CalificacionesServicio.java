package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Calificaciones;
import app.estudiante.repositorio.CalificacionesRepositorio;
import app.estudiante.servicio.InterfacesServicios.IcalificacionesServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CalificacionesServicio implements IcalificacionesServicio {
    @Autowired
    private CalificacionesRepositorio calificacionesRepositorio;
    @Override
    public List<Calificaciones> ListarCalificaciones() {
        List<Calificaciones> calificaciones = calificacionesRepositorio.findAll();
        return calificaciones;
    }

    @Override
    public Calificaciones buscarCalificacionesPorId(Integer idcalificaciones) {
        Calificaciones calificaciones = calificacionesRepositorio.findById(idcalificaciones).orElse(null);
        return calificaciones;
    }

    @Override
    public void guardarCalificaciones(Calificaciones calificaciones) {
     calificacionesRepositorio.save(calificaciones);
    }

    @Override
    public void eliminarCalificaciones(Calificaciones calificaciones){
        calificacionesRepositorio.delete(calificaciones);
    }


}
