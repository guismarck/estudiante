package app.estudiante.utils;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class LoginResponseDTO {
    private Integer id;
    private String username;
    private String nombreCompleto;
    private List<String> roles;
}