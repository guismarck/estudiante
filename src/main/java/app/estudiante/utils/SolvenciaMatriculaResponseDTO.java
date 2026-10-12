package app.estudiante.utils;

import java.math.BigDecimal;

public record SolvenciaMatriculaResponseDTO(
        Integer idPersona,
        Integer idTarifa,
        String arancel,
        Integer anioLectivo,
        BigDecimal montoOficial,
        BigDecimal totalPagado,
        BigDecimal saldoPendiente,
        String estado
) {}
