package app.estudiante.repositorio;

import app.estudiante.modelo.DetallePlandeEstudio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePlandeEstudioRepositorio extends JpaRepository<DetallePlandeEstudio , Integer> {
}
