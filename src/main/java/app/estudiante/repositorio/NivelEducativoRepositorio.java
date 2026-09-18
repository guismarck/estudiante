package app.estudiante.repositorio;

import app.estudiante.modelo.NivelEducativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NivelEducativoRepositorio extends JpaRepository<NivelEducativo, Integer> {
}