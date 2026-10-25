package app.estudiante.api;

import app.estudiante.exception.recursosNoEncontradoException;
import app.estudiante.servicio.InterfacesServicios.IEstudianteServicio;
import app.estudiante.utils.EstudianteRequestDTO;
import jakarta.validation.Valid;
import app.estudiante.utils.EstudianteRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import app.estudiante.modelo.Estudiante;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Map;
import java.util.Map;
import java.util.Map;
import java.util.Map;

@RestController
@RequestMapping("estudiante-app")
@CrossOrigin(value = "http://localhost:3000")
public class EstudianteControler {
    private static final
    Logger logger = LoggerFactory.getLogger(GradoControlador.class);//Infomacion de la consola

    @Autowired
    private IEstudianteServicio  estudianteServicio;

    //http://localhost:8080/estudiante-app/estudiantes
    @GetMapping(path = "/estudiantes")
   public List<Estudiante>obternerEstudiantes(@RequestParam(value = "search",required = false) String search){
        if (search == null || search.trim().isEmpty())
            return  estudianteServicio.ListarEstudiantes();
        return estudianteServicio.busquedaGeneral(search);
    }

    @GetMapping(path = {"/estudiantes/buscar", "/buscar"})
    public List<Estudiante>obternerEstudiantes(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "criterio", required = false) String criterio){
        String busqueda = criterio != null ? criterio : search;
        if (busqueda == null || busqueda.trim().isEmpty())
            return  estudianteServicio.ListarEstudiantes();
        return estudianteServicio.busquedaGeneral(busqueda.trim());
    }

  /* @PostMapping(path = "/estudiantes/create") // agregar estudiante
   public ResponseEntity agregarEstudiante(@RequestBody Estudiante estudiante){
       logger.info("el estudiante a agregar " + estudiante);
       try {
           estudianteServicio.guardarEstudiante(estudiante);
         return  ResponseEntity.ok("OK");
       }catch (Exception e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }

   }*/

    @PostMapping("/estudiantes/create")
    public ResponseEntity<?> agregarEstudiante(@RequestBody EstudianteRequestDTO estudianteRequest) {
        logger.info("Iniciando registro completo de estudiante y tutores: {}", estudianteRequest);

        try {
            // Una sola llamada al servicio maneja todo el flujo de negocio de forma segura
            Estudiante estudianteRegistrado = estudianteServicio.registrarEstudianteConTutores(estudianteRequest);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(estudianteRegistrado);

        } catch (IllegalArgumentException e) {
            logger.error("Error de validación en la solicitud", e);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error crítico al registrar estudiante y tutores", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Ocurrió un error interno en el servidor: " + e.getMessage()));
        }
    }



   @GetMapping(path = "/estudiantes/{id}")// buscar por ID
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
