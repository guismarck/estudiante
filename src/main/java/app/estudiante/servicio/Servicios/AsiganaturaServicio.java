package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Asignatura;
import app.estudiante.repositorio.AsignaturaRepositorio;
import app.estudiante.servicio.InterfacesServicios.IAsignaturaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AsiganaturaServicio implements IAsignaturaServicio {
    @Autowired

    private AsignaturaRepositorio asignaturaRepositorio;
    @Override
    public List<Asignatura> ListarAsignatura() {
        List<Asignatura> asignaturas = asignaturaRepositorio.findAll();
        return asignaturas;
    }

    @Override
    public Asignatura buscarAsignaturaPorId(Integer idAsignatura) {
        Asignatura asignatura =asignaturaRepositorio.findById(idAsignatura).orElse(null);
        return asignatura;
    }

    @Override
    public void guardarAsignatura(Asignatura asignatura) {
       asignaturaRepositorio.save(asignatura);
    }

    @Override
    public void eliminarAsignatura(Asignatura asignatura) {
       asignaturaRepositorio.delete(asignatura);
    }
}
