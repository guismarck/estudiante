package app.estudiante.repositorio;

import app.estudiante.modelo.CatalogoTarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Repository
public interface CatalogoTarifaRepositorio extends JpaRepository<CatalogoTarifa, Integer> {
    List<CatalogoTarifa> findByAnioLectivo(Year anioLectivo);
    List<CatalogoTarifa> findByGradoIdGradoAndAnioLectivo(Integer idGrado, Year anioLectivo);
    Optional<CatalogoTarifa> findByGradoIdGradoAndAnioLectivoAndConcepto(
            Integer idGrado, 
            Year anioLectivo, 
            String concepto
    );
}