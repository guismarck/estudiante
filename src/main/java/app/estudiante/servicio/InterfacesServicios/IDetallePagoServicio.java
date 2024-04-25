package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.DetallePago;

import java.util.List;

public interface IDetallePagoServicio {
    public List<DetallePago> ListarDetallePago();
    public DetallePago buscarDetallePagoPorId(Integer iddetalle_pago);
    public void guardarDetallePago (DetallePago detallePago);
    public  void eliminarDetallePago(DetallePago detallePago);
}
