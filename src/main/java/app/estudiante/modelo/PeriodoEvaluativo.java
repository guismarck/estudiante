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
@Table
public class PeriodoEvaluativo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idperiodo_evaluativo;
    @Column
    private String NombrePeriodo;
    @Column
    private  Integer NumeroPeriodo;
    @Column
    private Date AñoEscolar;
    @Column
    private boolean estado;

}
