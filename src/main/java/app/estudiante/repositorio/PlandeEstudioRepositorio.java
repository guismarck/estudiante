package app.estudiante.repositorio;

import app.estudiante.modelo.PlandeEstudio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlandeEstudioRepositorio extends JpaRepository<PlandeEstudio, Integer> {
}
