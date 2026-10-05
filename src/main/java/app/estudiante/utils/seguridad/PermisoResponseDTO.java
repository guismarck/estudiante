package app.estudiante.utils.seguridad;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO para la transferencia de la matriz de permisos granulares por módulo.
 * Utilizado en la respuesta de autenticación y en la gestión de seguridad.
 */
@Getter
@Setter
public class PermisoResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long moduloId;
    private String modulo;
    private String codigoModulo;
    private Boolean buscar;
    private Boolean agregar;
    private Boolean modificar;
    private Boolean inactivar;
    private Boolean procesar;
    private Boolean guardar;
    private Boolean exportar;

}
