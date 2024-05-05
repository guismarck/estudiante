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
@Table
public class Barrio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 private  Integer idbarrio;
    @ManyToOne
    @JoinColumn(name = "idpersona", referencedColumnName = "idpersona")
    private Persona persona ;
    @Column
 private  String pais;
    @Column
 private  String departamento;
    @Column
 private  String municipio;
    @Column
 private  String cumunida;

}
