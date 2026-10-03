package app.estudiante.utils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EmisionReciboResponseDTO(
        String numeroRecibo,
        Integer idPersona,
        BigDecimal monto,
        String concepto,
        LocalDateTime fechaEmision,
        String mensaje
) {}