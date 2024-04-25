package app.estudiante.repositorio;

import app.estudiante.modelo.Parcial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcialRepositorio extends JpaRepository<Parcial, Integer> {
}
