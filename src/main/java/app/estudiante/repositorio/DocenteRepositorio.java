package app.estudiante.repositorio;

import app.estudiante.modelo.Docente;
import app.estudiante.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocenteRepositorio extends JpaRepository<Docente,Integer> {
    @Query(value = "select e from Docente e where e.codDocente = ?1 or e.nombre_completo = ?1 or e.apellido_completo = ?1")
    List<Docente> busquedaGeneral(String search);

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
