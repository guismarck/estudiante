package app.estudiante.utils;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CalificacionBatchRequestDTO(
    @NotNull(message = "El ID del detalle del plan de estudio es obligatorio")
    Integer idDetallePlanDeEstudio,

    @NotNull(message = "El ID del período evaluativo es obligatorio")
    Integer idPeriodoEvaluativo,

    @NotEmpty(message = "La lista de calificaciones no puede estar vacía")
    @Valid
    List<CalificacionItemDTO> calificaciones
) {}