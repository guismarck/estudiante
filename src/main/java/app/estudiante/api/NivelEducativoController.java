package app.estudiante.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.estudiante.servicio.InterfacesServicios.INivelEducativoServicio;
import app.estudiante.utils.NivelEducativoRequestDTO;
import app.estudiante.utils.NivelEducativoResponseDTO;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("nivel-educativo")
@CrossOrigin(value = "http://localhost:3000")
public class NivelEducativoController {

    @Autowired
    private INivelEducativoServicio servicio;

    @GetMapping("/")
    public ResponseEntity<List<NivelEducativoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(servicio.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NivelEducativoResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<NivelEducativoResponseDTO> crear(@Valid @RequestBody NivelEducativoRequestDTO dto) {
        NivelEducativoResponseDTO creado = servicio.crear(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getIdnivel())
                .toUri();
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NivelEducativoResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody NivelEducativoRequestDTO dto) {
        return ResponseEntity.ok(servicio.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/reporte-pdf")
    public ResponseEntity<byte[]> descargarReportePdf(@PathVariable Integer id) {
        byte[] pdfBytes = servicio.generarReporteJasper(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline")
                .filename("reporte_nivel_educativo_" + id + ".pdf")
                .build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}