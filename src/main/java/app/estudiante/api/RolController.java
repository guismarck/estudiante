package app.estudiante.api;
import app.estudiante.utils.*;
import app.estudiante.servicio.InterfacesServicios.IRolServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("roles")
@RequiredArgsConstructor
public class RolController {

    private final IRolServicio rolServicio;

    @GetMapping
    public ResponseEntity<List<RolResponseDTO>> listarTodos() {
        return ResponseEntity.ok(rolServicio.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolResponseDTO> obtenerPorId(@PathVariable Short id) {
        return ResponseEntity.ok(rolServicio.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<RolResponseDTO> crear(
            @Valid @RequestBody RolRequestDTO dto,
            Principal principal) {
        String usuarioAuditoria = (principal != null) ? principal.getName() : "SYSTEM";
        RolResponseDTO creado = rolServicio.crear(dto, usuarioAuditoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolResponseDTO> actualizar(
            @PathVariable Short id, 
            @Valid @RequestBody RolRequestDTO dto,
            Principal principal) {
        String usuarioAuditoria = (principal != null) ? principal.getName() : "SYSTEM";
        return ResponseEntity.ok(rolServicio.actualizar(id, dto, usuarioAuditoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Short id) {
        rolServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}