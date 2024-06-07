package app.estudiante.api;

import app.estudiante.exception.recurosNoEncontradoException;
import app.estudiante.modelo.CatalogoSalon;
import app.estudiante.servicio.InterfacesServicios.ICatalogoSalonServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
////http://localhost:8080/estudiante-app/catalogo/salon/1
@RequestMapping("estudiante-app")
@CrossOrigin(value = "http://localhost:3000")
public class CatalogoSalonControler {
    private static final
    Logger logger = LoggerFactory.getLogger(CatalogoSalonControler.class);//Infomacion de la consola
    @Autowired
    private ICatalogoSalonServicio iCatalogoSalonServicio;
    //http://localhost:8080/estudiante-app/grados
    @GetMapping(path = "/catalogo/salon")
    public List<CatalogoSalon> obtenerCatalogoSalon(){
        var cat_salon = iCatalogoSalonServicio.ListarCatalogoSalon();

        cat_salon.forEach((salon -> logger.info(salon.toString())));//probado en consola
        return cat_salon;
    }
    ////http://localhost:8080/estudiante-app/catalogo/salon/update
    @PutMapping(path = "/catalogo/salon/{id}")
    public ResponseEntity<CatalogoSalon> ActualizarporID(@PathVariable Integer id,
                                                @RequestBody CatalogoSalon CatSalonRecibido){
        CatalogoSalon cat_salon = iCatalogoSalonServicio.buscarCatalogoSalonPorId(id);
        if(cat_salon == null){
            throw  new recurosNoEncontradoException("No existe el salon a actualizar con Id"+id);
        }
        // cat_salon.setIdcatalogo_Salon(gradoResivido.getIdGrado());
        cat_salon.setNombre_salon(CatSalonRecibido.getNombre_salon());
        iCatalogoSalonServicio.guardarCatalogoSalon(cat_salon);
        return  ResponseEntity.ok(cat_salon);
    }

    @GetMapping(path = "/catalogo/salon/{id}") //listar por ID
    public ResponseEntity<CatalogoSalon> getPorSalonId(@PathVariable Integer id){
        if(id == null){
            throw  new recurosNoEncontradoException("No se encontro el salon con Id"+id);
        }
        return ResponseEntity.ok(iCatalogoSalonServicio.buscarCatalogoSalonPorId(id));
    }

    @PostMapping("/catalogo/salon/create") // agregar salon
    public void agregarCatSalon(@RequestBody CatalogoSalon cat_Salon){
        logger.info("el salon a agregar "+cat_Salon);
        iCatalogoSalonServicio.guardarCatalogoSalon(cat_Salon);

    }

    @DeleteMapping("/catalogo/salon/{id}")  // eliminar salon
    public void eliminarCatSalonporID(@PathVariable Integer id) {
        iCatalogoSalonServicio.eliminarCatalogoSalon(iCatalogoSalonServicio.buscarCatalogoSalonPorId(id));
    }
}
