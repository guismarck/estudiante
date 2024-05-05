package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.DetallePago;
import app.estudiante.repositorio.DetallePagoRepositorio;
import app.estudiante.servicio.InterfacesServicios.IDetallePagoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DetallePagoServicio implements IDetallePagoServicio {

    @Autowired
    private DetallePagoRepositorio detallePagoRepositorio;
    @Override
    public List<DetallePago> ListarDetallePago() {
        List<DetallePago> detallePagos = detallePagoRepositorio.findAll();
        return detallePagos ;
    }

    @Override
    public DetallePago buscarDetallePagoPorId(Integer iddetalle_pago) {
        DetallePago detallePago = detallePagoRepositorio.findById(iddetalle_pago).orElse(null);
        return detallePago;
    }

    @Override
    public void guardarDetallePago(DetallePago detallePago) {
        detallePagoRepositorio.save(detallePago);
    }

    @Override
    public void eliminarDetallePago(DetallePago detallePago) {
        detallePagoRepositorio.delete(detallePago);
    }
}
