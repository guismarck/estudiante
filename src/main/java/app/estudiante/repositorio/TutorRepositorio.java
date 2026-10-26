package app.estudiante.repositorio;

import app.estudiante.modelo.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TutorRepositorio extends JpaRepository<Tutor,Integer> {
    @Query("""
            select e from Tutor e
            where lower(e.nombre_completo) like lower(concat('%', :search, '%'))
               or lower(e.apellido_completo) like lower(concat('%', :search, '%'))
               or lower(concat(concat(e.nombre_completo, ' '), e.apellido_completo))
                    like lower(concat('%', :search, '%'))
               or lower(e.ocupacion) like lower(concat('%', :search, '%'))
               or lower(e.cedula) like lower(concat('%', :search, '%'))
               or lower(e.telefonoPrincipal) like lower(concat('%', :search, '%'))
               or lower(e.telefonoSecundario) like lower(concat('%', :search, '%'))
            """)
    List<Tutor> busquedaGeneral(@Param("search") String search);
}
