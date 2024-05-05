package app.estudiante.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
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
@Table(name = "estudiante")
@PrimaryKeyJoinColumn(name = "idpersona")
public class Estudiante extends Persona {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//   private  Integer idpersona ;
////   private  Integer idEstudiante;
    @Column
   private  String cod_estudiante;
    @Column
   private  String codigo_MINED;
    @Column
   private  String nombre_tutor;
    @Column
   private  boolean estado;
}
