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
public class Parcial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idParcial;
    @Column
    private String parcial;
}
