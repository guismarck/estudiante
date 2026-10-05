package app.estudiante.utils.seguridad;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String username;

    private String password;
}