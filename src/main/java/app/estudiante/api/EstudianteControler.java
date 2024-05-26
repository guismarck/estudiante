package app.estudiante.api;

import app.estudiante.exception.recurosNoEncontradoException;
import app.estudiante.servicio.InterfacesServicios.IEstudianteServicio;
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
    @GetMapping(path = "/estudiantes")
   public List<Estudiante>obternerEstudiantes(){
        var estudiates = estudianteServicio.ListarEstudiantes();
        estudiates.forEach((estudiante -> logger.info(estudiante.toString())));
        return estudiates;
    }

   @PostMapping(path = "/estudiantes/create") // agregar estudiante
   public ResponseEntity agregarEstudiante(@RequestBody Estudiante estudiante){
       logger.info("el empledo a agregar " + estudiante);
       try {
           estudianteServicio.guardarEstudiante(estudiante);
         return  ResponseEntity.ok("OK");
       }catch (Exception e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }

   }

   @GetMapping(path = "/estudiantes/{id}")// buscar por ID
   public ResponseEntity<Estudiante>getEstudianteId(@PathVariable Integer id){
       if(id == null){
           throw  new recurosNoEncontradoException("No se encontro el grado con Id"+id);
       }
        return  ResponseEntity.ok(estudianteServicio.buscarEstudinatePorId(id));
   }

   @PutMapping(path = "/estudiantes/{id}")// actulizar estudiante
   public ResponseEntity<Estudiante>ActulizarId(@PathVariable Integer id,
                                                @RequestBody Estudiante estudianteResivido){
        Estudiante estudiante = estudianteServicio.buscarEstudinatePorId(id);
       if(estudiante == null){
           throw  new recurosNoEncontradoException("No exite el a actualizar con Id"+id);
       }
       estudiante.setNombre_completo(estudianteResivido.getNombre_completo());
       estudiante.setApellido_completo(estudianteResivido.getApellido_completo());
       estudiante.setSexo(estudianteResivido.getSexo());
       estudiante.setDireccion(estudianteResivido.getDireccion());
       estudiante.setPartidad_nacimiento(estudiante.getPartidad_nacimiento());
       estudiante.setFecha_nacimiento(estudianteResivido.getFecha_nacimiento());
       estudiante.setCedula(estudianteResivido.getCedula());
       estudiante.setCod_estudiante(estudianteResivido.getCod_estudiante());
       estudiante.setCodigo_MINED(estudianteResivido.getCodigo_MINED());
       estudiante.setNombre_tutor(estudianteResivido.getNombre_tutor());
       estudiante.setEstado(estudianteResivido.isEstado());
       estudianteServicio.guardarEstudiante(estudiante);
        return ResponseEntity.ok(estudiante);
   }
   @DeleteMapping(path = "/estudiantes/{id}")// eliminar estudiante
    public  void eliminarEstudinateporID(@PathVariable Integer id){
        estudianteServicio.eliminarEstudinate(estudianteServicio.buscarEstudinatePorId(id));
   }

}
