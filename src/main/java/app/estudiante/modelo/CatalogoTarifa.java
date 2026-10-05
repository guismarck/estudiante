package app.estudiante.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(
    name = "catalogo_tarifa",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_tarifa_grado_anio_concepto", 
            columnNames = {"idGrado", "anio_lectivo", "concepto"}
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
    @JoinColumn(name = "idnivel", nullable = false)
    private NivelEducativo idnivel;

    @Column(name = "anio_lectivo", nullable = false, length = 4)
    private Integer anioLectivo;

    @Column(name = "concepto", nullable = false)
    private String concepto;

    @NotNull(message = "El monto es obligatorio")
    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;
}