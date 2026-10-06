package app.estudiante.modelo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    @Column(name = "nombre_periodo", nullable = false, length = 100)
    private String nombrePeriodo;

    @Column(name = "numero_periodo", nullable = false)
    private Integer numeroPeriodo;

    @Column(name = "anio_escolar", nullable = false)
    private LocalDate anioEscolar;

    @Column(name = "estado", nullable = false)
    private Boolean estado = true;
}