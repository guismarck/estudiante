package app.estudiante.api;

import app.estudiante.exception.recurosNoEncontradoException;
import app.estudiante.modelo.Asignatura;
import app.estudiante.servicio.InterfacesServicios.IAsignaturaServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
////http://localhost:8080/estudiante-app/asignatura/1
@RequestMapping("estudiante-app")
@CrossOrigin(value = "http://localhost:3000")
public class AsignaturaControler { private static final
    Logger logger = LoggerFactory.getLogger(AsignaturaControler.class);//Infomacion de la consola
    private IAsignaturaServicio asignaturaServicio;

    //http://localhost:8080/estudiante-app/asignatura
    @GetMapping(path = "/asignatura")
    public List<Asignatura> obtenerCatalogoSalon(){
        var asignatura = asignaturaServicio.ListarAsignatura();

        asignatura.forEach((salon -> logger.info(salon.toString())));//probado en consola
        return asignatura;
    }
    ////http://localhost:8080/estudiante-app/catalogo/salon/update
    @PutMapping(path = "/asignatura/{id}")
    public ResponseEntity<Asignatura> ActualizarporID(@PathVariable Integer id,
                                                         @RequestBody Asignatura asignaturaRecibido){
        Asignatura asignatura = asignaturaServicio.buscarAsignaturaPorId(id);
        if(asignatura == null){
            throw  new recurosNoEncontradoException("No existe el salon a actualizar con Id"+id);
        }
        // cat_salon.setIdcatalogo_Salon(gradoResivido.getIdGrado());
        asignatura.setNombre(asignaturaRecibido.getNombre());
        asignaturaServicio.guardarAsignatura(asignatura);
        return  ResponseEntity.ok(asignatura);
    }

    @GetMapping(path = "/asignatura/{id}") //listar por ID
    public ResponseEntity<Asignatura> getPorSalonId(@PathVariable Integer id){
        if(id == null){
            throw  new recurosNoEncontradoException("No se encontro el salon con Id"+id);
        }
        return ResponseEntity.ok(asignaturaServicio.buscarAsignaturaPorId(id));
    }

    @PostMapping("/asignatura/create") // agregar asignatura
    public void agregarCatSalon(@RequestBody Asignatura asignatura){
        logger.info("el salon a agregar "+asignatura);
        asignaturaServicio.guardarAsignatura(asignatura);

    }

    @DeleteMapping("/asignatura/{id}")  // eliminar asignatura
    public void eliminarAsignaturaPorID(@PathVariable Integer id) {
        asignaturaServicio.eliminarAsignatura(asignaturaServicio.buscarAsignaturaPorId(id));
    }
}
