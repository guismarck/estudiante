package app.estudiante.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "nivel_educativo",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_nivel_nombre", columnNames = {"nombre"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NivelEducativo extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("idnivel")
    @Column(name = "idnivel")
    private Integer idNivel;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "descripcion", length = 150)
    private String descripcion;
}
