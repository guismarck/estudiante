package app.estudiante.servicio.InterfacesServicios;

import java.util.Collection;
import java.util.Map;

import app.estudiante.utils.ReporteFormato;

public interface IJasperReportService {
    /**
     * Carga una plantilla (.jasper / .jrxml) e inyecta parámetros y la conexión a la BD
     */
    byte[] generarReporteDesdePlantilla(String nombrePlantilla, ReporteFormato formato, Map<String, Object> parametros);

    /**
     * Carga una plantilla e inyecta parámetros junto a una colección de Beans Java
     */
    byte[] generarReporteDesdeColeccion(String nombrePlantilla, ReporteFormato formato, Map<String, Object> parametros, Collection<?> datos);
}