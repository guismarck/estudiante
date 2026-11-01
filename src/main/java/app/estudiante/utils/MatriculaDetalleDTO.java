package app.estudiante.utils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MatriculaDetalleDTO(
        Integer idMatricula,
        Integer estudianteId,
        String nombreEstudiante,
        String apellidoEstudiante,
        String nombreCompletoEstudiante,
        String grado,
        String nivel,
        Integer idPlanEstudio,
        Integer anioPlanEstudio,
        String planDeEstudio,
        Short anioLectivo,
        BigDecimal costoMatricula,
        String estadoMatricula,
        LocalDateTime fechaMatricula,
        String salon,
        String turno,
        String seccion
) {}
