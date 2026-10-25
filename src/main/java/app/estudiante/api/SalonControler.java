package app.estudiante.api;

import app.estudiante.modelo.Salon;
import app.estudiante.servicio.InterfacesServicios.ISalonServicio;
import app.estudiante.utils.SalonRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import app.estudiante.exception.recurosNoEncontradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiante-app")
@CrossOrigin(origins = "http://localhost:3000")
public class SalonControler {

    private static final Logger logger =
            LoggerFactory.getLogger(SalonControler.class);

    private final ISalonServicio salonServicio;

    // Inyección de dependencias por constructor
    public SalonControler(ISalonServicio salonServicio) {
        this.salonServicio = salonServicio;
    }

    @GetMapping(path = "/salones", params = "!idGrado")
    public ResponseEntity<List<Salon>> obtenerSalones() {
        return ResponseEntity.ok(salonServicio.listarSalones());
    }

   /* @PutMapping(path = "/salones/{id}")
    public ResponseEntity<Salon> actualizarSalon(
            @PathVariable Integer id,
            @Valid @RequestBody SalonRequestDTO request) {
        return ResponseEntity.ok(salonServicio.actualizarSalon(id, request));
    }*/

    // Listar todos los salones o buscar por grado
    @GetMapping("/salones")
    public ResponseEntity<List<Salon>> obtenerSalones(
            @RequestParam(value = "search", required = false) String search) {

        List<Salon> salones;

        if (search == null || search.trim().isEmpty()) {
            salones = salonServicio.ListarSalon();
        } else {
            try {
                Integer idGrado = Integer.valueOf(search.trim());
                salones = salonServicio
                        .obtenerSalonesDisponiblesPorGrado(idGrado);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().build();
            }
        }

        return ResponseEntity.ok(salones);
    }

    // Registrar un salón
    @PostMapping("/salones/create")
    public ResponseEntity<?> agregarSalon(
            @RequestBody Salon salon) {

        logger.info("Registrando salón: {}", salon);

        try {

            salonServicio.guardarSalon(salon);
        }catch(Exception e){
            ProblemDetail errorBody = ProblemDetail.forStatusAndDetail(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage()
            );

            // Opcional: Puedes agregar un título o propiedades extra si quieres
            errorBody.setTitle("Error al registrar el salon");
           return ResponseEntity.badRequest().body(errorBody);
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Buscar salón por ID
    @GetMapping("/salones/{id}")
    public ResponseEntity<Salon> obtenerSalonPorId(
            @PathVariable Integer id) {

        Salon salon = salonServicio.buscarSalonPorId(id);

        if (salon == null) {
            throw new recurosNoEncontradoException(
                    "No existe el salón con ID " + id);
        }

        return ResponseEntity.ok(salon);
    }

    // Actualizar salón
    @PutMapping("/salones/{id}")
    public ResponseEntity<Salon> actualizarSalonPorId(
            @PathVariable Integer id,
            @RequestBody Salon salonRecibido) {

        Salon salonExistente = salonServicio.buscarSalonPorId(id);

        if (salonExistente == null) {
            throw new recurosNoEncontradoException(
                    "No existe el salón a actualizar con ID " + id);
        }

        salonExistente.setAnioLectivo(
                salonRecibido.getAnioLectivo());

        salonExistente.setTurno(
                salonRecibido.getTurno());

        salonExistente.setSeccion(
                salonRecibido.getSeccion());
        if (salonRecibido.getGrado() != null) {
            salonExistente.setGrado(salonRecibido.getGrado());
        }
        if (salonRecibido.getCatalogoSalon() != null) {
            salonExistente.setCatalogoSalon(salonRecibido.getCatalogoSalon());
        }

        // Guardar los cambios
        salonServicio.guardarSalon(salonExistente);

        return ResponseEntity.ok(salonExistente);
    }

    // Eliminar salón
    @DeleteMapping("/salones/{id}")
    public ResponseEntity<Void> eliminarSalonPorId(
            @PathVariable Integer id) {

        Salon salon = salonServicio.buscarSalonPorId(id);

        if (salon == null) {
            throw new recurosNoEncontradoException(
                    "No existe el salón a eliminar con ID " + id);
        }

        salonServicio.eliminarSalon(salon);

        return ResponseEntity.noContent().build();
    }
}
