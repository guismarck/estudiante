package app.estudiante.api;


import app.estudiante.exception.recursosNoEncontradoException;
import app.estudiante.modelo.Tutor;

import app.estudiante.servicio.InterfacesServicios.ITutorServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("estudiante-app")
@CrossOrigin(value = "http://localhost:3000")

public class TutorControler {

    Logger logger = LoggerFactory.getLogger(GradoControlador.class);//Infomacion de la consola
    @Autowired
    private ITutorServicio  tutorServicio;


    //http://localhost:8080/estudiante-app/estudiantes
    @GetMapping(path = "/tutores")
    public List<Tutor>obternerTutores(@RequestParam(value = "search",required = false) String search){
        if (search == null || search.trim().isEmpty())
            return  tutorServicio.ListarTutor();
        return tutorServicio.busquedaGeneral(search);
    }

    @PostMapping(path = "/tutores/create") // agregar tutores
    public ResponseEntity agregarTutores(@RequestBody Tutor tutor){
        logger.info("el estudiante a agregar " + tutor);
        try {
            tutorServicio.guardarTutor(tutor);
            return  ResponseEntity.ok("OK");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping(path = "/tutores/{id}")// buscar por ID
    public ResponseEntity<Tutor>getTutorId(@PathVariable Integer id){
        if(id == null){
            throw  new recursosNoEncontradoException("No se encontro el tutor con Id"+id);
        }
        return  ResponseEntity.ok(tutorServicio.buscarTutorPorId(id));
    }

    @PutMapping(path = "/tutores/{id}")// actulizar tutores
    public ResponseEntity<Tutor>ActulizarId(@PathVariable Integer id,
                                                 @RequestBody Tutor tutorResivido){
        Tutor tutor = tutorServicio.buscarTutorPorId(id);
        if(tutor == null){
            throw  new recursosNoEncontradoException("No exite el a actualizar con Id"+id);
        }
        tutor.setNombre_completo(tutorResivido.getNombre_completo());
        tutor.setApellido_completo(tutorResivido.getApellido_completo());
        tutor.setSexo(tutorResivido.getSexo());
        tutor.setDireccion(tutorResivido.getDireccion());
        tutor.setPartida_nacimiento(tutorResivido.getPartida_nacimiento());
        tutor.setFecha_nacimiento(tutorResivido.getFecha_nacimiento());
        tutor.setCedula(tutorResivido.getCedula());
        tutor.setOcupacion(tutorResivido.getOcupacion());
        tutor.setTelefonoPrincipal(tutorResivido.getTelefonoPrincipal());
        tutor.setTelefonoSecundario(tutorResivido.getTelefonoSecundario());
        tutorServicio.guardarTutor(tutor);
        return ResponseEntity.ok(tutor);
    }


    @DeleteMapping(path = "/tutores/{id}")// eliminar estudiante
    public  void eliminarTutoresID(@PathVariable Integer id){
        tutorServicio.eliminarTutor(tutorServicio.buscarTutorPorId(id));
    }

}
