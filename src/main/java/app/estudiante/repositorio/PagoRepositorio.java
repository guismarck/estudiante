package app.estudiante.repositorio;

import app.estudiante.modelo.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepositorio extends JpaRepository<Pago,Integer> {
}
