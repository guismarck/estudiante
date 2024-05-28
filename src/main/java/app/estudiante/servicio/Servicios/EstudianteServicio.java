package app.estudiante.servicio.Servicios;


import app.estudiante.modelo.Estudiante;
import app.estudiante.repositorio.EstudiateRepositorio;
import app.estudiante.servicio.InterfacesServicios.IEstudianteServicio;
import app.estudiante.utils.NumerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EstudianteServicio implements IEstudianteServicio {

 @Autowired
 private EstudiateRepositorio estudiateRepositorio;
    @Override
    public List<Estudiante> ListarEstudiantes() {
        List<Estudiante> estudiantes = estudiateRepositorio.findAll();
         return estudiantes;
    }

    @Override
    public Estudiante buscarEstudinatePorId(Integer idEstudiante) {
        Estudiante estudiante = estudiateRepositorio.findById(idEstudiante).orElse(null);
        return estudiante;
    }

    public List<Estudiante> busquedaGeneral(String search){
        var isInt = NumerUtils.isInt(search);
        if (isInt) return List.of(buscarEstudinatePorId(Integer.parseInt(search)));
        return estudiateRepositorio.busquedaGeneral(search);
    }

    @Override
    public void guardarEstudiante(Estudiante estudiante) {
        estudiateRepositorio.save(estudiante);//agregar o modificar
    }

    @Override
    public void eliminarEstudinate(Estudiante estudiante) {
      estudiateRepositorio.delete(estudiante);
    }
}
