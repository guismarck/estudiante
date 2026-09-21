package app.estudiante.api;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.estudiante.servicio.InterfacesServicios.IJasperReportService;
import app.estudiante.utils.ReporteRequestDTO;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final IJasperReportService jasperReportService = null;

    /**
     * Endpoint unificado para invocar cualquier reporte alojado en src/main/resources/reportes/
     */
    @PostMapping("/descargar")
    public ResponseEntity<byte[]> descargarReporteGenerico(@Valid @RequestBody ReporteRequestDTO request) {
        byte[] bytes = jasperReportService.generarReporteDesdePlantilla(
                request.nombrePlantilla(),
                request.formato(),
                request.parametros()
        );

        String filename = request.nombrePlantilla() + "." + request.formato().getExtension();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(request.formato().getMediaType());
        headers.setContentDisposition(ContentDisposition.builder(request.formato().getDispositionType()).filename(filename).build());

        return ResponseEntity.ok().headers(headers).body(bytes);
    }
}