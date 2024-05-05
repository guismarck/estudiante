package app.estudiante.repositorio;

import app.estudiante.modelo.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocenteRepositorio extends JpaRepository<Docente,Integer> {

}
