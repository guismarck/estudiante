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
public class Asignatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  idAsignatura;
    @Column
    private String nombre;

}
