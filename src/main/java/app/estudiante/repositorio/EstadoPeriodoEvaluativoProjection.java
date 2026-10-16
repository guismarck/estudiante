package app.estudiante.repositorio;

import java.time.LocalDateTime;

public interface EstadoPeriodoEvaluativoProjection {
    Boolean getPermiteEdicion();
    String getMensajePantalla();
    LocalDateTime getFechaCierreDocente();
}
