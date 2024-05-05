package app.estudiante.repositorio;

import app.estudiante.modelo.DetallePago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePagoRepositorio extends JpaRepository<DetallePago, Integer> {
}
