package app.estudiante.repositorio;

import app.estudiante.modelo.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignaturaRepositorio extends JpaRepository<Asignatura,Integer> {
}
