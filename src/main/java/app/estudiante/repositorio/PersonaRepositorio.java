package app.estudiante.repositorio;

import app.estudiante.modelo.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepositorio extends JpaRepository<Persona, Integer> {
    Optional<Persona> findByCedula(String cedula);
}
