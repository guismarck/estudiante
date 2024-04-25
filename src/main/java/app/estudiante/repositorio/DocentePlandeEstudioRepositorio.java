package app.estudiante.repositorio;

import app.estudiante.modelo.DocentePlandeEstudio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocentePlandeEstudioRepositorio extends JpaRepository<DocentePlandeEstudio,Integer> {
}
