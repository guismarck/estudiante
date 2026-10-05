package app.estudiante.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "docente")
@PrimaryKeyJoinColumn(name = "idpersona", referencedColumnName = "idpersona")
public class Docente extends Persona {

    @Column(name = "cod_docente", nullable = false, length = 20, unique = true)
    private String codDocente;

    @Column(name = "especialidad", length = 100)
    private String especialidad;

    @Column(name = "estado", nullable = false)
    private boolean estado;
}