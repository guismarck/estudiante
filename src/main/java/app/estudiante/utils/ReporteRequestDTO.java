package app.estudiante.utils;


import java.util.Map;

public record ReporteRequestDTO(
    String nombrePlantilla,
    ReporteFormato formato,
    Map<String, Object> parametros
) {}