package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Grado;
import app.estudiante.repositorio.GradoRepositorio;
import app.estudiante.servicio.InterfacesServicios.IGradoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GradoServicio implements IGradoServicio {

    @Autowired
    private GradoRepositorio gradoRepositorio;
    @Override
    public List<Grado> ListarGrado() {
        List<Grado> grados = gradoRepositorio.findAll();
        return grados;
    }

    @Override
    public Grado buscarGradoPorId(Integer idGrado) {
       Grado grado = gradoRepositorio.findById(idGrado).orElse(null);
        return grado;
    }

    @Override
    public void guardarGrado(Grado grado)   {
       gradoRepositorio.save(grado);
    }

    @Override
    public void eliminarGrado(Grado grado) {
      gradoRepositorio.delete(grado);
    }
}
