package app.estudiante.api;

import app.estudiante.exception.recurosNoEncontradoException;
import app.estudiante.modelo.Pago;
import app.estudiante.servicio.InterfacesServicios.IPagoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("estudiante-app")
@CrossOrigin(value = "http://localhost:3000")
public class PagoControler {

    private static final
    Logger logger = LoggerFactory.getLogger(PagoControler.class);//Infomacion de la consola

    @Autowired
    private IPagoServicio pagoServicio;

    @GetMapping(path = "/pago")
    public List<Pago> obtenerPago(){
        var pagos = pagoServicio.ListarPago();
        pagos.forEach((pago -> logger.info(pago.toString())));//probado en consola
        return pagos;
    }

    @GetMapping(path = "/pago/{id}")// buscar por ID
    public ResponseEntity<Pago> getPagoId(@PathVariable Integer id){
        if(id == null){
            throw  new recurosNoEncontradoException("No se encontro el grado con Id"+id);
        }
        return  ResponseEntity.ok(pagoServicio.buscarPagoPorId(id));
    }

    @PostMapping(path = "/pago/create") // agregar de Pago
    public ResponseEntity agregarPago(@RequestBody Pago pago){
        logger.info("el empledo a agregar " + pago);
        try {
            pagoServicio.guardarPago(pago);
            return  ResponseEntity.ok("OK");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping(path = "/pago/{id}")// actulizar Pago
    public ResponseEntity<Pago>ActulizarId(@PathVariable Integer id,
                                                    @RequestBody Pago pagoResivido){
        Pago pago = pagoServicio.buscarPagoPorId(id);
        if(pago == null){
            throw  new recurosNoEncontradoException("No exite el a actualizar con Id"+id);
        }
        pago.setConcepto(pagoResivido.getConcepto());
        pago.setFecha_pago(pagoResivido.getFecha_pago());
        pago.setTipo_pago(pagoResivido.getTipo_pago());
        pagoServicio.guardarPago(pago);
        return ResponseEntity.ok(pago);
    }

    @DeleteMapping(path = "/pago/{id}")// eliminar Pago
    public  void eliminarporID(@PathVariable Integer id){
        pagoServicio.
                eliminarPago(pagoServicio.buscarPagoPorId(id));
    }


}
