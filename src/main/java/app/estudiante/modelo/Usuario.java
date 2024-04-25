package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
     private  Integer idusuario;
//     private Integer idDocente;
    @Column
     private String nombre;
    @Column
     private String contraseña;
    @Column
     private String tipo_usuario;
    @Column
     private String correo;
     @Column
     private boolean estado;
     @OneToOne
     @JoinColumn(name="idpersona",referencedColumnName = "idpersona")
     private Docente docente;
}
