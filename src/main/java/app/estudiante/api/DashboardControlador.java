package app.estudiante.api;

import app.estudiante.servicio.Servicios.DashboardServicio;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import app.estudiante.utils.DashboardResumenDTO;

@RestController
@RequestMapping("/estudiante-app/dashboard")
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardControlador {


    private final DashboardServicio dashboardServicio;

    public DashboardControlador(
            DashboardServicio dashboardServicio) {
        this.dashboardServicio = dashboardServicio;
    }

    @GetMapping("/resumen")
    public DashboardResumenDTO obtenerResumen() {
        return dashboardServicio.obtenerResumen();
    }
}
