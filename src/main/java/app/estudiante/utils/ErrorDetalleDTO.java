package app.estudiante.utils;

import java.time.LocalDateTime;

public record ErrorDetalleDTO(
        LocalDateTime timestamp,
        int status,
        String error,
        String mensaje,
        String path
) {}