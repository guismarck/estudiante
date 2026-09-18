package app.estudiante.utils;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EmisionReciboRequestDTO(
    Integer idPersona,
    Integer idSalon,
    Integer anioLectivo,
    String concepto,
    String tipoPago,
    BigDecimal monto,
    Integer idTarifa,
    LocalDateTime fechaTransaccion,
    String usuario
) {}