package app.estudiante.api;
import app.estudiante.servicio.InterfacesServicios.IAutenticacionServicio;
import app.estudiante.utils.LoginRequestDTO;
import app.estudiante.utils.LoginResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AutenticacionController {

    private final IAutenticacionServicio autenticacionServicio;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        LoginResponseDTO respuesta = autenticacionServicio.verificarCredenciales(request);
        return ResponseEntity.ok(respuesta);
    }
}