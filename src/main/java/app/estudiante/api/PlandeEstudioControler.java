package app.estudiante.api;

import app.estudiante.exception.recurosNoEncontradoException;
import app.estudiante.modelo.PlandeEstudio;
import app.estudiante.servicio.InterfacesServicios.IPlandeEstudioServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("estudiante-app")
@CrossOrigin(value = "http://localhost:3000")
public class PlandeEstudioControler {
    private static final
    Logger logger = LoggerFactory.getLogger(PlandeEstudioControler.class);//Infomacion de la consola

    @Autowired
    private IPlandeEstudioServicio plandeEstudioServicio;

    //http://localhost:8080/estudiante-app/plandeEstudio
   /* @GetMapping(path = "/plandeEstudio")//Busqueda general
    public List<PlandeEstudio> obternerPlandeEstudio(@RequestParam(value = "search",required = false) String search){
        if (search == null || search.trim().isEmpty())
            return  plandeEstudioServicio.ListarPlandeEstudio();
        return plandeEstudioServicio.busquedaGeneral(search);
    }*/

    @GetMapping(path = "/plandeEstudio")
    public List<PlandeEstudio> obtenerPlandeEstudio(){
        var planEstudios = plandeEstudioServicio.ListarPlandeEstudio();
        planEstudios.forEach((planEstudio -> logger.info(planEstudio.toString())));//probado en consola
        return planEstudios;
    }

    @GetMapping(path = "/plandeEstudio/{id}")// buscar por ID
    public ResponseEntity<PlandeEstudio> getPlandeEstudioId(@PathVariable Integer id){
        if(id == null){
            throw  new recurosNoEncontradoException("No se encontro el grado con Id"+id);
        }
        return  ResponseEntity.ok(plandeEstudioServicio.buscarPlandeEstudioPorId(id));
    }

    @PostMapping(path = "/plandeEstudio/create") // agregar de estudio
    public ResponseEntity agregarPlanEstudio(@RequestBody PlandeEstudio plandeEstudio){
        logger.info("el empledo a agregar " + plandeEstudio);
        try {
            plandeEstudioServicio.guardarPlandeEstudio(plandeEstudio);
            return  ResponseEntity.ok("OK");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping(path = "/plandeEstudio/{id}")// actulizar PlandeEstudio
    public ResponseEntity<PlandeEstudio>ActulizarId(@PathVariable Integer id,
                                                 @RequestBody PlandeEstudio plandeEstudioResivido){
        PlandeEstudio plandeEstudio = plandeEstudioServicio.buscarPlandeEstudioPorId(id);
        if(plandeEstudio == null){
            throw  new recurosNoEncontradoException("No exite el a actualizar con Id"+id);
        }
        plandeEstudio.setAño_electivo(plandeEstudioResivido.getAño_electivo());
        plandeEstudio.setFecha_inicio(plandeEstudioResivido.getFecha_inicio());
        plandeEstudio.setFecha_fin(plandeEstudioResivido.getFecha_fin());
        plandeEstudio.setPeriodo(plandeEstudioResivido.getPeriodo());
        plandeEstudio.setEstado(plandeEstudioResivido.isEstado());
        plandeEstudioServicio.guardarPlandeEstudio(plandeEstudio);
        return ResponseEntity.ok(plandeEstudio);
    }

    @DeleteMapping(path = "/plandeEstudio/{id}")// eliminar Plan de estudio
    public  void eliminarporID(@PathVariable Integer id){
        plandeEstudioServicio.
                eliminarPlandeEstudio(plandeEstudioServicio.buscarPlandeEstudioPorId(id));
    }

}
