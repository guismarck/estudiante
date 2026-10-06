package app.estudiante.utils;

import java.math.BigDecimal;

public record NominaEstudianteResponseDTO(
    Integer idMatricula,
    String codEstudiante,
    String codigoMined,
    String nombreCompletoEstudiante,
    BigDecimal acumulado,
    BigDecimal examen,
    BigDecimal notaFinal
) {
    // Constructor de conveniencia si HQL retorna Double desde COALESCE
    public NominaEstudianteResponseDTO(
            Integer idMatricula,
            String codEstudiante,
            String codigoMined,
            String nombreCompletoEstudiante,
            Double acumulado,
            Double examen,
            Double notaFinal) {
        this(
            idMatricula,
            codEstudiante,
            codigoMined,
            nombreCompletoEstudiante,
            BigDecimal.valueOf(acumulado != null ? acumulado : 0.0),
            BigDecimal.valueOf(examen != null ? examen : 0.0),
            BigDecimal.valueOf(notaFinal != null ? notaFinal : 0.0)
        );
    }
}