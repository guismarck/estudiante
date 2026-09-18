package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "grado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Grado extends AuditableEntity {
 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idGrado")
    private Integer idGrado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idnivel", nullable = false, foreignKey = @ForeignKey(name = "fk_grado_nivel"))
    private NivelEducativo nivelEducativo;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

}
