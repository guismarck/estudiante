package app.estudiante.servicio.Servicios;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import app.estudiante.servicio.InterfacesServicios.IJasperReportService;
import app.estudiante.utils.ReporteFormato;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.io.Resource;

@Slf4j
@Service
@RequiredArgsConstructor
public class JasperReportServiceImpl implements IJasperReportService {

    private final DataSource dataSource;
    private final ResourceLoader resourceLoader;

    private static final String RUTA_REPORTES = "classpath:reportes/";

    @Override
    public byte[] generarReporteDesdePlantilla(String nombrePlantilla, ReporteFormato formato, Map<String, Object> parametros) {
        try (Connection connection = dataSource.getConnection()) {
            JasperReport jasperReport = cargarPlantillaExistente(nombrePlantilla);
            Map<String, Object> params = (parametros != null) ? parametros : new HashMap<>();
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, connection);
            return exportarABytes(jasperPrint, formato);
        } catch (Exception e) {
            log.error("Error procesando plantilla de reporte Jasper: {}", nombrePlantilla, e);
            throw new RuntimeException("No se pudo generar el reporte: " + nombrePlantilla, e);
        }
    }

    @Override
    public byte[] generarReporteDesdeColeccion(String nombrePlantilla, ReporteFormato formato, Map<String, Object> parametros, Collection<?> datos) {
        try {
            JasperReport jasperReport = cargarPlantillaExistente(nombrePlantilla);
            Map<String, Object> params = (parametros != null) ? parametros : new HashMap<>();
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(datos);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, ds);
            return exportarABytes(jasperPrint, formato);
        } catch (Exception e) {
            log.error("Error procesando reporte con colección de datos: {}", nombrePlantilla, e);
            throw new RuntimeException("Error al llenar el reporte con la colección de datos: " + nombrePlantilla, e);
        }
    }

    /**
     * Busca la plantilla preexistente en resources/reportes/
     * Prioriza archivos .jasper (ya compilados) y luego archivos .jrxml
     */
    private JasperReport cargarPlantillaExistente(String nombrePlantilla) throws Exception {
        Resource jasperResource = resourceLoader.getResource(RUTA_REPORTES + nombrePlantilla + ".jasper");

        if (jasperResource.exists()) {
            try (InputStream inputStream = jasperResource.getInputStream()) {
                return (JasperReport) net.sf.jasperreports.engine.util.JRLoader.loadObject(inputStream);
            }
        }

        Resource jrxmlResource = resourceLoader.getResource(RUTA_REPORTES + nombrePlantilla + ".jrxml");
        if (jrxmlResource.exists()) {
            try (InputStream inputStream = jrxmlResource.getInputStream()) {
                return JasperCompileManager.compileReport(inputStream);
            }
        }

        throw new IllegalArgumentException("Plantilla de JasperReports no encontrada en la ruta: " + RUTA_REPORTES + nombrePlantilla + ".(jasper|jrxml)");
    }

    private byte[] exportarABytes(JasperPrint jasperPrint, ReporteFormato formato) throws JRException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        if (ReporteFormato.PDF.equals(formato)) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } else if (ReporteFormato.XLSX.equals(formato)) {
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setOnePagePerSheet(false);
            configuration.setRemoveEmptySpaceBetweenRows(true);
            configuration.setDetectCellType(true);
            configuration.setWhitePageBackground(false);
            exporter.setConfiguration(configuration);

            exporter.exportReport();
        }

        return outputStream.toByteArray();
    }
}