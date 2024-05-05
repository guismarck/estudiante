package app.estudiante.repositorio;

import app.estudiante.modelo.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatriculaRepositorio extends JpaRepository<Matricula, Integer> {

}
