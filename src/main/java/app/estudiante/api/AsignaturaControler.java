package app.estudiante.api;

import app.estudiante.servicio.InterfacesServicios.IAsignaturaServicio;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AsignaturaControler {
    private final IAsignaturaServicio asignaturaServicio;

//    @GetMapping(path = "/api/asignatura")
//    public ResponseEntity<List<Asignatura>>getAllAsignatura(){
//        Asignatura a = new Asignatura();
//        asignaturaServicio.guardarAsignatura(a);
//        List<Asignatura> list = asignaturaServicio.ListarAsignatura();
//        return ResponseEntity.ok(list);
//
//    }
//    @GetMapping(path = "/api/Asignatura")
//    public List<Asignatura> getAllAsignaturas(){
//        List<Asignatura> list = asignaturaServicio.ListarAsignatura();
//
//        return  list;
//    }

//    @GetMapping(path = "/api/Asignatura/1")
//     public  Asignatura get
}
