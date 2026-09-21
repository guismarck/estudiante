package app.estudiante.repositorio;

import app.estudiante.modelo.CatalogoTarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CatalogoTarifaRepositorio extends JpaRepository<CatalogoTarifa, Integer> {
    List<CatalogoTarifa> findByAnioLectivo(Integer anioLectivo);
    @Query("""
           SELECT t 
        FROM CatalogoTarifa t 
        JOIN t.idnivel n 
        WHERE n.idnivel = :idNivel 
          AND t.anioLectivo = :anioLectivo 
          AND LOWER(t.concepto) = LOWER(:concepto)
    """)
    Optional<CatalogoTarifa> buscarPorGradoAnioYConcepto(
        @Param("idNivel") Integer idNivel, 
        @Param("anioLectivo") Integer anioLectivo, 
        @Param("concepto") String concepto
    );
}