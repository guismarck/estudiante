package app.estudiante.repositorio;

import app.estudiante.modelo.CatalogoSalon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public interface CatalogoSalonRepositorio extends JpaRepository<CatalogoSalon , Integer> {

    @Modifying
    @Query("UPDATE CatalogoSalon s SET s.capacidad = s.capacidad - 1, s.actualizadoEl = CURRENT_TIMESTAMP WHERE s.idCatalogoSalon = :idSalon AND s.capacidad > 0")
    int decrementarCapacidadSiDisponible(@Param("idSalon") Long idSalon);
}
