package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;

//boilerplate
@Entity
@Data //get a set
@NoArgsConstructor //vacio
@AllArgsConstructor//lleno
@ToString
@Table(name = "persona")
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer idpersona;
    @Column
    private String nombre_completo;
    @Column
    private String apellido_completo;
    @Column
    private  String sexo;
    @Column
    private String direccion;
    @Column
    private String partidad_nacimiento;
    @Column
    private Date fecha_nacimiento ;
    @Column
    private String cedula;

}
