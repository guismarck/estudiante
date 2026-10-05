package app.estudiante.repositorio;

import app.estudiante.modelo.PermisoRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermisoRolRepository extends JpaRepository<PermisoRol, Long> {

    @Query("SELECT p FROM PermisoRol p " +
            "JOIN p.rol r " +
            "JOIN Usuario u JOIN u.roles ur " +
            "WHERE u.id = :usuarioId AND ur.id = r.id AND p.estado = true AND p.modulo.estado = true")
    List<PermisoRol> findPermisosByUsuarioId(@Param("usuarioId") Long usuarioId);
}
