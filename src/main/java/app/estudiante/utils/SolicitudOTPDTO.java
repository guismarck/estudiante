package app.estudiante.utils;
import jakarta.validation.constraints.NotBlank;

public record SolicitudOTPDTO(
    @NotBlank(message = "La cédula, correo o código MINED es obligatorio")
    String identificacion,

    @NotBlank(message = "El nombre de usuario es obligatorio")
    String username
) {}