package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class AuditableEntity {

    @Column(name = "creado_por", nullable = false, length = 50, updatable = false)
    private String creadoPor;

    @Column(name = "creado_el", nullable = false, updatable = false)
    private LocalDateTime creadoEl;

    @Column(name = "actualizado_por", length = 50)
    private String actualizadoPor;

    @Column(name = "actualizado_el")
    private LocalDateTime actualizadoEl;

    @PrePersist
    public void prePersist() {
        if (this.creadoPor == null || this.creadoPor.isBlank()) {
            this.creadoPor = "SYSTEM";
        }
        this.creadoEl = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.actualizadoEl = LocalDateTime.now();
    }
}