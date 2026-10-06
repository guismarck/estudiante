package app.estudiante.utils;
import java.math.BigDecimal;

public interface NominaEstudianteProjection {
    Integer getIdMatricula();
    String getCodEstudiante();
    String getCodigoMined();
    String getNombreCompletoEstudiante();
    BigDecimal getAcumulado();
    BigDecimal getExamen();
    BigDecimal getNotaFinal();
}