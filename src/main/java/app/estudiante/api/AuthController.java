package app.estudiante.api;

import app.estudiante.servicio.InterfacesServicios.IUsuarioServicio;
import app.estudiante.utils.seguridad.AuthRequestDTO;
import app.estudiante.utils.seguridad.AuthResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("estudiante-app")
@RequiredArgsConstructor
public class AuthController {

    private final IUsuarioServicio usuarioServicio;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        AuthResponseDTO response = usuarioServicio.autenticar(request);
        return ResponseEntity.ok(response);
    }
}
