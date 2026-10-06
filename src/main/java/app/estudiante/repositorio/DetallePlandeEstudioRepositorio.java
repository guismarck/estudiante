package app.estudiante.repositorio;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.estudiante.modelo.DetallePlanDeEstudio;

@Repository
public interface DetallePlandeEstudioRepositorio extends JpaRepository<DetallePlanDeEstudio , Integer> {

    @Query("""
 SELECT dpe 
        FROM DetallePlanDeEstudio dpe
        JOIN FETCH dpe.planDeEstudio pe
        JOIN FETCH dpe.asignatura a
        JOIN FETCH dpe.salon s
        WHERE pe.grado.idGrado = :idGrado
          AND pe.estado = true
    """)
    List<DetallePlanDeEstudio> findAsignaturasConSalonPorGrado(@Param("idGrado") Integer idGrado);
}
