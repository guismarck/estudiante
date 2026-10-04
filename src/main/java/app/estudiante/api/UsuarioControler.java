package app.estudiante.api;

import app.estudiante.servicio.InterfacesServicios.IUsuarioServicio;
import app.estudiante.utils.UsuarioRequestDTO;
import app.estudiante.utils.UsuarioResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("estudiante-app")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class UsuarioControler {

    private final IUsuarioServicio usuarioServicio;


    @PostMapping("/createUsuario")
    public ResponseEntity<UsuarioResponseDTO> crear(@RequestBody UsuarioRequestDTO request) {
        UsuarioResponseDTO nuevoUsuario = usuarioServicio.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioServicio.obtenerUsuarioPorId(id));
    }

    @GetMapping("/listarUsuario")
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioServicio.listarTodos());
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody UsuarioRequestDTO request) {
        return ResponseEntity.ok(usuarioServicio.actualizar(id, request));
    }

}
