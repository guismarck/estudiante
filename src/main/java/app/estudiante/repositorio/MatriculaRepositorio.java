package app.estudiante.repositorio;

import app.estudiante.modelo.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface MatriculaRepositorio extends JpaRepository<Matricula, Integer> {
    @Query(value = "select e from Matricula e where idMatricula = ?1  ")
    List<Matricula> busquedaGeneral(String search);

    @Query("SELECT COUNT(m) > 0 FROM Matricula m WHERE m.estudiante.idpersona = :idPersona AND m.anioLectivo = :anioLectivo")
    boolean existsByEstudianteIdAndAnioLectivo(@Param("idPersona") Long idPersona, @Param("anioLectivo") Short anioLectivo);
}
