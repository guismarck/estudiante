package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "detalle_plan_de_estudio")
public class DetallePlandeEstudio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddetalle_plan_de_estudio", columnDefinition = "INT UNSIGNED")
    private Integer iddetallePlanDeEstudio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "idPlan_de_estudio", 
        referencedColumnName = "idPlan_de_estudio", 
        columnDefinition = "INT UNSIGNED", 
        nullable = false
    )
    private PlandeEstudio planEstudio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "idAsignatura", 
        referencedColumnName = "idAsignatura", 
        columnDefinition = "INT UNSIGNED", 
        nullable = false
    )
    private Asignatura asignatura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "idDocente", 
        referencedColumnName = "idpersona", 
        columnDefinition = "INT UNSIGNED", 
        nullable = false
    )
    private Docente docente;

    @Column(name = "creado_por", length = 50)
    private String creadoPor;

    @Column(name = "creado_el", nullable = false, updatable = false)
    private LocalDateTime creadoEl;

    @Column(name = "actualizado_por", length = 50)
    private String actualizadoPor;

    @Column(name = "actualizado_el")
    private LocalDateTime actualizadoEl;
}