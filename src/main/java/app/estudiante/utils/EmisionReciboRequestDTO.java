package app.estudiante.utils;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EmisionReciboRequestDTO(
    Long idPersona,
    Long idSalon,
    Integer anioLectivo,
    String concepto,
    String tipoPago,
    BigDecimal monto,
    Long idTarifa,
    LocalDateTime fechaTransaccion,
    String usuario
) {}