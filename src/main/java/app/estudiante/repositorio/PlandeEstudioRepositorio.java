package app.estudiante.repositorio;

import app.estudiante.modelo.Matricula;
import app.estudiante.modelo.PlandeEstudio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlandeEstudioRepositorio extends JpaRepository<PlandeEstudio, Integer> {
   List<PlandeEstudio> findByAnioLectivo(Integer anioLectivo);
   Optional<PlandeEstudio> findByGrado_IdGradoAndAnioLectivo(Integer idGrado, Integer anioLectivo);
   // @Query(value = "select e from plan_de_estudio e where idPlan_de_estudio = ?1  ")
    //List<PlandeEstudio> busquedaGeneral(String search);
}
