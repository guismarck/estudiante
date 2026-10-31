package app.estudiante.utils;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record VerificarOTPDTO(
    @NotNull(message = "El ID del docente es obligatorio")
    Long docenteId,

    @NotBlank(message = "El token de recuperación es obligatorio")
    String tokenRecuperacion,

    @NotBlank(message = "El código OTP es obligatorio")
    @Size(min = 6, max = 6, message = "El código OTP debe tener 6 dígitos")
    @Pattern(regexp = "[0-9]{6}", message = "El código OTP debe contener solo números")
    String otpCode,

    @NotBlank(message = "La nueva contraseña es obligatoria")
    @Size(min = 8, message = "La nueva contraseña debe tener al menos 8 caracteres")
    String nuevaPassword
) {}