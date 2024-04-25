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
public class Salon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer idSalon;
    @ManyToOne
    @JoinColumn(name="idGrado", referencedColumnName = "idGrado")
    private Grado grado;
    @ManyToOne
    @JoinColumn(name="idcatalaogo_salon", referencedColumnName = "idcatalaogo_salon")
    private  CatalogoSalon catalogoSalon;
    @Column
    private  String turmo;

}
