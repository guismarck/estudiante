package app.estudiante.utils.seguridad;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequestDTO {

    @NotBlank(message = "El usuario o código MINED es obligatorio")
    private String username;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;
}