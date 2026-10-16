package app.estudiante.utils;

import java.time.LocalDateTime;

public record EstadoPeriodoEvaluativoResponseDTO(
        Integer idPeriodoEvaluativo,
        boolean permiteEdicion,
        String mensajePantalla,
        LocalDateTime fechaCierreDocente) {
}
