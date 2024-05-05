package app.estudiante.api;

import app.estudiante.modelo.Grado;
import app.estudiante.servicio.InterfacesServicios.IGradoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
////http://localhost:8080/estudiante-app/estudiante/1
//@RequestMapping("estudiante-app")
//@CrossOrigin(value = "http://localhost:3000")
public class GradoControlador {

    private static final Logger logger = LoggerFactory.getLogger(GradoControlador.class);//Infomacion de la consola
    @Autowired
    private IGradoServicio gradoServicio;
    //http://localhost:8080/estudiante-app/grados
    @GetMapping( path = "/grados")
    public List<Grado> obtenerGrados(){
        var grados = gradoServicio.ListarGrado();

       grados.forEach((grado -> logger.info(grado.toString())));//probado en consola
        return grados;
    }




    //http://localhost:8080/api/grados
   /* @GetMapping(path = "/api/grados")
    public ResponseEntity<List<Grado>> getAllGrados(){

        List<Grado> grado = gradoServicio.ListarGrado();
        grado.forEach((Grado -> logger.info(grado.toString())));//probado en consola
        return ResponseEntity.ok(grado);
    }*/


}

