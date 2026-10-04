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
    @Query(value = """
        SELECT 
            m.id, m.nombre, m.codigo, m.recurso, m.component_key, m.path_img, m.modulo_padre_id,
            p.puede_buscar, p.puede_agregar, p.puede_modificar, p.puede_inactivar, 
            p.puede_procesar, p.puede_guardar, p.puede_exportar
        FROM sec_modulos m
        INNER JOIN sec_permisos_rol p ON m.id = p.modulo_id AND p.estado = 1
        INNER JOIN sec_roles r ON p.rol_id = r.id AND r.estado = 1
        WHERE r.codigo = :rolCodigo 
          AND m.estado = 1 
          AND p.puede_buscar = 1
        ORDER BY m.modulo_padre_id ASC, m.id ASC
        """, nativeQuery = true)
    List<Object[]> findModulosYPermisosByRol(@Param("rolCodigo") String rolCodigo);
}