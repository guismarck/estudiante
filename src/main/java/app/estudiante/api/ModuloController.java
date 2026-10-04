package app.estudiante.api;

import app.estudiante.servicio.InterfacesServicios.IModuloService;
import app.estudiante.utils.seguridad.ModuloUsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("modulo")
@RequiredArgsConstructor
public class ModuloController {
    private final IModuloService seguridadService;
    /**
     * Retorna la jerarquía de módulos y permisos asignados al usuario autenticado.
     * Endpoint: GET /seguridad/modulos-usuario
     */
    @GetMapping("/modulos-usuario")
    public ResponseEntity<List<ModuloUsuarioDTO>> getModulosUsuario(Authentication authentication) {
        // Extrae el rol primario del contexto JWT de Spring Security
        String rolCodigo = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(role -> role.replace("ROLE_", ""))
                .findFirst()
                .orElse("DOCENTE");

        List<ModuloUsuarioDTO> modulos = seguridadService.obtenerModulosPorRol(rolCodigo);
        return ResponseEntity.ok(modulos);
    }
}
