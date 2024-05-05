package app.estudiante.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table
public class Docente  extends Persona {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Integer idDocente;
// private Integer idpersona;
@Column
 private boolean estado;
}
