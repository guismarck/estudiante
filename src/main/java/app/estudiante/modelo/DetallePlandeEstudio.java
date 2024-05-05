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
public class DetallePlandeEstudio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iddetalle_plan_de_estudio;
    @ManyToOne
    @JoinColumn(name = "idPlan_de_estudio", referencedColumnName = "idPlan_de_estudio")
    private PlandeEstudio planEstudio;
    @ManyToOne
    @JoinColumn(name = "idAsignatura", referencedColumnName = "idAsignatura")
    private Asignatura asignatura;
    @ManyToOne
    @JoinColumn(name = "idperiodo_evaluativo", referencedColumnName = "idperiodo_evaluativo")
    private PeriodoEvaluativo periodoEvaluativo;
    @ManyToOne
    @JoinColumn(name = "idDocente_plan_de_estudio", referencedColumnName = "idDocente_plan_de_estudio")
    private DocentePlandeEstudio docentePlanEstudio;
    @Column
    private  String corte_evaluatico;
    @Column
    private String semestre;

}
