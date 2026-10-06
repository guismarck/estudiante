package app.estudiante.utils;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CalificacionRequestDTO(
    @NotNull(message = "El ID de matrícula es obligatorio")
    Long idMatricula,

    @NotNull(message = "El ID del detalle del plan de estudio es obligatorio")
    Long idDetallePlanDeEstudio,

    @NotNull(message = "El ID del período evaluativo es obligatorio")
    Long idPeriodoEvaluativo,

    @NotNull(message = "El acumulado es obligatorio")
    @DecimalMin(value = "0.00", message = "El acumulado mínimo es 0.00")
    @DecimalMax(value = "40.00", message = "El acumulado máximo es 40.00")
    BigDecimal acumulado,

    @NotNull(message = "El examen es obligatorio")
    @DecimalMin(value = "0.00", message = "El examen mínimo es 0.00")
    @DecimalMax(value = "60.00", message = "El examen máximo es 60.00")
    BigDecimal examen
) {}