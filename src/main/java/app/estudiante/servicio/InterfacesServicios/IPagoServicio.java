package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.Pago;

import java.util.List;

public interface IPagoServicio {
    public List<Pago> ListarPago();
    public Pago buscarPagoPorId(Integer idpago);
    public void guardarPago(Pago pago);
    public  void eliminarPago(Pago pago);
}
