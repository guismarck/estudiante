package app.estudiante.servicio.Servicios;


import app.estudiante.modelo.Estudiante;
import app.estudiante.exception.recurosNoEncontradoException;
import app.estudiante.repositorio.EstudianteRepositorio;
import app.estudiante.servicio.InterfacesServicios.IEstudianteServicio;
import app.estudiante.utils.NumerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class EstudianteServicio implements IEstudianteServicio {

 @Autowired
 private EstudianteRepositorio estudianteRepositorio;
    @Override
    public List<Estudiante> ListarEstudiantes() {
        List<Estudiante> estudiantes = estudianteRepositorio.findAll();
         return estudiantes;
    }

    @Override
    public Estudiante buscarEstudinatePorId(Integer idEstudiante) {
        Estudiante estudiante = estudianteRepositorio.findById(idEstudiante).orElse(null);
        return estudiante;
    }

    @Override
    public Estudiante buscarEstudiantePorCodigo(String codEstudiante) {
        return estudianteRepositorio.findByCodEstudiante(codEstudiante)
                .orElseThrow(() -> new recurosNoEncontradoException(
                        "No se encontró un estudiante con código " + codEstudiante));
    }

    public List<Estudiante> busquedaGeneral(String search){
        List<Estudiante> estudiantes = new ArrayList<>(estudianteRepositorio.busquedaGeneral(search));
        if (NumerUtils.isInt(search)) {
            estudianteRepositorio.findById(Integer.parseInt(search)).ifPresent(estudiante -> {
                boolean yaIncluido = estudiantes.stream()
                        .anyMatch(resultado -> resultado.getIdpersona().equals(estudiante.getIdpersona()));
                if (!yaIncluido) {
                    estudiantes.add(0, estudiante);
                }
            });
        }
        return estudiantes;
    }

    @Override
    public void guardarEstudiante(Estudiante estudiante) {
        estudianteRepositorio.save(estudiante);//agregar o modificar
    }

    @Override
    public void eliminarEstudinate(Estudiante estudiante) {
      estudianteRepositorio.delete(estudiante);
    }
}
