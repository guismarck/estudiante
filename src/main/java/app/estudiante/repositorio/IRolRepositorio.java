package app.estudiante.repositorio;
import app.estudiante.modelo.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRolRepositorio extends JpaRepository<RolEntity, Short> {
    Optional<RolEntity> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
}