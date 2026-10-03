package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Pago;
import app.estudiante.repositorio.PagoRepositorio;
import app.estudiante.servicio.InterfacesServicios.IPagoServicio;
import app.estudiante.utils.EmisionReciboResponseDTO;
import lombok.extern.slf4j.Slf4j;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import app.estudiante.utils.EmisionReciboRequestDTO;
import java.time.LocalDateTime;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class PagoServicio implements IPagoServicio {
    @Autowired
    private final EntityManager entityManager;
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

//    @Override
//    @Transactional
//    public EmisionReciboResponseDTO emitirRecibo(EmisionReciboRequestDTO requestDTO) {
//
//        try {
//            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_procesar_emision_recibo");
//
//            // Registro de Parámetros de Entrada (IN)
//            query.registerStoredProcedureParameter("p_idpersona", Integer.class, ParameterMode.IN);
//            query.registerStoredProcedureParameter("p_idSalon", Integer.class, ParameterMode.IN);
//            query.registerStoredProcedureParameter("p_anio_lectivo", Integer.class, ParameterMode.IN);
//            query.registerStoredProcedureParameter("p_concepto", String.class, ParameterMode.IN);
//            query.registerStoredProcedureParameter("p_tipo_pago", String.class, ParameterMode.IN);
//            query.registerStoredProcedureParameter("p_monto", java.math.BigDecimal.class, ParameterMode.IN);
//            query.registerStoredProcedureParameter("p_idtarifa", Integer.class, ParameterMode.IN);
//            query.registerStoredProcedureParameter("p_fecha_transaccion", LocalDateTime.class, ParameterMode.IN);
//            query.registerStoredProcedureParameter("p_usuario", String.class, ParameterMode.IN);
//
//            // Registro de Parámetro de Salida (OUT)
//            query.registerStoredProcedureParameter("p_recibo_generado", String.class, ParameterMode.OUT);
//
//            // Setear Valores
//            query.setParameter("p_idpersona", requestDTO.idPersona());
//            query.setParameter("p_idSalon", requestDTO.idSalon());
//            query.setParameter("p_anio_lectivo", requestDTO.anioLectivo());
//            query.setParameter("p_concepto", requestDTO.concepto());
//            query.setParameter("p_tipo_pago", requestDTO.tipoPago());
//            query.setParameter("p_monto", requestDTO.monto());
//            query.setParameter("p_idtarifa", requestDTO.idTarifa());
//            query.setParameter("p_fecha_transaccion", requestDTO.fechaTransaccion() != null ? requestDTO.fechaTransaccion() : LocalDateTime.now());
//            query.setParameter("p_usuario", requestDTO.usuario());
//
//            query.execute();
//
//            // Recuperar número de recibo generado
//            String numReciboGenerado = (String) query.getOutputParameterValue("p_recibo_generado");
//
//           // log.info("Recibo generado exitosamente: {} para estudiante ID: {}", numReciboGenerado, requestDTO.idPersona());
//
//            return new EmisionReciboResponseDTO(
//                    numReciboGenerado,
//                    requestDTO.idPersona(),
//                    requestDTO.monto(),
//                    requestDTO.concepto(),
//                    LocalDateTime.now(),
//                    "Emisión de recibo procesada correctamente."
//            );
//
//        } catch (Exception e) {
//            //log.error("Error al procesar el Stored Procedure sp_procesar_emision_recibo: {}", e.getMessage());
//            throw new RuntimeException("Error en la transacción: " + e.getMessage());
//        }
//    }
@Override
@Transactional
public EmisionReciboResponseDTO emitirRecibo(EmisionReciboRequestDTO requestDTO) {
    log.info("Iniciando emisión de recibo para la persona: {}", requestDTO);

    LocalDateTime fechaTransaccion = requestDTO.fechaTransaccion() != null
            ? requestDTO.fechaTransaccion()
            : LocalDateTime.now();

    String numReciboGenerado = pagoRepositorio.procesarEmisionRecibo(
            requestDTO.idPersona(),
            requestDTO.idSalon(),
            requestDTO.anioLectivo(),
            requestDTO.concepto(),
            requestDTO.tipoPago(),
            requestDTO.monto(),
            requestDTO.idTarifa(),
            fechaTransaccion,
            requestDTO.usuario()
    );

    log.info("Recibo generado exitosamente: {} para estudiante ID: {}", numReciboGenerado, requestDTO.idPersona());

    return new EmisionReciboResponseDTO(
            numReciboGenerado,
            requestDTO.idPersona().intValue(),
            requestDTO.monto(),
            requestDTO.concepto(),
            LocalDateTime.now(),
            "Emisión de recibo procesada correctamente."
    );
}
}
