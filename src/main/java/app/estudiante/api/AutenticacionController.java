package app.estudiante.api;

import app.estudiante.servicio.InterfacesServicios.IAutenticacionServicio;
import app.estudiante.utils.LoginRequestDTO;
import app.estudiante.utils.LoginResponseDTO;
import app.estudiante.utils.RespuestaOTPDTO;
import app.estudiante.utils.SolicitudOTPDTO;
import app.estudiante.utils.VerificarOTPDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping("/solicitar-otp")
    public ResponseEntity<RespuestaOTPDTO> solicitarCodigoOTP(@Valid @RequestBody SolicitudOTPDTO solicitud) {
        return ResponseEntity.ok(autenticacionServicio.solicitarCodigoOTP(solicitud));
    }

    @PostMapping("/verificar-otp")
    public ResponseEntity<Map<String, String>> verificarOTPYCambiarPassword(
            @Valid @RequestBody VerificarOTPDTO verificarDto) {
        autenticacionServicio.verificarOTPYCambiarPassword(verificarDto);
        return ResponseEntity.ok(Map.of("mensaje", "Contraseña actualizada con éxito. Ya puede iniciar sesión."));
    }
}