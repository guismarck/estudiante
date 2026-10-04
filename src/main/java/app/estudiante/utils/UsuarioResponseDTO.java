package app.estudiante.utils;

import java.time.LocalDateTime;
import java.util.Set;

public record UsuarioResponseDTO(
        Long id,
        Long idPersona,
        String username,
        Short estado,
        LocalDateTime creadoEn,
        Set<String> roles
) {}
