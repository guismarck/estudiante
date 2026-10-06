package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "detalle_plan_de_estudio",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_plan_asignatura",
            columnNames = {"idPlan_de_estudio", "idAsignatura"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallePlanDeEstudio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddetalle_plan_de_estudio", nullable = false)
    private Long idDetallePlanDeEstudio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "idPlan_de_estudio", 
        nullable = false, 
        foreignKey = @ForeignKey(name = "fk_det_plan")
    )
    private PlandeEstudio planDeEstudio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "idAsignatura", 
        nullable = false, 
        foreignKey = @ForeignKey(name = "fk_det_asig")
    )
    private Asignatura asignatura;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "idDocente", 
        referencedColumnName = "idpersona", 
        nullable = false, 
        foreignKey = @ForeignKey(name = "fk_det_docente")
    )
    private Docente docente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "idSalon", 
        foreignKey = @ForeignKey(name = "fk_det_salon")
    )
    private Salon salon;

    @Column(name = "creado_por", length = 50)
    private String creadoPor;

    @CreatedDate
    @Column(name = "creado_el", nullable = false, updatable = false)
    private LocalDateTime creadoEl;

    @Column(name = "actualizado_por", length = 50)
    private String actualizadoPor;

    @LastModifiedDate
    @Column(name = "actualizado_el")
    private LocalDateTime actualizadoEl;
}