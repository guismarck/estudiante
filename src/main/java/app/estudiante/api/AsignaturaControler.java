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
public class AsignaturaControler {
    private static final
    Logger logger = LoggerFactory.getLogger(AsignaturaControler.class);//Infomacion de la consola
    private   final   IAsignaturaServicio asignaturaServicio;

    // Spring inyecta automáticamente el servicio
    public AsignaturaControler(
            IAsignaturaServicio asignaturaServicio) {
        this.asignaturaServicio = asignaturaServicio;
    }


    //http://localhost:8080/estudiante-app/asignatura
    @GetMapping(path = "/asignatura")
    public  List<Asignatura> obtenerCatalogoSalon(){
        List<Asignatura> asignaturas =
                asignaturaServicio.ListarAsignatura();

        asignaturas.forEach(asignatura ->
                logger.info(asignatura.toString()));

        return asignaturas;
    }
    ////http://localhost:8080/estudiante-app/catalogo/salon/update
    // GET: buscar asignatura por ID
    @GetMapping("/asignatura/{id}")
    public ResponseEntity<Asignatura> getPorSalonId(
            @PathVariable Integer id) {

        Asignatura asignatura =
                asignaturaServicio.buscarAsignaturaPorId(id);

        if (asignatura == null) {
            throw new recurosNoEncontradoException(
                    "No existe la asignatura con ID " + id);
        }

        return ResponseEntity.ok(asignatura);
    }

    // PUT: actualizar asignatura
    @PutMapping("/asignatura/{id}")
    public ResponseEntity<Asignatura> actualizarPorID(
            @PathVariable Integer id,
            @RequestBody Asignatura asignaturaRecibida) {

        Asignatura asignatura =
                asignaturaServicio.buscarAsignaturaPorId(id);

        if (asignatura == null) {
            throw new recurosNoEncontradoException(
                    "No existe la asignatura con ID " + id);
        }

        asignatura.setNombre(asignaturaRecibida.getNombre());
        asignatura.setCodigo(asignaturaRecibida.getCodigo());

        asignaturaServicio.guardarAsignatura(asignatura);

        return ResponseEntity.ok(asignatura);
    }

    // POST: crear asignatura
    @PostMapping("/asignatura/create")
    public void  agregarCatSalon(
            @RequestBody Asignatura asignatura) {

        //logger.info("Asignatura a agregar: {}", asignatura);

        asignaturaServicio.guardarAsignatura(asignatura);


    }

    // DELETE: eliminar asignatura
    @DeleteMapping("/asignatura/{id}")
    public ResponseEntity<Void> eliminarAsignaturaPorID(
            @PathVariable Integer id) {

        Asignatura asignatura =
                asignaturaServicio.buscarAsignaturaPorId(id);

        if (asignatura == null) {
            throw new recurosNoEncontradoException(
                    "No existe la asignatura con ID " + id);
        }

        asignaturaServicio.eliminarAsignatura(asignatura);

        return ResponseEntity.noContent().build();
    }
}
