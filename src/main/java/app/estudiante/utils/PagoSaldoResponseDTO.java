package app.estudiante.utils;

import java.math.BigDecimal;

public record PagoSaldoResponseDTO(
        Integer idTarifa,
        String concepto,
        Integer anioLectivo,
        BigDecimal montoTarifa,
        BigDecimal montoPagado,
        BigDecimal saldoRestante
) {}
