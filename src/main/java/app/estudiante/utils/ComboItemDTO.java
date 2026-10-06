package app.estudiante.utils;

public record ComboItemDTO(
    Long id,
    String descripcion
) {
    // Constructor de conveniencia si tus entidades manejan ID como Integer
    public ComboItemDTO(Integer id, String descripcion) {
        this(id != null ? id.longValue() : null, descripcion);
    }
}