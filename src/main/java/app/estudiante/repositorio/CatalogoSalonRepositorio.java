package app.estudiante.repositorio;

import app.estudiante.modelo.CatalogoSalon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface CatalogoSalonRepositorio extends JpaRepository<CatalogoSalon , Integer> {
}
