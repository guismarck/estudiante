package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Pago;
import app.estudiante.repositorio.PagoRepositorio;
import app.estudiante.servicio.InterfacesServicios.IPagoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import app.estudiante.utils.EmisionReciboRequestDTO;
import java.time.LocalDateTime;

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

    @Override
    @Transactional
    public String emitirRecibo(EmisionReciboRequestDTO dto) {
      LocalDateTime fechaProceso = dto.fechaTransaccion() != null 
                ? dto.fechaTransaccion() 
                : LocalDateTime.now();

        // Ejecución delegada al procedimiento almacenado
        String numeroRecibo = pagoRepositorio.procesarEmisionRecibo(
                dto.idPersona(),
                dto.idSalon(),
                dto.anioLectivo(),
                dto.concepto(),
                dto.tipoPago(),
                dto.monto(),
                dto.idTarifa(),
                fechaProceso,
                dto.usuario()
        );

        return numeroRecibo;
    }
}
