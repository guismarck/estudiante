package app.estudiante.api;

import app.estudiante.exception.recurosNoEncontradoException;
import app.estudiante.modelo.Estudiante;
import app.estudiante.modelo.Grado;
import app.estudiante.modelo.Matricula;
import app.estudiante.servicio.InterfacesServicios.IMatriculaServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("estudiante-app")
@CrossOrigin(value = "http://localhost:3000")
public class MatriculaControler {
    private static final
    Logger logger = LoggerFactory.getLogger(MatriculaControler.class);//Infomacion de la consola

    @Autowired
    private IMatriculaServicio matriculaServicio;

    //http://localhost:8080/estudiante-app/matriculas
      @GetMapping(path = "/matriculas")//Busqueda general
    public List<Matricula> obternerMatriculas(@RequestParam(value = "search",required = false) String search){
        if (search == null || search.trim().isEmpty())
            return  matriculaServicio.ListarMatricula();
        return matriculaServicio.busquedaGeneral(search);
    }

    @GetMapping(path = "/matriculas/{id}")// buscar por ID
    public ResponseEntity<Matricula> getMatriculaId(@PathVariable Integer id){
        if(id == null){
            throw  new recurosNoEncontradoException("No se encontro el grado con Id"+id);
        }
        return  ResponseEntity.ok(matriculaServicio.buscarMatriculaPorId(id));
    }

    @PostMapping(path = "/matriculas/create") // agregar matriculas
    public ResponseEntity agregarEstudiante(@RequestBody Matricula matricula){
        logger.info("la matricula ingresada" + matricula);
        try {
            matriculaServicio.guardarMatricula(matricula);
            return  ResponseEntity.ok("OK");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping(path = "/matriculas/{id}")// actulizar matricula
    public ResponseEntity<Matricula>ActulizarId(@PathVariable Integer id,
                                                 @RequestBody Matricula matriculaResivido){
        Matricula matricula = matriculaServicio.buscarMatriculaPorId(id);
        if(matricula == null){
            throw  new recurosNoEncontradoException("No exite el a actualizar con Id"+id);
        }
        matricula.setTurno(matriculaResivido.getTurno());
        matricula.setCosto_matricula(matriculaResivido.getCosto_matricula());
        matriculaServicio.guardarMatricula(matricula);
        return ResponseEntity.ok(matricula);
    }

    @DeleteMapping(path = "/matriculas/{id}")// eliminar estudiante
    public  void eliminarMatriculaporID(@PathVariable Integer id){
        matriculaServicio.eliminarMatricula(matriculaServicio.buscarMatriculaPorId(id));
    }

}
