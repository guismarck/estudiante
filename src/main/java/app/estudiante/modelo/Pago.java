package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Data //get a set
@NoArgsConstructor //vacio
@AllArgsConstructor//lleno
@ToString
@Table
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer idpago;
    @Column
    private  String concepto;
    @Column
    private Date  fecha_pago;
    @Column
    private  String tipo_pago;

}
