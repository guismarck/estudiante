package app.estudiante.repositorio;

import app.estudiante.modelo.PeriodoEvaluativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodoEvaluativoRepositorio extends JpaRepository<PeriodoEvaluativo, Integer> {
}
