package app.estudiante.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "docente")
@EqualsAndHashCode(callSuper=true)
public class Docente  extends Persona {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Integer idDocente;
// private Integer idpersona;
@Column
 private boolean estado;
}
