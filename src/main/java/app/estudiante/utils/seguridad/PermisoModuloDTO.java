package app.estudiante.utils.seguridad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermisoModuloDTO {
    private String moduloCodigo;
    private String recurso;
    private String componentKey;
    private Boolean buscar;
    private Boolean agregar;
    private Boolean modificar;
    private Boolean inactivar;
    private Boolean procesar;
    private Boolean guardar;
    private Boolean exportar;
}