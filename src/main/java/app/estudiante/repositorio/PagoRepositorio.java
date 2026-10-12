package app.estudiante.repositorio;

import app.estudiante.modelo.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepositorio extends JpaRepository<Pago,Integer> {
    @Query(value = """
            SELECT
                ct.id_tarifa AS idTarifa,
                ct.concepto AS concepto,
                CAST(ct.anio_lectivo AS UNSIGNED) AS anioLectivo,
                ct.monto AS montoTarifa,
                COALESCE(dp.montoPagado, 0.00) AS montoPagado,
                GREATEST(ct.monto - COALESCE(dp.montoPagado, 0.00), 0.00) AS saldoRestante
            FROM catalogo_tarifa ct
            LEFT JOIN (
                SELECT dp.id_tarifa, SUM(dp.monto) AS montoPagado
                FROM detalle_pago dp
                INNER JOIN pago p ON p.idpago = dp.idpago
                WHERE p.idpersona = :idPersona
                GROUP BY dp.id_tarifa
            ) dp ON dp.id_tarifa = ct.id_tarifa
            WHERE ct.id_tarifa = :idTarifa
            """, nativeQuery = true)
    Optional<PagoSaldoProjection> buscarSaldoPorPersonaYTarifa(
            @Param("idPersona") Integer idPersona,
            @Param("idTarifa") Integer idTarifa
    );

    @Query(value = """
            SELECT
                p.idpersona AS idPersona,
                ct.id_tarifa AS idTarifa,
                ct.concepto AS arancel,
                CAST(ct.anio_lectivo AS UNSIGNED) AS anioLectivo,
                CAST(ct.monto AS DECIMAL(10,2)) AS montoOficial,
                CAST(SUM(dp.monto) AS DECIMAL(10,2)) AS totalPagado,
                CAST(GREATEST(ct.monto - SUM(dp.monto), 0.00) AS DECIMAL(10,2)) AS saldoPendiente,
                CASE
                    WHEN SUM(dp.monto) >= ct.monto THEN 'CANCELADO'
                    ELSE 'PENDIENTE'
                END AS estado
            FROM pago p
            INNER JOIN detalle_pago dp ON p.idpago = dp.idpago
            INNER JOIN catalogo_tarifa ct ON dp.id_tarifa = ct.id_tarifa
            WHERE p.idpersona = :idPersona
              AND CAST(ct.anio_lectivo AS UNSIGNED) = :anioLectivo
              AND UPPER(ct.concepto) LIKE '%MATRICULA%'
            GROUP BY p.idpersona, ct.id_tarifa, ct.concepto, ct.anio_lectivo, ct.monto
            ORDER BY ct.id_tarifa
            """, nativeQuery = true)
    List<SolvenciaMatriculaProjection> buscarSolvenciaMatricula(
            @Param("idPersona") Integer idPersona,
            @Param("anioLectivo") Integer anioLectivo
    );
}
