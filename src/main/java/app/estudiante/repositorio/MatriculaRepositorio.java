package app.estudiante.repositorio;

import app.estudiante.modelo.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface MatriculaRepositorio extends JpaRepository<Matricula, Integer> {
    @Query(value = "select e from Matricula e where idmatricula = ?1  ")
    List<Matricula> busquedaGeneral(String search);
}
