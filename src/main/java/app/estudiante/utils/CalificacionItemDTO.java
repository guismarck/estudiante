package app.estudiante.utils;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CalificacionItemDTO(
    @NotNull(message = "El ID de matrícula es obligatorio")
    Integer idMatricula,

    @NotNull(message = "El acumulado es obligatorio")
    @DecimalMin(value = "0.00", message = "El acumulado no puede ser menor a 0.00")
    @DecimalMax(value = "60.00", message = "El acumulado no puede ser mayor a 60.00")
    BigDecimal acumulado,

    @NotNull(message = "El examen es obligatorio")
    @DecimalMin(value = "0.00", message = "El examen no puede ser menor a 0.00")
    @DecimalMax(value = "40.00", message = "El examen no puede ser mayor a 40.00")
    BigDecimal examen
) {}