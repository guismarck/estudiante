package app.estudiante.repositorio;

import app.estudiante.modelo.Calificaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalificacionesRepositorio extends JpaRepository<Calificaciones, Integer> {
}
