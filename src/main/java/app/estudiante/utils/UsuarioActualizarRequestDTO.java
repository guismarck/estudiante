package app.estudiante.utils;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;

public record UsuarioActualizarRequestDTO(
        @NotBlank(message = "El nombre de usuario es obligatorio")
        @Size(min = 4, max = 50, message = "El username debe tener entre 4 y 50 caracteres")
        String username,

        @Size(max = 150)
        String nombres,

        @Size(max = 150)
        String apellidos,

        @Size(max = 20)
        String sexo,

        LocalDate fechaNacimiento,

        @Size(max = 300)
        String direccion,

        @Email(message = "El correo electrónico no es válido")
        @Size(max = 255)
        String email,

        String password,

        Integer estado,

        @NotEmpty(message = "Debe asignar exactamente un rol al usuario")
        @Size(max = 1, message = "Solo puede asignar un rol por usuario")
        Set<Short> rolesIds
) {}
