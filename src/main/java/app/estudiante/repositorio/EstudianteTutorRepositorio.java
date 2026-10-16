package app.estudiante.repositorio;

import app.estudiante.modelo.EstudianteTutor;
import app.estudiante.modelo.EstudianteTutorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EstudianteTutorRepositorio extends JpaRepository<EstudianteTutor, EstudianteTutorId> {
    @Query("""
        SELECT et
        FROM EstudianteTutor et
        JOIN et.persona estudiante
        JOIN et.tutor tutor
        WHERE
            LOWER(COALESCE(estudiante.nombre_completo, ''))
                LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(COALESCE(estudiante.apellido_completo, ''))
                LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(COALESCE(tutor.nombre_completo, ''))
                LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(COALESCE(tutor.apellido_completo, ''))
                LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(COALESCE(et.parentesco, ''))
                LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(COALESCE(tutor.ocupacion, ''))
                LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(COALESCE(tutor.telefonoPrincipal, ''))
                LIKE LOWER(CONCAT('%', :search, '%'))
        """)
    List<EstudianteTutor> busquedaGeneral(
            @Param("search") String search);

    @Query(value="select et from EstudianteTutor et where et.persona.idpersona = :id")
    List<EstudianteTutor> findByEstudianteId(Integer id);

}
