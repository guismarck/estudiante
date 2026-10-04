package app.estudiante.repositorio;

import app.estudiante.modelo.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    /**
     * Busca una persona por su cédula de identidad.
     */
    Optional<Persona> findByCedula(String cedula);

    /**
     * Busca una persona por su correo electrónico.
     */
    Optional<Persona> findByCorreo(String correo);

    /**
     * Busca una persona por su número de partida de nacimiento.
     */
    Optional<Persona> findByPartidaNacimiento(String partidaNacimiento);

    /**
     * Verifica la existencia de una cédula para evitar duplicados en registros.
     */
    boolean existsByCedula(String cedula);

    /**
     * Verifica la existencia de un correo para evitar duplicados en usuarios.
     */
    boolean existsByCorreo(String correo);

    /**
     * Búsqueda de personas por coincidencia parcial de nombre o apellido.
     * Aprovecha el índice `idx_persona_apellidos` definido en la entidad.
     */
    @Query("SELECT p FROM Persona p WHERE " +
            "LOWER(p.nombreCompleto) LIKE LOWER(CONCAT('%', :criterio, '%')) OR " +
            "LOWER(p.apellidoCompleto) LIKE LOWER(CONCAT('%', :criterio, '%'))")
    List<Persona> buscarPorNombreOApellido(@Param("criterio") String criterio);

    /**
     * Búsqueda paginada de personas por coincidencia parcial de nombre o apellido.
     */
    @Query("SELECT p FROM Persona p WHERE " +
            "LOWER(p.nombreCompleto) LIKE LOWER(CONCAT('%', :criterio, '%')) OR " +
            "LOWER(p.apellidoCompleto) LIKE LOWER(CONCAT('%', :criterio, '%'))")
    Page<Persona> buscarPorNombreOApellidoPaginado(@Param("criterio") String criterio, Pageable pageable);
}