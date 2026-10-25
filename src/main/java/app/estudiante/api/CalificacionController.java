package app.estudiante.api;

import app.estudiante.servicio.InterfacesServicios.IcalificacionesServicio;
import app.estudiante.utils.CalificacionBatchRequestDTO;
import app.estudiante.utils.ComboItemDTO;
import app.estudiante.utils.NominaEstudianteResponseDTO;
import app.estudiante.utils.RespuestaMensajeDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("estudiante-app")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@Validated
public class CalificacionController {

    private final IcalificacionesServicio calificacionServicio;

    @GetMapping(path = "/nomina")
    public ResponseEntity<List<NominaEstudianteResponseDTO>> obtenerNominaEstudiantes(
            @RequestParam(required = false) Integer idGrado,
            @RequestParam @NotNull(message = "El salón es requerido") Integer idSalon,
            @RequestParam(name = "iddetallePlanDeEstudio", required = false) Long idDetallePlanDeEstudio,
            @RequestParam(required = false) Integer idPeriodoEvaluativo,
            @RequestParam(required = false) Integer idDocente) {

        return ResponseEntity.ok(calificacionServicio.obtenerNominaPorGradoYSalon(
                idGrado, idSalon, idDetallePlanDeEstudio, idPeriodoEvaluativo, idDocente));
    }

    @GetMapping(path = "/salones/{idSalon}/estudiantes")
    public ResponseEntity<List<NominaEstudianteResponseDTO>> obtenerEstudiantesActivosPorSalon(
            @PathVariable Integer idSalon,
            @RequestParam(required = false) Integer idGrado,
            @RequestParam(name = "iddetallePlanDeEstudio", required = false) Long idDetallePlanDeEstudio,
            @RequestParam(required = false) Integer idPeriodoEvaluativo,
            @RequestParam(required = false) Integer idDocente) {
        return ResponseEntity.ok(calificacionServicio.obtenerNominaPorGradoYSalon(
                idGrado, idSalon, idDetallePlanDeEstudio, idPeriodoEvaluativo, idDocente));
    }

    @PostMapping(path = "/batch")
    public ResponseEntity<RespuestaMensajeDTO> guardarCalificacionesBatch(
            @Valid @RequestBody CalificacionBatchRequestDTO payload) {

        calificacionServicio.guardarCalificacionesBatch(payload, "SISTEMA_DOCENTE");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RespuestaMensajeDTO(true, "Calificaciones registradas correctamente en el SIGE."));
    }

    @GetMapping(path = "/niveles")
    public ResponseEntity<List<ComboItemDTO>> obtenerNivelesCombo() {
        return ResponseEntity.ok(calificacionServicio.obtenerNivelesCombo());
    }

    @GetMapping(path = "/calificaciones/grados")
    public ResponseEntity<List<ComboItemDTO>> obtenerGradosCombo(
            @RequestParam(required = false) Integer idNivel) {
        return ResponseEntity.ok(calificacionServicio.obtenerGradosCombo(idNivel));
    }

    @GetMapping(path = "/salone")
    public ResponseEntity<List<ComboItemDTO>> obtenerSalonesCombo(
            @RequestParam @NotNull(message = "El idGrado es requerido") Integer idGrado,
            @RequestParam(required = false) Integer anioLectivo) {
        int anio = anioLectivo == null ? Year.now().getValue() : anioLectivo;
        return ResponseEntity.ok(calificacionServicio.obtenerSalonesCombo(idGrado, anio));
    }

    @GetMapping(path = "/asignaturas")
    public ResponseEntity<List<ComboItemDTO>> obtenerAsignaturasSeccionesCombo(
            @RequestParam @NotNull(message = "El idGrado es requerido") Integer idGrado,
            @RequestParam @NotNull(message = "El docente es requerido") Integer idDocente,
            @RequestParam(required = false) Integer idSalon) {
        return ResponseEntity.ok(calificacionServicio.obtenerAsignaturasSeccionesCombo(
                idGrado, idSalon, idDocente));
    }

    @GetMapping(path = "/periodos")
    public ResponseEntity<List<ComboItemDTO>> obtenerPeriodosEvaluativosCombo(
            @RequestParam(required = false) Integer anioLectivo) {
        int anio = anioLectivo == null ? Year.now().getValue() : anioLectivo;
        return ResponseEntity.ok(calificacionServicio.obtenerPeriodosEvaluativosCombo(anio));
    }

    @GetMapping(path = "/reportes/acta-pdf")
    public ResponseEntity<byte[]> descargarActaCalificacionesPDF(
            @RequestParam @NotNull(message = "El detalle del plan de estudio es requerido") Long iddetallePlanDeEstudio,
            @RequestParam @NotNull(message = "El período evaluativo es requerido") Integer idPeriodoEvaluativo) {

        byte[] pdfReporte = calificacionServicio.generarActaCalificacionesPdf(
                iddetallePlanDeEstudio, idPeriodoEvaluativo);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(
                "attachment",
                String.format("Acta_Calificaciones_Plan_%d_P%d.pdf",
                        iddetallePlanDeEstudio, idPeriodoEvaluativo));

        return new ResponseEntity<>(pdfReporte, headers, HttpStatus.OK);
    }
}
