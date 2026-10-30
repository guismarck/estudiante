package app.estudiante.repositorio;

import app.estudiante.modelo.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepositorio extends JpaRepository<Estudiante,Integer> {
    Optional<Estudiante> findByCodEstudiante(String codEstudiante);

    @Query("""
            select e from Estudiante e
            where lower(e.codEstudiante) like lower(concat('%', :search, '%'))
               or lower(e.nombre_completo) like lower(concat('%', :search, '%'))
               or lower(e.apellido_completo) like lower(concat('%', :search, '%'))
               or lower(concat(concat(e.nombre_completo, ' '), e.apellido_completo))
                    like lower(concat('%', :search, '%'))
            """)
    List<Estudiante> busquedaGeneral(@Param("search") String search);


    // Contar estudiantes activos
    @Query("SELECT COUNT(e) FROM Estudiante e WHERE e.estado = true")
    Long contarEstudiantesActivos();

    // Contar estudiantes inactivos
    @Query("SELECT COUNT(e) FROM Estudiante e WHERE e.estado = false")
    Long contarEstudiantesInactivos();
}
