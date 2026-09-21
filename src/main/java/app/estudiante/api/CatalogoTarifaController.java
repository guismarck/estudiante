package app.estudiante.api;

import app.estudiante.modelo.CatalogoTarifa;
import app.estudiante.servicio.InterfacesServicios.ICatalogoTarifaServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("estudiante-app")
@CrossOrigin(
    origins = "https://miniature-space-enigma-65prg5v459vh46xx-3000.app.github.dev"
)
public class CatalogoTarifaController {
    @Autowired
    private final ICatalogoTarifaServicio tarifaServicio = null;

    @GetMapping
    public ResponseEntity<List<CatalogoTarifa>> listarTodas(@RequestParam(required = false) Integer anioLectivo) {
        List<CatalogoTarifa> tarifas = (anioLectivo != null)
                ? tarifaServicio.obtenerPorAnio(anioLectivo)
                : tarifaServicio.obtenerTodas();
        return ResponseEntity.ok(tarifas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatalogoTarifa> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(tarifaServicio.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<CatalogoTarifa> crear(@Valid @RequestBody CatalogoTarifa tarifa) {
        CatalogoTarifa nuevaTarifa = tarifaServicio.crear(tarifa);
        return new ResponseEntity<>(nuevaTarifa, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CatalogoTarifa> actualizar(
            @PathVariable Integer id, 
            @Valid @RequestBody CatalogoTarifa tarifa) {
        return ResponseEntity.ok(tarifaServicio.actualizar(id, tarifa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        tarifaServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reportes/pdf")
    public ResponseEntity<byte[]> descargarReportePdf(@RequestParam Integer anioLectivo) {
        byte[] pdfContent = tarifaServicio.generarReporteTarifasPdf(anioLectivo);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "Arancel_Escolar_" + anioLectivo + ".pdf");

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }

    @GetMapping("/buscar")
    public ResponseEntity<CatalogoTarifa> obtenerTarifaPorGradoYConcepto(
            @RequestParam(name = "idGrado") Integer idGrado,
            @RequestParam(name = "anioLectivo") Integer anioLectivo,
            @RequestParam(name = "concepto") String concepto) {
               // Year anio = Year.of(anioLectivo);
        CatalogoTarifa tarifa = tarifaServicio.buscarPorGradoAnioYConcepto(idGrado, anioLectivo, concepto);
        return ResponseEntity.ok(tarifa);
    }
}