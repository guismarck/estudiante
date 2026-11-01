package app.estudiante.repositorio;

import app.estudiante.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio  extends JpaRepository<Usuario,Long> {
    boolean existsByUsername(String username);
    boolean existsByUsernameAndIdNot(String username, Long id);
    Optional<Usuario> findByUsername(String username);
}
