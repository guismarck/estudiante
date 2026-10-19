package app.estudiante.api;

import app.estudiante.exception.recursosNoEncontradoException;
import app.estudiante.modelo.Docente;
import app.estudiante.servicio.InterfacesServicios.IDocenteServicio;
import app.estudiante.utils.DocenteAsignacionResponseDTO;
import app.estudiante.utils.DocenteAsignacionRequestDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("estudiante-app")
@CrossOrigin(value = "http://localhost:3000")
public class DocenteControler {
    private static final
    Logger logger = LoggerFactory.getLogger(GradoControlador.class);//Infomacion de la consol

    @Autowired
    private IDocenteServicio docenteServicio;

    //http://localhost:8080/estudiante-app/docentes
    @GetMapping(path = "/docentes")
    public List<Docente> obternerDocentes(@RequestParam(value = "search",required = false) String search){
        if (search == null || search.trim().isEmpty())
            return  docenteServicio.ListarDocente();
        return docenteServicio.busquedaGeneral(search);
    }

    @GetMapping(path = "/docentes/mis-asignaciones")
    public ResponseEntity<List<DocenteAsignacionResponseDTO>> obtenerMisAsignaciones(
            @RequestParam(required = false) Integer idDocente,
            @RequestParam(required = false) String codigoDocente) {
        boolean tieneId = idDocente != null;
        boolean tieneCodigo = codigoDocente != null && !codigoDocente.isBlank();
        if (tieneId == tieneCodigo) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Envía exactamente uno de estos parámetros: idDocente o codigoDocente.");
        }
        return ResponseEntity.ok(
                docenteServicio.obtenerMisAsignaciones(idDocente, codigoDocente));
    }

    @PostMapping(path = "/docentes/asignar-carga")
    public ResponseEntity<DocenteAsignacionResponseDTO> asignarCarga(
            @Valid @RequestBody DocenteAsignacionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(docenteServicio.asignarCarga(request));
    }

    @DeleteMapping(path = "/docentes/{idDocente}/carga-academica/{idDetallePlanDeEstudio}")
    public ResponseEntity<Void> eliminarCargaAcademica(
            @PathVariable Integer idDocente,
            @PathVariable Long idDetallePlanDeEstudio) {
        docenteServicio.eliminarCargaAcademica(idDocente, idDetallePlanDeEstudio);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/docentes/create") // agregar docente
    public void agregarDocente(@RequestBody Docente  docente){
       // logger.info("el empledo a agregar " + docente);
        docenteServicio.guardarDocente(docente);
    }

    @GetMapping(path = "/docentes/{id:\\d+}")// buscar por ID
    public ResponseEntity<Docente> getDocenteId(@PathVariable Integer id){
        if(id == null){
            throw  new recursosNoEncontradoException("No se encontro el grado con Id"+id);
        }
        return  ResponseEntity.ok(docenteServicio.buscarDocentePorId(id));
    }

    @PutMapping(path = "/docentes/{id:\\d+}")// actulizar estudiante
    public ResponseEntity<Docente>ActulizarId(@PathVariable Integer id,
                                              @RequestBody Docente docenteResivido){
        Docente docente = docenteServicio.buscarDocentePorId(id);
        if(docente == null){
            throw  new recursosNoEncontradoException("No exite el a actualizar con Id"+id);
        }
        docente.setNombre_completo(docenteResivido.getNombre_completo());
        docente.setApellido_completo(docenteResivido.getApellido_completo());
        docente.setSexo(docenteResivido.getSexo());
        docente.setDireccion(docenteResivido.getDireccion());
        docente.setCedula(docenteResivido.getCedula());
        docente.setEspecialidad(docenteResivido.getEspecialidad());
        docente.setEstado(docenteResivido.getEstado());
        docente.setCodDocente(docenteResivido.getCodDocente());
        docente.setFecha_nacimiento(docenteResivido.getFecha_nacimiento());
        docenteServicio.guardarDocente(docente);
        //docente.setPart(docenteResivido.getPartidad_nacimiento());
        //
        return ResponseEntity.ok(docente);
    }

    @DeleteMapping(path = "/docentes/{id:\\d+}")// eliminar docente
    public  void eliminarDocenteID(@PathVariable Integer id){
        docenteServicio.eliminarDocente(docenteServicio.buscarDocentePorId(id));
    }

}
