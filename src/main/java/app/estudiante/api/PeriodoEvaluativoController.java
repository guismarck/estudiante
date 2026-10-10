package app.estudiante.api;

import app.estudiante.modelo.PeriodoEvaluativo;
import app.estudiante.servicio.InterfacesServicios.IPeridoEvaluativoServicio;
import app.estudiante.servicio.InterfacesServicios.IcalificacionesServicio;
import app.estudiante.utils.EstadoPeriodoEvaluativoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("estudiante-app")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class PeriodoEvaluativoController {

    private final IPeridoEvaluativoServicio periodoEvaluativoServicio;
    private final IcalificacionesServicio calificacionServicio;

    @GetMapping(path = "/periodos-evaluativos")
    public ResponseEntity<List<PeriodoEvaluativo>> listar() {
        return ResponseEntity.ok(periodoEvaluativoServicio.ListarPeriodoEvaluativo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeriodoEvaluativo> obtenerPorId(@PathVariable Integer id) {
        PeriodoEvaluativo periodo = periodoEvaluativoServicio.buscarPeriodoEvaluativoPorId(id);
        return periodo == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(periodo);
    }

    @GetMapping("/{id}/estado-edicion")
    public ResponseEntity<EstadoPeriodoEvaluativoResponseDTO> obtenerEstadoEdicion(
            @PathVariable Integer id,
            @RequestParam(required = false) Long idDetallePlanDeEstudio) {
        return ResponseEntity.ok(calificacionServicio.obtenerEstadoEdicion(id, idDetallePlanDeEstudio));
    }
}
