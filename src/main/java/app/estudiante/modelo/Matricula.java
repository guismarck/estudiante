package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "matricula",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_estudiante_anio", columnNames = {"idpersona", "anio_lectivo"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Matricula extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmatricula", columnDefinition = "INT UNSIGNED")
    private Integer idMatricula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "idpersona",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_mat_estudiante")
    )
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "idSalon",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_mat_salon")
    )
    private Salon salon;

    // Se mapea como Short o Year para soportar el tipo YEAR de MySQL
    @Column(name = "anio_lectivo", nullable = false)
    private Short anioLectivo;

    @Column(name = "costo_matricula", precision = 10, scale = 2)
    private BigDecimal costoMatricula;

    @Column(name = "fecha_matricula", nullable = false, updatable = false)
    private LocalDateTime fechaMatricula;

    @Column(name = "estado", nullable = false, length = 20)
    private String estadoMatricula;

    @PrePersist
    public void prePersist() {
        if (this.fechaMatricula == null) {
            this.fechaMatricula = LocalDateTime.now();
        }
        if (this.estadoMatricula == null) {
            this.estadoMatricula = "ACTIVA";
        }
        if (this.costoMatricula == null) {
            this.costoMatricula = BigDecimal.ZERO;
        }
    }
}
