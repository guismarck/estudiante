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
public class ModuloMenuDTO {
    private Long id;
    private String nombre;
    private String codigo;
    private String recurso;
    private String componentKey;
    private String pathImg;
    private Integer orden;
    private PermisoModuloDTO permisos;
    @Builder.Default
    private List<ModuloMenuDTO> submodulos = new ArrayList<>();
}