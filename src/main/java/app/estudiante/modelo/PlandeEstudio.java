package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

//boilerplate
@Entity
@Data //get a set
@NoArgsConstructor //vacio
@AllArgsConstructor//lleno
@ToString
@Table(name = "plan_de_estudio")
public class PlandeEstudio  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 private  Integer idPlan_de_estudio;
    @ManyToOne
    @JoinColumn(name = "idSalon", referencedColumnName = "idSalon")
    private Salon salon;
 @Column
 private  Date año_electivo;
 @Column
 private Date fecha_inicio;
 @Column
 private  Date  fecha_fin;
 @Column
 private  String periodo;
 @Column
 private  boolean estado;

}
