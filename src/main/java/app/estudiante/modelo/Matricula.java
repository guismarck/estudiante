package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "matricula", 
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_estudiante_anio", columnNames = {"idpersona", "anio_lectivo"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmatricula")
    private Integer idMatricula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpersona", nullable = false)
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idGrado", nullable = false)
    private Grado grado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPlan_de_estudio", referencedColumnName = "idPlan_de_estudio", nullable = false)
    private PlandeEstudio planDeEstudio;

    @Column(name = "anio_lectivo", nullable = false)
    private Short anioLectivo;

    @Column(name = "turno", nullable = false)
    private String turno;

    @Column(name = "seccion", nullable = false, length = 5)
    private String seccion;

    @Column(name = "costo_matricula", nullable = false, precision = 10, scale = 2)
    private BigDecimal costoMatricula;

    @Column(name = "fecha_matricula", nullable = true, updatable = false)
    private LocalDateTime fechaMatricula;

    @Column(name = "estado_matricula", nullable = false)
    private String estadoMatricula;

    @PrePersist
    public void prePersist() {
        if (this.fechaMatricula == null) {
            this.fechaMatricula = LocalDateTime.now();
        }
        if (this.estadoMatricula == null) {
            this.estadoMatricula = "AC";
        }
        if (this.seccion == null || this.seccion.isBlank()) {
            this.seccion = "A";
        }
    }
}