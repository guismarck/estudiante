package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "calificaciones",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_nota_periodo_plan",
            columnNames = {"idmatricula", "iddetalle_plan_de_estudio", "idperiodo_evaluativo"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcalificacion", nullable = false)
    private Long idCalificacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idmatricula", nullable = false, foreignKey = @ForeignKey(name = "fk_calif_matricula"))
    private Matricula matricula;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "iddetalle_plan_de_estudio", nullable = false, foreignKey = @ForeignKey(name = "fk_calif_detalle_plan"))
    private DetallePlanDeEstudio detallePlanDeEstudio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idperiodo_evaluativo", nullable = false, foreignKey = @ForeignKey(name = "fk_calif_periodo"))
    private PeriodoEvaluativo periodoEvaluativo;

    @Column(name = "acumulado", nullable = false, precision = 5, scale = 2)
    private BigDecimal acumulado;

    @Column(name = "examen", nullable = false, precision = 5, scale = 2)
    private BigDecimal examen;

    @Column(name = "nota_final", insertable = false, updatable = false, precision = 5, scale = 2)
    private BigDecimal notaFinal;

    @Column(name = "creado_por", length = 50)
    private String creadoPor;

    @Column(name = "creado_el", nullable = false, insertable = false, updatable = false)
    private LocalDateTime creadoEl;

    @Column(name = "actualizado_por", length = 50)
    private String actualizadoPor;

    @Column(name = "actualizado_el", insertable = false, updatable = false)
    private LocalDateTime actualizadoEl;
}