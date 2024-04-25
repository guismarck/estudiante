package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Pago;
import app.estudiante.repositorio.PagoRepositorio;
import app.estudiante.servicio.InterfacesServicios.IPagoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PagoServicio implements IPagoServicio {

    @Autowired
    private PagoRepositorio pagoRepositorio;
    @Override
    public List<Pago> ListarPago() {
        List<Pago> pagos = pagoRepositorio.findAll();
        return pagos;
    }

    @Override
    public Pago buscarPagoPorId(Integer idpago) {
        Pago pago = pagoRepositorio.findById(idpago).orElse(null);
        return pago;
    }

    @Override
    public void guardarPago(Pago pago) {
        pagoRepositorio.save(pago);
    }

    @Override
    public void eliminarPago(Pago pago) {
      pagoRepositorio.delete(pago);
    }
}
