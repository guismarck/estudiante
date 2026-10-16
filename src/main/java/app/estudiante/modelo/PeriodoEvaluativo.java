package app.estudiante.modelo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "periodo_evaluativo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PeriodoEvaluativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idperiodo_evaluativo")
    @EqualsAndHashCode.Include
    private Integer idPeriodoEvaluativo;

    @Column(name = "nombre_periodo", nullable = false, length = 30)
    private String nombrePeriodo;

    @Column(name = "numero_periodo", nullable = false)
    private Integer numeroPeriodo;

    @Column(name = "anio_escolar", nullable = false)
    private Integer anioEscolar;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "fecha_cierre_docente", nullable = false)
    private LocalDateTime fechaCierreDocente;

    @Column(name = "estado", nullable = false)
    @Builder.Default
    private Boolean estado = true;

    @Column(name = "creado_por", length = 50)
    private String creadoPor;

    @Column(name = "creado_el", nullable = false, insertable = false, updatable = false)
    private LocalDateTime creadoEl;

    @Column(name = "actualizado_por", length = 50)
    private String actualizadoPor;

    @Column(name = "actualizado_el", insertable = false, updatable = false)
    private LocalDateTime actualizadoEl;
}