package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(
    name = "catalogo_tarifa",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_tarifa_nivel_mes_anio", 
            columnNames = {"idnivel", "anio_lectivo", "mes_aplicacion", "concepto"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogoTarifa extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarifa")
    private Integer idTarifa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idnivel", nullable = true)
    private NivelEducativo nivel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_grado", nullable = true)
    private Grado grado;

    @Column(name = "anio_lectivo", nullable = false)
    private Integer anioLectivo;

    @Column(name = "concepto", nullable = false, length = 100)
    private String concepto;

    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Builder.Default
    @Column(name = "es_obligatorio", nullable = false)
    private Boolean esObligatorio = true;

    @Column(name = "mes_aplicacion")
    private Integer mesAplicacion;
}