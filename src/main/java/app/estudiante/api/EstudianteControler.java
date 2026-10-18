package app.estudiante.api;

import app.estudiante.exception.recursosNoEncontradoException;
import app.estudiante.servicio.InterfacesServicios.IEstudianteServicio;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import app.estudiante.modelo.Estudiante;
import java.util.List;

@RestController
@RequestMapping("estudiante-app")
@CrossOrigin(value = "http://localhost:3000")
public class EstudianteControler {
    private static final
    Logger logger = LoggerFactory.getLogger(GradoControlador.class);//Infomacion de la consola

    @Autowired
    private IEstudianteServicio  estudianteServicio;

    //http://localhost:8080/estudiante-app/estudiantes
    @GetMapping(path = {"/estudiantes/buscar", "/buscar"})
   public List<Estudiante>obternerEstudiantes(
           @RequestParam(value = "search", required = false) String search,
           @RequestParam(value = "criterio", required = false) String criterio){
        String busqueda = criterio != null ? criterio : search;
        if (busqueda == null || busqueda.trim().isEmpty())
            return  estudianteServicio.ListarEstudiantes();
        return estudianteServicio.busquedaGeneral(busqueda.trim());
    }

   @GetMapping(path = "/estudiantes/codigo/{codEstudiante}")
   public ResponseEntity<Estudiante> getEstudiantePorCodigo(@PathVariable String codEstudiante) {
       return ResponseEntity.ok(estudianteServicio.buscarEstudiantePorCodigo(codEstudiante));
   }

   @PostMapping(path = "/estudiantes/create") // agregar estudiante
   public ResponseEntity<String> agregarEstudiante(@Valid @RequestBody Estudiante estudiante){
       logger.info("el estudiante a agregar " + estudiante);
       try {
           estudianteServicio.guardarEstudiante(estudiante);
         return  ResponseEntity.ok("OK");
       }catch (Exception e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }

   }

   @GetMapping(path = "/estudiantes/{id:\\d+}")// buscar por ID
   public ResponseEntity<Estudiante>getEstudianteId(@PathVariable Integer id){
       if(id == null){
           throw  new recursosNoEncontradoException("No se encontro el grado con Id"+id);
       }
        return  ResponseEntity.ok(estudianteServicio.buscarEstudinatePorId(id));
   }

   @PutMapping(path = "/estudiantes/{id:\\d+}")// actulizar estudiante
   public ResponseEntity<Estudiante>ActulizarId(@PathVariable Integer id,
                                                @Valid @RequestBody Estudiante estudianteResivido){
        Estudiante estudiante = estudianteServicio.buscarEstudinatePorId(id);
       if(estudiante == null){
           throw  new recursosNoEncontradoException("No exite el a actualizar con Id"+id);
       }
       estudiante.setNombre_completo(estudianteResivido.getNombre_completo());
       estudiante.setApellido_completo(estudianteResivido.getApellido_completo());
       estudiante.setSexo(estudianteResivido.getSexo());
       estudiante.setDireccion(estudianteResivido.getDireccion());
       estudiante.setPartida_nacimiento(estudiante.getPartida_nacimiento());
       estudiante.setFecha_nacimiento(estudianteResivido.getFecha_nacimiento());
       estudiante.setCedula(estudianteResivido.getCedula());
       estudiante.setCodEstudiante(estudianteResivido.getCodEstudiante());
       estudiante.setCodigoMined(estudianteResivido.getCodigoMined());
       estudiante.setEstado(estudianteResivido.getEstado());
       estudianteServicio.guardarEstudiante(estudiante);
        return ResponseEntity.ok(estudiante);
   }

   @DeleteMapping(path = "/estudiantes/{id:\\d+}")// eliminar estudiante
    public  void eliminarEstudinateporID(@PathVariable Integer id){
        estudianteServicio.eliminarEstudinate(estudianteServicio.buscarEstudinatePorId(id));
   }

}
