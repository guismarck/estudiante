package app.estudiante.api;

import app.estudiante.exception.recurosNoEncontradoException;
import app.estudiante.modelo.Grado;
import app.estudiante.servicio.InterfacesServicios.IGradoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
////http://localhost:8080/estudiante-app/estudiante/1
@RequestMapping("estudiante-app")
@CrossOrigin(value = "http://localhost:3000")
public class GradoControlador {

    private static final Logger logger = LoggerFactory.getLogger(GradoControlador.class);//Infomacion de la consola
    @Autowired
    private IGradoServicio gradoServicio;
    //http://localhost:8080/estudiante-app/grados
    @GetMapping(path = "/grados")
    public List<Grado> obtenerGrados(){
        var grados = gradoServicio.ListarGrado();

       grados.forEach((grado -> logger.info(grado.toString())));//probado en consola
        return grados;
    }
    ////http://localhost:8080/estudiante-app/grados/update
    @PutMapping(path = "/grados/{id}")
    public  ResponseEntity<Grado>ActulizarporID(@PathVariable Integer id,
                                                @RequestBody Grado gradoResivido){

        Grado grado = gradoServicio.buscarGradoPorId(id);
        if(grado == null){
            throw  new recurosNoEncontradoException("No exite el a actualizar con Id"+id);
        }
       // grado.setIdGrado(gradoResivido.getIdGrado());
        grado.setCategoria(gradoResivido.getCategoria());
        grado.setNombre(gradoResivido.getNombre());
        gradoServicio.guardarGrado(grado);
        return  ResponseEntity.ok(grado);
    }

    @GetMapping(path = "/grados/{id}") //listar por ID
    public ResponseEntity<Grado> getPorGradoId(@PathVariable Integer id){
        if(id == null){
            throw  new recurosNoEncontradoException("No se encontro el grado con Id"+id);
        }
        return ResponseEntity.ok(gradoServicio.buscarGradoPorId(id));

    }

@PostMapping("/grados/create") // agrear grado
    public void agregarGrado(@RequestBody Grado grado){
        logger.info("el empledo a agregar "+grado);
         gradoServicio.guardarGrado(grado);

}

    @DeleteMapping("/grados/{id}")  // elimianr grado
    public void eliminarGradoporID(@PathVariable Integer id) {
        gradoServicio.eliminarGrado(gradoServicio.buscarGradoPorId(id));

    }

}

