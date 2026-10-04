package app.estudiante.repositorio;

import app.estudiante.modelo.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteRepositorio extends JpaRepository<Estudiante,Integer> {
    @Query(value = "select e from Estudiante e")
    List<Estudiante> busquedaGeneral(String search);
}
