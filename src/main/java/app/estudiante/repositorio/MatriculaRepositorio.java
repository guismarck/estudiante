package app.estudiante.repositorio;

import app.estudiante.modelo.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatriculaRepositorio extends JpaRepository<Matricula, Integer> {
    @Query("""
            select m from Matricula m
            left join fetch m.estudiante estudiante
            left join fetch m.salon salon
            left join fetch salon.grado grado
            left join fetch grado.nivelEducativo
            left join fetch salon.catalogoSalon
            where m.idMatricula = :id
            """)
    Optional<Matricula> buscarDetallePorId(@Param("id") Integer id);

    boolean existsBySalon_IdSalon(Integer idSalon);
    @Query(value = "select e from Matricula e where idMatricula = ?1  ")
    List<Matricula> busquedaGeneral(String search);

    @Query("SELECT COUNT(m) > 0 FROM Matricula m WHERE m.estudiante.idpersona = :idPersona AND m.anioLectivo = :anioLectivo")
    boolean existsByEstudianteIdAndAnioLectivo(@Param("idPersona") Long idPersona, @Param("anioLectivo") Short anioLectivo);

    // Cantidad de matrículas del año lectivo indicado.
    @Query(
            "SELECT COUNT(m) FROM Matricula m " +
                    "WHERE m.anioLectivo = :anio " +
                    "AND UPPER(m.estadoMatricula) IN ('AC', 'ACTIVA')"
    )
    long contarMatriculasActivasPorAnio(
            @Param("anio") Short anio
    );

    // Cantidad de estudiantes únicos matriculados en el año indicado.
    @Query(
            "SELECT COUNT(DISTINCT m.estudiante.idpersona) " +
                    "FROM Matricula m " +
                    "WHERE m.anioLectivo = :anio " +
                    "AND UPPER(m.estadoMatricula) IN ('AC', 'ACTIVA')"
    )
    long contarEstudiantesMatriculadosPorAnio(
            @Param("anio") Short anio
    );


}
