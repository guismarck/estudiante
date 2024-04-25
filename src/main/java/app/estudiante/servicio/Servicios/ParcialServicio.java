package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Parcial;
import app.estudiante.repositorio.ParcialRepositorio;
import app.estudiante.servicio.InterfacesServicios.IParcialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ParcialServicio implements IParcialServicio {
    @Autowired
    private ParcialRepositorio parcialRepositorio;
    @Override
    public List<Parcial> ListarParcial() {
        List<Parcial> parcials = parcialRepositorio.findAll();
        return parcials ;
    }

    @Override
    public Parcial buscarParcialPorId(Integer idParcial) {
        Parcial parcial = parcialRepositorio.findById(idParcial).orElse(null);
        return parcial;
    }

    @Override
    public void guardarParcial(Parcial parcial) {
        parcialRepositorio.save(parcial);
    }

    @Override
    public void eliminarParcial(Parcial parcial) {
        parcialRepositorio.delete(parcial);
    }
}
