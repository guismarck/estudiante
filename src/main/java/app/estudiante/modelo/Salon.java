package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "salon",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_salon_catalogo_turno_anio",
                        columnNames = {"idcatalogo_salon", "turno", "anio_lectivo"}
                ),
                @UniqueConstraint(
                        name = "uk_grado_seccion_turno_anio",
                        columnNames = {"idGrado", "seccion", "turno", "anio_lectivo"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Salon extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSalon", columnDefinition = "INT UNSIGNED")
    private Integer idSalon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "idGrado",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_salon_grado")
    )
    private Grado grado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "idcatalogo_salon",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_salon_catalogo")
    )
    private CatalogoSalon catalogoSalon;

    @Column(name = "anio_lectivo", nullable = false)
    private Short anioLectivo;

    @Column(name = "turno", nullable = false, length = 20)
    private String turno;

    @Column(name = "seccion", nullable = false, length = 5)
    private String seccion;

    @PrePersist
    public void prePersist() {
        if (this.seccion == null || this.seccion.isBlank()) {
            this.seccion = "A";
        }
    }
}
