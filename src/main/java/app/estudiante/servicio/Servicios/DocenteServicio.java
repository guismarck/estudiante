package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Docente;
import app.estudiante.repositorio.DocenteRepositorio;
import app.estudiante.servicio.InterfacesServicios.IDocenteServicio;
import app.estudiante.utils.NumerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DocenteServicio implements IDocenteServicio {

    @Autowired
    private DocenteRepositorio docenteRepositorio;

    @Override
    public List<Docente> ListarDocente() {
        List<Docente> docentes = docenteRepositorio.findAll();
        return docentes;
    }

    @Override
    public List<Docente> busquedaGeneral(String search) {
        var isInt = NumerUtils.isInt(search);
        if (isInt) return List.of(buscarDocentePorId(Integer.parseInt(search)));
        return  docenteRepositorio.busquedaGeneral(search);
    }

    @Override
    public Docente buscarDocentePorId(Integer idDocente) {
        Docente docente = docenteRepositorio.findById(idDocente).orElse(null);
        return docente;
    }

    @Override
    public void guardarDocente(Docente docente) {
    docenteRepositorio.save(docente);
    }

    @Override
    public void eliminarDocente(Docente docente) {
        docenteRepositorio.delete(docente);
    }
}
