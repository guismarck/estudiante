package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//boilerplate
@Entity
@Data //get a set
@NoArgsConstructor //vacio
@AllArgsConstructor//lleno
@ToString
@Table
public class Calificaciones  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcalificaciones;
    @ManyToOne
    @JoinColumn(name = "iddetalle_plan_de_estudio", referencedColumnName = "iddetalle_plan_de_estudio")
    private DetallePlandeEstudio iddetalle_plan_de_estudio;
    @ManyToOne
    @JoinColumn(name = "idParcial", referencedColumnName = "idParcial")
    private  Parcial parcial;
    @Column
    private float acumulado;
    @Column
    private float examen;

}
