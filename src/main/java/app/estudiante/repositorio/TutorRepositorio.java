package app.estudiante.repositorio;

import app.estudiante.modelo.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TutorRepositorio extends JpaRepository<Tutor,Integer> {
    @Query(value = "select e from Tutor e where  nombre_completo = ?1 or apellido_completo = ?1   or ocupacion= ?1 ")
    List<Tutor> busquedaGeneral(String search);
}
