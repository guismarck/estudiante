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
@Table (name ="catalaogo_salon")
public class CatalogoSalon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcatalogo_salon;
    @Column
    private  String nombre_salon;
}
