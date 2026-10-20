package app.estudiante.repositorio;

import app.estudiante.modelo.PeriodoEvaluativo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodoEvaluativoRepositorio extends JpaRepository<PeriodoEvaluativo, Integer> {

    List<PeriodoEvaluativo> findByAnioEscolarAndEstadoTrueOrderByNumeroPeriodoAsc(Integer anioEscolar);

    @Query(value = """
        SELECT permite_edicion AS permiteEdicion,
               mensaje_pantalla AS mensajePantalla,
               fecha_cierre_docente AS fechaCierreDocente
        FROM vw_periodos_estado_pantalla
        WHERE idperiodo_evaluativo = :idPeriodo
        """, nativeQuery = true)
    Optional<EstadoPeriodoEvaluativoProjection> obtenerEstadoEdicion(
            @Param("idPeriodo") Integer idPeriodo);

    @Query(value = """
        SELECT EXISTS (
            SELECT 1
            FROM autorizacion_extemporanea
            WHERE iddetalle_plan_de_estudio = :idDetallePlan
              AND idperiodo_evaluativo = :idPeriodo
              AND estado = 1
              AND fecha_expiracion >= CURRENT_TIMESTAMP
        )
        """, nativeQuery = true)
    boolean existeAutorizacionExtemporanea(
            @Param("idDetallePlan") Long idDetallePlan,
            @Param("idPeriodo") Integer idPeriodo);

    List<PeriodoEvaluativo> findByAnioEscolar(Integer anioEscolar);

    List<PeriodoEvaluativo> findByEstadoTrue();
}
