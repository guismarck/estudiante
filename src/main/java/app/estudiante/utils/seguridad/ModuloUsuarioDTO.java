package app.estudiante.utils.seguridad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuloUsuarioDTO {
    private Long id;
    private String nombre;
    private String codigo;
    private String recurso;
    private String componentKey;
    private String pathImg;
    private Long moduloPadreId;
    private PermisosDTO permisos;

    @Builder.Default
    private List<ModuloUsuarioDTO> subModulos = new ArrayList<>();
}