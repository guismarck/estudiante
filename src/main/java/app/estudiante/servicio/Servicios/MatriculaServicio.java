package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Matricula;
import app.estudiante.repositorio.MatriculaRepositorio;
import app.estudiante.servicio.InterfacesServicios.IMatriculaServicio;
import app.estudiante.utils.NumerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MatriculaServicio implements IMatriculaServicio {
    @Autowired
    private MatriculaRepositorio matriculaRepositorio;

    @Override
    public List<Matricula> ListarMatricula() {
        List<Matricula>  matriculas= matriculaRepositorio.findAll();
        return matriculas;
    }

    @Override
    public Matricula buscarMatriculaPorId(Integer idmatricula) {
        Matricula matricula = matriculaRepositorio.findById(idmatricula).orElse(null);
        return matricula;
    }

    @Override
    public List<Matricula> busquedaGeneral(String search){
        var isInt = NumerUtils.isInt(search);
        if (isInt) return List.of(buscarMatriculaPorId(Integer.parseInt(search)));
        return matriculaRepositorio.busquedaGeneral(search);
    }

    @Override
    public void guardarMatricula(Matricula matricula) {
     matriculaRepositorio.save(matricula);
    }

    @Override
    public void eliminarMatricula(Matricula matricula) {
     matriculaRepositorio.delete(matricula);
    }
}
