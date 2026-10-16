package app.estudiante.utils;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CalificacionBatchRequestDTO(
    @NotNull(message = "El detalle del plan de estudio es obligatorio")
    Long idDetallePlanDeEstudio,

    @NotNull(message = "El docente es obligatorio")
    Integer idDocente,

    @NotNull(message = "El período evaluativo es obligatorio")
    Integer idPeriodoEvaluativo,

    @NotEmpty(message = "Debe enviar al menos una calificación")
    List<@Valid CalificacionItemDTO> calificaciones
) {}