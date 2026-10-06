package app.estudiante.repositorio;

import app.estudiante.modelo.PeriodoEvaluativo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodoEvaluativoRepositorio extends JpaRepository<PeriodoEvaluativo, Integer> {

    @Query("""
        SELECT p 
        FROM PeriodoEvaluativo p 
        WHERE YEAR(p.anioEscolar) = :anioEscolar 
          AND p.estado = true 
        ORDER BY p.numeroPeriodo ASC
    """)
    List<PeriodoEvaluativo> findByAnioEscolarAndEstadoTrueOrderByNumeroPeriodoAsc(@Param("anioEscolar") Integer anioEscolar);
}
