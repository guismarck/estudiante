package app.estudiante.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data //get a set
@NoArgsConstructor //vacio
@AllArgsConstructor//lleno
@ToString
@Table(name= "pago")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer idpago;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMatricula", nullable = false)
    private Matricula matricula;
    @Column
    private String num_recibo;
    @Column
    private  String concepto;
    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;
    @Column
    private Date  fecha_pago;
    @Column
    private  String tipo_pago;

}
