package app.estudiante.utils;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RolRequestDTO(
    @NotBlank(message = "El código del rol es obligatorio")
    @Size(min = 3, max = 50, message = "El código del rol debe tener entre 3 y 50 caracteres")
    String codigo,

    String descripcion
) {}