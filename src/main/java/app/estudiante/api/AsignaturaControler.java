package app.estudiante.api;

import app.estudiante.modelo.Asignatura;
import app.estudiante.servicio.InterfacesServicios.IAsignaturaServicio;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

//@AllArgsConstructor
//@RestController
//@RequestMapping("estudiante-app")
//@CrossOrigin(value = "http://localhost:3000")
public class AsignaturaControler {
    private IAsignaturaServicio asignaturaServicio;
    //http://localhost:8080/estudiante-app/asignaturas
//    @GetMapping(path = "/asignaturas")
//    public ResponseEntity<List<Asignatura>>getAllAsignatura(){
//
//        List<Asignatura> list = asignaturaServicio.ListarAsignatura();
//        return ResponseEntity.ok(list);
//
//    }
//http://localhost:8080/estudiante-app/asignaturas
    @GetMapping(path = "/asignaturas")
    public List<Asignatura> obtenerAsignaturas(){
        List<Asignatura> list = asignaturaServicio.ListarAsignatura();

        return  list;
    }

//    @GetMapping(path = "/api/Asignatura/1")
//     public  Asignatura get
}
