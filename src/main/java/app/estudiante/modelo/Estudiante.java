package app.estudiante.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "estudiante")
@PrimaryKeyJoinColumn(name = "idpersona")
public class Estudiante extends Persona {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//   private  Integer idpersona ;
////   private  Integer idEstudiante;
    @Column

    @NotNull(message = "CODIGO DE ESTUDINATE REQUERIDO")
    @NotBlank(message = "CODIGO DE ESTUDINATE REQUERIDO")
    @NotEmpty(message = "CODIGO DE ESTUDINATE REQUERIDO")
   private  String cod_estudiante;
    @NotNull(message = "CODIGO DE MINEDID REQUERIDO")
    @NotBlank(message = "CODIGO DE MINEDID REQUERIDO")
    @NotEmpty(message = "CODIGO DE MINEDID REQUERIDO")
    @Column
   private  String codigo_MINED;
    @NotNull(message = "MOBRE DEL TUTOR REQUERIDO")
    @NotBlank(message = "MOBRE DEL TUTOR REQUERIDO")
    @NotEmpty(message = "MOBRE DEL TUTOR REQUERIDO")
    @Column
   private  String nombre_tutor;
    @Column
   private  boolean estado;
}
