package app.estudiante.repositorio;

import app.estudiante.modelo.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalonRepositorio extends JpaRepository<Salon, Integer> {
}
