package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data //get a set
@NoArgsConstructor //vacio
@AllArgsConstructor//lleno
@ToString
@Table
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer  idmatricula;
    @ManyToOne
    @JoinColumn(name = "idpersona", referencedColumnName = "idpersona")
    private Persona persona ;
    @ManyToOne
    @JoinColumn(name = " idPlan_de_estudio", referencedColumnName = " idPlan_de_estudio")
    private  PlandeEstudio plandeEstudio;
    @ManyToOne
    @JoinColumn(name = "idGrado", referencedColumnName = " idGrado")
    private Grado grado;
    @ManyToOne
    @JoinColumn(name = "idpago", referencedColumnName = " idpago")
    private  Pago pago;
    @Column
    private  String turno;
    @Column
    private  float costo_matricula;


}
