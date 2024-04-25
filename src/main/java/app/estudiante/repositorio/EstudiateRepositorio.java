package app.estudiante.repositorio;

import app.estudiante.modelo.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudiateRepositorio extends JpaRepository<Estudiante,Integer> {

}
