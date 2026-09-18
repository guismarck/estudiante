package app.estudiante.utils;

import org.springframework.http.MediaType;

public enum ReporteFormato {
    PDF("pdf", MediaType.APPLICATION_PDF, "inline"),
    XLSX("xlsx", MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"), "attachment");

    private final String extension;
    private final MediaType mediaType;
    private final String dispositionType;

    ReporteFormato(String extension, MediaType mediaType, String dispositionType) {
        this.extension = extension;
        this.mediaType = mediaType;
        this.dispositionType = dispositionType;
    }

    public String getExtension() { return extension; }
    public MediaType getMediaType() { return mediaType; }
    public String getDispositionType() { return dispositionType; }
}