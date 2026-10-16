package app.estudiante.repositorio;

import java.time.LocalDateTime;

public interface EstadoPeriodoEvaluativoProjection {
    Long getPermiteEdicion();
    String getMensajePantalla();
    LocalDateTime getFechaCierreDocente();
}
