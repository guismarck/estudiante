package app.estudiante.repositorio;

import app.estudiante.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio  extends JpaRepository<Usuario,Integer> {

}
