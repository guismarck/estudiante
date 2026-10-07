package app.estudiante.repositorio;

import app.estudiante.modelo.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocenteRepositorio extends JpaRepository<Docente,Integer> {
    @Query(value = "select e from Docente e where e.codDocente = ?1 or e.nombre_completo = ?1 or e.apellido_completo = ?1")
    List<Docente> busquedaGeneral(String search);

}
