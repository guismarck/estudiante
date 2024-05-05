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
public class DetallePago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer iddetalle_pago;
    @Column
    private  float costo_mesualidad;
    @Column
    private float descuento ;
    @Column
    private float iva;
    @Column
    private  float subtota;
    @Column
    private  float total;
    @ManyToOne
    @JoinColumn(name = "idpago", referencedColumnName = "idpago")
    private Pago pago;

}
