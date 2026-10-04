package app.estudiante.utils.seguridad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermisosDTO {
    private boolean puedeBuscar;
    private boolean puedeAgregar;
    private boolean puedeModificar;
    private boolean puedeInactivar;
    private boolean puedeProcesar;
    private boolean puedeGuardar;
    private boolean puedeExportar;
}