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
@Table(name = "docente")
@PrimaryKeyJoinColumn(name = "idpersona")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Docente  extends Persona {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Integer idDocente;
// private Integer idpersona;


 @Column(name = "cod_docente", nullable = false, unique = true, length = 45)
 private String codDocente;

 @Column(name = "especialidad", unique = true, length = 50)
 private String especialidad;

 @Column(name = "estado", nullable = false)
 private Boolean estado = true;


}
