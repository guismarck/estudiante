package app.estudiante.repositorio;

import app.estudiante.modelo.Calificacion;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CalificacionesRepositorio extends JpaRepository<Calificacion, Long> {

    boolean existsByMatricula_IdMatricula(Integer idMatricula);

    boolean existsByDetallePlanDeEstudio_IdDetallePlanDeEstudio(Long idDetallePlanDeEstudio);

    @Query(value = """
        SELECT
            m.idmatricula AS idMatricula,
            e.cod_estudiante AS codEstudiante,
            e.codigo_MINED AS codigoMined,
            CONCAT(p.apellido_completo, ' ', p.nombre_completo) AS nombreCompletoEstudiante,
            COALESCE(c.acumulado, 0.00) AS acumulado,
            COALESCE(c.examen, 0.00) AS examen,
            COALESCE(c.nota_final, 0.00) AS notaFinal,
            COALESCE(c.conducta, 'E') AS conducta
        FROM matricula m
        INNER JOIN estudiante e ON e.idpersona = m.idpersona
        INNER JOIN persona p ON p.idpersona = e.idpersona
        INNER JOIN salon s ON s.idSalon = m.idSalon
        LEFT JOIN calificaciones c
            ON c.idmatricula = m.idmatricula
            AND :idDetallePlan IS NOT NULL
            AND :idPeriodo IS NOT NULL
            AND c.iddetalle_plan_de_estudio = :idDetallePlan
            AND c.idperiodo_evaluativo = :idPeriodo
        WHERE m.idSalon = :idSalon
          AND s.idGrado = :idGrado
          AND m.estado = 'ACTIVA'
        ORDER BY p.apellido_completo ASC, p.nombre_completo ASC
        """, nativeQuery = true)
    List<NominaEstudianteProjection> obtenerNominaActivaPorSalon(
            @Param("idGrado") Integer idGrado,
            @Param("idSalon") Integer idSalon,
            @Param("idDetallePlan") Long idDetallePlan,
            @Param("idPeriodo") Integer idPeriodo);

  Optional<Calificacion> findByMatricula_IdMatriculaAndDetallePlanDeEstudio_IdDetallePlanDeEstudioAndPeriodoEvaluativo_IdPeriodoEvaluativo(
    Integer idMatricula, Long idDetallePlan, Integer idPeriodo
);
}
