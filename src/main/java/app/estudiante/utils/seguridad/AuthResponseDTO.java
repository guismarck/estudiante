package app.estudiante.utils.seguridad;

import app.estudiante.utils.UsuarioResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {

    private String token;
    private String tokenType = "Bearer";
    private UsuarioDTO usuario;
    private List<String> roles;
    private List<PermisoModuloDTO> permisos;

    public AuthResponseDTO(String jwt, UsuarioResponseDTO usuarioDTO, List<String> roles, List<PermisoResponseDTO> permisos) {
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsuarioDTO {
        private Long id;
        private String username;
        private Long idPersona;
        private Integer estado;
    }
}