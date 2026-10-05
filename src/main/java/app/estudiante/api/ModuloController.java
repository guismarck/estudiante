package app.estudiante.api;
import app.estudiante.servicio.InterfacesServicios.IModuloService;
import app.estudiante.utils.seguridad.ModuloMenuDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/modulos")
public class ModuloController {

    private final IModuloService moduloServicio;

    public ModuloController(IModuloService moduloServicio) {
        this.moduloServicio = moduloServicio;
    }

    @GetMapping("/usuario-menu")
    public ResponseEntity<List<ModuloMenuDTO>> obtenerMenuUsuario(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(moduloServicio.obtenerMenuPorUsuario(userDetails.getUsername()));
    }

    @GetMapping("/usuario-rutas")
    public ResponseEntity<List<ModuloMenuDTO>> obtenerRutasPlanasUsuario(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(moduloServicio.obtenerRutasPlanasPorUsuario(userDetails.getUsername()));
    }
}