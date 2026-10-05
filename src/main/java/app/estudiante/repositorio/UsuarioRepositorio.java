package app.estudiante.repositorio;

import app.estudiante.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio  extends JpaRepository<Usuario,Integer> {

    Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String username);


    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.roles WHERE u.username = :username")
    Optional<Usuario> findByUsernameWithRoles(@Param("username") String username);

    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.roles WHERE u.id = :id")
    Optional<Usuario> findByIdConRoles(@Param("id") Long id);


}
