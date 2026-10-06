package app.estudiante.repositorio;

import app.estudiante.modelo.Calificacion;
import app.estudiante.utils.NominaEstudianteResponseDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CalificacionesRepositorio extends JpaRepository<Calificacion, Integer> {

@Query(value = """
        SELECT 
            m.idmatricula AS idMatricula,
            e.cod_estudiante AS codEstudiante,
            e.codigo_MINED AS codigoMined,
            CONCAT(p.primer_apellido, ' ', p.primer_nombre) AS nombreCompletoEstudiante,
            COALESCE(c.acumulado, 0.00) AS acumulado,
            COALESCE(c.examen, 0.00) AS examen,
            COALESCE(c.nota_final, 0.00) AS notaFinal
        FROM matricula m
        INNER JOIN estudiante e ON m.idpersona = e.idpersona
        INNER JOIN persona p ON e.idpersona = p.idpersona
        INNER JOIN salon s ON m.idSalon = s.idSalon
        INNER JOIN detalle_plan_de_estudio dpe ON dpe.idSalon = s.idSalon
        LEFT JOIN calificacion c ON c.idmatricula = m.idmatricula
            AND c.iddetalle_plan_de_estudio = :idDetallePlan
            AND c.idperiodo_evaluativo = :idPeriodo
        WHERE dpe.iddetalle_plan_de_estudio = :idDetallePlan
          AND m.estado = 'ACTIVA'
        ORDER BY p.primer_apellido ASC, p.primer_nombre ASC
    """, nativeQuery = true)
    List<NominaEstudianteResponseDTO> obtenerNominaEstudiantes(
        @Param("idDetallePlan") Integer idDetallePlan,
        @Param("idPeriodo") Integer idPeriodo
    );

  Optional<Calificacion> findByMatricula_IdMatriculaAndDetallePlanDeEstudio_IdDetallePlanDeEstudioAndPeriodoEvaluativo_IdPeriodoEvaluativo(
    Integer idMatricula, Integer idDetallePlan, Integer idPeriodo
);
}
