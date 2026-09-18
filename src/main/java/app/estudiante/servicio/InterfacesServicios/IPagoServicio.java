package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.Pago;

import java.util.List;
import app.estudiante.utils.EmisionReciboRequestDTO;

public interface IPagoServicio {
    public List<Pago> ListarPago();
    public Pago buscarPagoPorId(Integer idpago);
    public void guardarPago(Pago pago);
    public  void eliminarPago(Pago pago);
    String emitirRecibo(EmisionReciboRequestDTO dto);
}
