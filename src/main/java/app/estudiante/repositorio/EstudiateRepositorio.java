package app.estudiante.repositorio;

import app.estudiante.modelo.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudiateRepositorio extends JpaRepository<Estudiante,Integer> {
    @Query(value = "select e from Estudiante e where cod_estudiante = ?1 or nombre_completo = ?1 or apellido_completo = ?1")
    List<Estudiante> busquedaGeneral(String search);
}
