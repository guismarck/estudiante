

package app.estudiante.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "tutor")
@PrimaryKeyJoinColumn(name = "idpersona")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

public class Tutor  extends Persona{


    @Column(name = "ocupacion", nullable = false, unique = true, length = 45)
    private String ocupacion;

    @Column(name = "telefono_principal",  nullable = false, unique = true, length = 45)
    private String telefonoPrincipal;

    @Column(name = "telefono_secundario",  nullable = false, unique = true, length = 45)
    private String telefonoSecundario;


}
