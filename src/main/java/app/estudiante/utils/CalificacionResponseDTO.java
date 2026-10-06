package app.estudiante.utils;

import java.math.BigDecimal;

public record CalificacionResponseDTO(
    Long idCalificacion,
    Long idMatricula,
    Long idDetallePlanDeEstudio,
    Long idPeriodoEvaluativo,
    BigDecimal acumulado,
    BigDecimal examen,
    BigDecimal notaFinal,
    String creadoPor,
    String actualizadoPor
) {}