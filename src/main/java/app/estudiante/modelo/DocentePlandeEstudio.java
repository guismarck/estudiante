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
public class DocentePlandeEstudio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDocente_plan_de_estudio;
    @ManyToOne
    @JoinColumn(name = "idpersona", referencedColumnName = "idpersona")
    private  Persona persona;
    @ManyToOne
    @JoinColumn(name = "idSalon", referencedColumnName = "idsalon")
    private  Salon salon;
}
