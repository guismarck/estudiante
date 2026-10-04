package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PermisoRolId implements Serializable {

    @Column(name = "rol_id")
    private Short rolId;

    @Column(name = "modulo_id")
    private Long moduloId;
}
