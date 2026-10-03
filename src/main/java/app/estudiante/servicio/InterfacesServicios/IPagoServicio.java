package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.Pago;

import java.util.List;
import app.estudiante.utils.EmisionReciboRequestDTO;
import app.estudiante.utils.EmisionReciboResponseDTO;

public interface IPagoServicio {
    public List<Pago> ListarPago();
    public Pago buscarPagoPorId(Integer idpago);
    public void guardarPago(Pago pago);
    public  void eliminarPago(Pago pago);
    EmisionReciboResponseDTO emitirRecibo(EmisionReciboRequestDTO dto);
}
