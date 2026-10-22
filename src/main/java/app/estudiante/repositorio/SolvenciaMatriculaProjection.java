package app.estudiante.repositorio;

import java.math.BigDecimal;

public interface SolvenciaMatriculaProjection {
    Integer getIdPersona();
    Integer getIdTarifa();
    String getArancel();
    Integer getAnioLectivo();
    BigDecimal getMontoOficial();
    BigDecimal getTotalPagado();
    BigDecimal getSaldoPendiente();
    String getEstado();
}
