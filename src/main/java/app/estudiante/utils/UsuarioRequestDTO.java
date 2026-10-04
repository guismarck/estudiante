package app.estudiante.utils;
import jakarta.validation.constraints.*;
import java.util.Set;

public record UsuarioRequestDTO(
        Long idPersona,

        @NotBlank(message = "El nombre de usuario es obligatorio")
        @Size(min = 4, max = 50, message = "El username debe contener entre 4 y 50 caracteres")
        String username,

        @NotBlank(message = "La contraseña no puede estar vacía")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        String password,

        @NotNull(message = "El estado es requerido")
        Short estado,

        @NotEmpty(message = "Debe asignar al menos un rol al usuario")
        Set<Short> rolesIds,

        String creadoPor
) {}
