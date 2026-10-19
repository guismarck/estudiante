package app.estudiante.api;

import app.estudiante.modelo.Salon;
import app.estudiante.servicio.InterfacesServicios.ISalonServicio;
import app.estudiante.utils.SalonRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("estudiante-app")
@CrossOrigin(value = "http://localhost:3000")
public class SalonControler {

    private final ISalonServicio salonServicio;

    public SalonControler(ISalonServicio salonServicio) {
        this.salonServicio = salonServicio;
    }

    @GetMapping(path = "/salones", params = "!idGrado")
    public ResponseEntity<List<Salon>> obtenerSalones() {
        return ResponseEntity.ok(salonServicio.listarSalones());
    }

    @GetMapping(path = "/salones/{id}")
    public ResponseEntity<Salon> obtenerSalonPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(salonServicio.obtenerSalonPorId(id));
    }

    @PostMapping(path = "/salones")
    public ResponseEntity<Salon> crearSalon(@Valid @RequestBody SalonRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(salonServicio.crearSalon(request));
    }

    @PutMapping(path = "/salones/{id}")
    public ResponseEntity<Salon> actualizarSalon(
            @PathVariable Integer id,
            @Valid @RequestBody SalonRequestDTO request) {
        return ResponseEntity.ok(salonServicio.actualizarSalon(id, request));
    }

    @DeleteMapping(path = "/salones/{id}")
    public ResponseEntity<Void> eliminarSalon(@PathVariable Integer id) {
        salonServicio.eliminarSalonPorId(id);
        return ResponseEntity.noContent().build();
    }
}
