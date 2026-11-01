package app.estudiante.utils;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record AsignarRolesDTO(
    @NotNull(message = "El ID de usuario es obligatorio")
    Long usuarioId,

    @NotEmpty(message = "Debe enviar al menos un ID de rol")
    Set<Short> rolesIds
) {}