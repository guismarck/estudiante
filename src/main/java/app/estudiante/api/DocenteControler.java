package app.estudiante.api;

import app.estudiante.exception.recurosNoEncontradoException;
import app.estudiante.modelo.Docente;
import app.estudiante.modelo.Estudiante;
import app.estudiante.servicio.InterfacesServicios.IDocenteServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<Docente> obternerDocentes(){
        var docentes = docenteServicio.ListarDocent();
        docentes.forEach((docente -> logger.info(docente.toString())));
        return docentes;
    }

    @PostMapping(path = "/docentes/create") // agregar docente
    public void agregarDocente(@RequestBody Docente  docente){
        logger.info("el empledo a agregar " + docente);
        docenteServicio.guardarDocente(docente);
    }

    @GetMapping(path = "/docentes/{id}")// buscar por ID
    public ResponseEntity<Docente> getDocenteId(@PathVariable Integer id){
        if(id == null){
            throw  new recurosNoEncontradoException("No se encontro el grado con Id"+id);
        }
        return  ResponseEntity.ok(docenteServicio.buscarDocentePorId(id));
    }

    @PutMapping(path = "/docentes/{id}")// actulizar estudiante
    public ResponseEntity<Docente>ActulizarId(@PathVariable Integer id,
                                              @RequestBody Docente docenteResivido){
        Docente docente = docenteServicio.buscarDocentePorId(id);
        if(docente == null){
            throw  new recurosNoEncontradoException("No exite el a actualizar con Id"+id);
        }
        docente.setNombre_completo(docenteResivido.getNombre_completo());
        docente.setApellido_completo(docenteResivido.getApellido_completo());
        docente.setSexo(docenteResivido.getSexo());
        docente.setDireccion(docenteResivido.getDireccion());
        docente.setPartidad_nacimiento(docenteResivido.getPartidad_nacimiento());
        docente.setFecha_nacimiento(docenteResivido.getFecha_nacimiento());
        docente.setCedula(docenteResivido.getCedula());
        docente.setEstado(docenteResivido.isEstado());
        docenteServicio.guardarDocente(docente);
        return ResponseEntity.ok(docente);
    }

    @DeleteMapping(path = "/docentes/{id}")// eliminar docente
    public  void eliminarDocenteID(@PathVariable Integer id){
        docenteServicio.eliminarDocente(docenteServicio.buscarDocentePorId(id));
    }

}
