package app.estudiante.utils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Record inmutable para la transferencia de datos del usuario autenticado o consultado.
 */
public record UsuarioResponseDTO(
        Long id,
        Long idPersona,
        String username,
        Integer estado,
        LocalDateTime creadoEn,
        Set<String> roles
) implements Serializable {
    private static final long serialVersionUID = 1L;
}
