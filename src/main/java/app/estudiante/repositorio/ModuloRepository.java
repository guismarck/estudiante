package app.estudiante.repositorio;

import app.estudiante.modelo.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Long> {

    /**
     * Consulta los módulos asignados a un rol específico con sus permisos activos.
     */
    @Query("SELECT DISTINCT m FROM Modulo m " +
            "JOIN PermisoRol pr ON pr.modulo.id = m.id " +
            "JOIN pr.rol r " +
            "JOIN Usuario u JOIN u.roles ur " +
            "WHERE u.id = :usuarioId AND ur.id = r.id " +
            "AND m.estado = true AND pr.estado = true AND pr.puedeBuscar = true " +
            "ORDER BY m.orden ASC")
    List<Modulo> findModulosAutorizadosPorUsuario(@Param("usuarioId") Long usuarioId);
}