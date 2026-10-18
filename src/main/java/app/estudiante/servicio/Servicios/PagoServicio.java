package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Pago;
import app.estudiante.exception.recursosNoEncontradoException;
import app.estudiante.repositorio.PagoRepositorio;
import app.estudiante.servicio.InterfacesServicios.IPagoServicio;
import app.estudiante.utils.EmisionReciboResponseDTO;
import app.estudiante.utils.PagoSaldoResponseDTO;
import app.estudiante.utils.SolvenciaMatriculaResponseDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import app.estudiante.utils.EmisionReciboRequestDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Service
@Slf4j
public class PagoServicio implements IPagoServicio {
    private final PagoRepositorio pagoRepositorio;
    private final EntityManager entityManager;

    public PagoServicio(PagoRepositorio pagoRepositorio, EntityManager entityManager) {
        this.pagoRepositorio = pagoRepositorio;
        this.entityManager = entityManager;
    }

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
    public PagoSaldoResponseDTO buscarSaldoPorPersonaYTarifa(Integer idPersona, Integer idTarifa) {
        return pagoRepositorio.buscarSaldoPorPersonaYTarifa(idPersona, idTarifa)
                .map(saldo -> new PagoSaldoResponseDTO(
                        saldo.getIdTarifa(),
                        saldo.getConcepto(),
                        saldo.getAnioLectivo(),
                        saldo.getMontoTarifa(),
                        saldo.getMontoPagado(),
                        saldo.getSaldoRestante()
                ))
                .orElseThrow(() -> new recursosNoEncontradoException(
                        "No se encontró la tarifa con ID " + idTarifa));
    }

    @Override
    public List<SolvenciaMatriculaResponseDTO> buscarSolvenciaMatricula(
            Integer idPersona, Integer anioLectivo) {
        List<SolvenciaMatriculaResponseDTO> solvencias = pagoRepositorio
                .buscarSolvenciaMatricula(idPersona, anioLectivo).stream()
                .map(solvencia -> new SolvenciaMatriculaResponseDTO(
                        solvencia.getIdPersona(),
                        solvencia.getIdTarifa(),
                        solvencia.getArancel(),
                        solvencia.getAnioLectivo(),
                        solvencia.getMontoOficial(),
                        solvencia.getTotalPagado(),
                        solvencia.getSaldoPendiente(),
                        solvencia.getEstado()
                ))
                .toList();
        if (solvencias.isEmpty()) {
            throw new recursosNoEncontradoException(
                    "El estudiante con ID " + idPersona
                            + " no ha realizado pagos de matrícula para el año lectivo " + anioLectivo);
        }
        return solvencias;
    }

@Override
@Transactional
public EmisionReciboResponseDTO emitirRecibo(EmisionReciboRequestDTO requestDTO) {
    log.info("Iniciando emisión de recibo para la persona: {}", requestDTO);

    StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_procesar_emision_recibo");
    query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
    query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
    query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
    query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
    query.registerStoredProcedureParameter(5, BigDecimal.class, ParameterMode.IN);
    query.registerStoredProcedureParameter(6, Integer.class, ParameterMode.IN);
    query.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);
    query.registerStoredProcedureParameter(8, String.class, ParameterMode.OUT);
    query.registerStoredProcedureParameter(9, BigDecimal.class, ParameterMode.OUT);

    query.setParameter(1, Math.toIntExact(requestDTO.idPersona()));
    query.setParameter(2, requestDTO.anioLectivo());
    query.setParameter(3, requestDTO.concepto());
    query.setParameter(4, requestDTO.tipoPago());
    query.setParameter(5, requestDTO.monto());
    query.setParameter(6, Math.toIntExact(requestDTO.idTarifa()));
    query.setParameter(7, requestDTO.usuario());
    query.execute();

    String numReciboGenerado = (String) query.getOutputParameterValue(8);
    BigDecimal saldoRestante = (BigDecimal) query.getOutputParameterValue(9);

    log.info("Recibo generado exitosamente: {} para estudiante ID: {}", numReciboGenerado, requestDTO.idPersona());

    return new EmisionReciboResponseDTO(
            numReciboGenerado,
            requestDTO.idPersona().intValue(),
            requestDTO.monto(),
            saldoRestante,
            requestDTO.concepto(),
            LocalDateTime.now(),
            "Emisión de recibo procesada correctamente."
    );
}
}
