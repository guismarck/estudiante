package app.estudiante.repositorio;

import app.estudiante.modelo.Grado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradoRepositorio extends JpaRepository<Grado , Integer> {
}
