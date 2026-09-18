package app.estudiante.repositorio;

import app.estudiante.modelo.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface PagoRepositorio extends JpaRepository<Pago,Integer> {

@Procedure(procedureName = "sp_procesar_emision_recibo")
    String procesarEmisionRecibo(
        @Param("p_idpersona") Integer idPersona,
        @Param("p_idSalon") Integer idSalon,
        @Param("p_anio_lectivo") Integer anioLectivo,
        @Param("p_concepto") String concepto,
        @Param("p_tipo_pago") String tipoPago,
        @Param("p_monto") BigDecimal monto,
        @Param("p_idtarifa") Integer idTarifa,
        @Param("p_fecha_transaccion") LocalDateTime fechaTransaccion,
        @Param("p_usuario") String usuario
    );
}
