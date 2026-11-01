package app.estudiante.repositorio;

import app.estudiante.modelo.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocenteRepositorio extends JpaRepository<Docente,Integer> {
    @Query("""
            select d from Docente d
            where lower(d.codDocente) like lower(concat('%', :search, '%'))
               or lower(d.nombre_completo) like lower(concat('%', :search, '%'))
               or lower(d.apellido_completo) like lower(concat('%', :search, '%'))
               or lower(concat(concat(d.nombre_completo, ' '), d.apellido_completo))
                    like lower(concat('%', :search, '%'))
            """)
    List<Docente> busquedaGeneral(@Param("search") String search);

    Optional<Docente> findByCodDocente(String codDocente);

    @Query("""
            select d from Docente d
            join Usuario u on u.persona.idpersona = d.idpersona
            where (lower(d.cedula) = lower(:identificacion)
                   or lower(d.email) = lower(:identificacion)
                   or lower(d.codDocente) = lower(:identificacion))
              and u.username = :username
              and d.estado = true
              and u.estado = 1
            """)
    Optional<Docente> buscarParaRecuperacion(
            @Param("identificacion") String identificacion,
            @Param("username") String username);

}
