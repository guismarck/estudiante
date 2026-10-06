package app.estudiante.modelo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "plan_de_estudio",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_plan_grado_anio", columnNames = {"idGrado", "anio_lectivo"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlandeEstudio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPlan_de_estudio")
    private Integer idPlanDeEstudio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idGrado", nullable = false, foreignKey = @ForeignKey(name = "fk_plan_grado"))
    private Grado grado;

    @Column(name = "anio_lectivo", nullable = false)
    private Integer anioLectivo;

    @Column(name = "estado", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean estado = true;

    @Column(name = "creado_por", length = 50)
    private String creadoPor;

    @CreationTimestamp
    @Column(name = "creado_el", nullable = false, updatable = false)
    private LocalDateTime creadoEl;

    @Column(name = "actualizado_por", length = 50)
    private String actualizadoPor;

    @UpdateTimestamp
    @Column(name = "actualizado_el")
    private LocalDateTime actualizadoEl;
}