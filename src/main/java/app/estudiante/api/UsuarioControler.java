package app.estudiante.api;

import app.estudiante.servicio.InterfacesServicios.IUsuarioServicio;
import app.estudiante.utils.AsignarRolesDTO;
import app.estudiante.utils.UsuarioActualizarRequestDTO;
import app.estudiante.utils.UsuarioCrearRequestDTO;
import app.estudiante.utils.UsuarioResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("usuarios")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class UsuarioControler {
    private final IUsuarioServicio usuarioServicio;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(
            @Valid @RequestBody UsuarioCrearRequestDTO dto,
            Principal principal) {
        String usuarioCreador = (principal != null) ? principal.getName() : "SYSTEM";
        UsuarioResponseDTO creado = usuarioServicio.crearUsuario(dto, usuarioCreador);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioServicio.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioServicio.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioActualizarRequestDTO dto) {
        return ResponseEntity.ok(usuarioServicio.actualizarUsuario(id, dto));
    }

    @PutMapping("/asignar-roles")
    public ResponseEntity<UsuarioResponseDTO> asignarRoles(
             @RequestBody AsignarRolesDTO dto,
            Principal principal) {
        String usuarioCreador = (principal != null) ? principal.getName() : "SYSTEM";
        return ResponseEntity.ok(usuarioServicio.asignarRoles(dto, usuarioCreador));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long id, @RequestParam Integer estado) {
        usuarioServicio.cambiarEstado(id, estado);
        return ResponseEntity.noContent().build();
    }

}
