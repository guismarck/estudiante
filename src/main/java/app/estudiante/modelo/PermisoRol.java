package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "sec_permisos_rol")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermisoRol extends AuditableEntity{

    @EmbeddedId
    private PermisoRolId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("rolId")
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("moduloId")
    @JoinColumn(name = "modulo_id")
    private Modulo modulo;

    @Column(name = "puede_buscar", nullable = false)
    private Boolean puedeBuscar;

    @Column(name = "puede_agregar", nullable = false)
    private Boolean puedeAgregar;

    @Column(name = "puede_modificar", nullable = false)
    private Boolean puedeModificar;

    @Column(name = "puede_inactivar", nullable = false)
    private Boolean puedeInactivar;

    @Column(name = "puede_procesar", nullable = false)
    private Boolean puedeProcesar;

    @Column(name = "puede_guardar", nullable = false)
    private Boolean puedeGuardar;

    @Column(name = "puede_exportar", nullable = false)
    private Boolean puedeExportar;

    @Column(nullable = false)
    private Boolean estado;

}