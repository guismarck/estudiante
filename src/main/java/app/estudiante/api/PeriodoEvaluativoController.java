package app.estudiante.api;

import app.estudiante.servicio.InterfacesServicios.IPeridoEvaluativoServicio;
import app.estudiante.servicio.InterfacesServicios.IcalificacionesServicio;
import app.estudiante.utils.EstadoPeriodoEvaluativoResponseDTO;
import app.estudiante.utils.PeriodoEvaluativoRequestDTO;
import app.estudiante.utils.PeriodoEvaluativoResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("periodo-evaluativo")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class PeriodoEvaluativoController {

    private final IPeridoEvaluativoServicio periodoEvaluativoServicio;
    private final IcalificacionesServicio calificacionServicio;

    @GetMapping
    public ResponseEntity<List<PeriodoEvaluativoResponseDTO>> listar() {
        return ResponseEntity.ok(periodoEvaluativoServicio.listarPeriodosEvaluativos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeriodoEvaluativoResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(periodoEvaluativoServicio.buscarPeriodoEvaluativoPorId(id));
    }

    @GetMapping("/{id}/estado-edicion")
    public ResponseEntity<EstadoPeriodoEvaluativoResponseDTO> obtenerEstadoEdicion(
            @PathVariable Integer id,
            @RequestParam(required = false) Long idDetallePlanDeEstudio) {
        return ResponseEntity.ok(calificacionServicio.obtenerEstadoEdicion(id, idDetallePlanDeEstudio));
    }

    @PostMapping
    public ResponseEntity<PeriodoEvaluativoResponseDTO> crear(
            @Valid @RequestBody PeriodoEvaluativoRequestDTO requestDTO) {
        PeriodoEvaluativoResponseDTO creado = periodoEvaluativoServicio.crearPeriodoEvaluativo(requestDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getIdPeriodoEvaluativo())
                .toUri();

        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeriodoEvaluativoResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody PeriodoEvaluativoRequestDTO requestDTO) {
        PeriodoEvaluativoResponseDTO actualizado = periodoEvaluativoServicio.actualizarPeriodoEvaluativo(id, requestDTO);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        periodoEvaluativoServicio.eliminarPeriodoEvaluativo(id);
        return ResponseEntity.noContent().build();
    }
}
