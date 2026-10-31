package app.estudiante.api;

import app.estudiante.servicio.Servicios.DashboardPromediosServicio;
import app.estudiante.utils.MejorPromedioDTO;

import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/estudiante-app/dashboard")
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardPromediosControlador {

    private final DashboardPromediosServicio servicio;

    public DashboardPromediosControlador(
            DashboardPromediosServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/mejores-promedios/{nivel}")
    public List<MejorPromedioDTO> obtenerMejoresPromedios(
            @PathVariable String nivel) {

        return servicio.obtenerMejoresPromedios(nivel);
    }
}
