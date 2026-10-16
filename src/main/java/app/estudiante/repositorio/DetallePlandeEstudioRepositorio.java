package app.estudiante.repositorio;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.estudiante.modelo.DetallePlanDeEstudio;
import app.estudiante.utils.DocenteAsignacionResponseDTO;

@Repository
public interface DetallePlandeEstudioRepositorio extends JpaRepository<DetallePlanDeEstudio , Long> {

    @Query("""
        SELECT new app.estudiante.utils.DocenteAsignacionResponseDTO(
            dpe.idDetallePlanDeEstudio,
            pe.idPlanDeEstudio,
            a.idAsignatura,
            a.nombre,
            g.idGrado,
            g.nombre,
            pe.anioLectivo,
            s.idSalon,
            s.seccion,
            s.turno
        )
        FROM DetallePlanDeEstudio dpe
        JOIN dpe.planDeEstudio pe
        JOIN pe.grado g
        JOIN dpe.asignatura a
        LEFT JOIN dpe.salon s
        WHERE dpe.docente.idpersona = :idDocente
          AND pe.estado = true
        ORDER BY pe.anioLectivo DESC, g.nombre, a.nombre
        """)
    List<DocenteAsignacionResponseDTO> findAsignacionesActivasPorDocente(
            @Param("idDocente") Integer idDocente);

    @Query("""
 SELECT dpe 
        FROM DetallePlanDeEstudio dpe
        JOIN FETCH dpe.planDeEstudio pe
        JOIN FETCH dpe.asignatura a
        JOIN FETCH dpe.salon s
        WHERE pe.grado.idGrado = :idGrado
          AND dpe.docente.idpersona = :idDocente
          AND pe.estado = true
    """)
    List<DetallePlanDeEstudio> findAsignaturasConSalonPorGrado(
            @Param("idGrado") Integer idGrado,
            @Param("idDocente") Integer idDocente);

    @Query("""
        SELECT dpe
        FROM DetallePlanDeEstudio dpe
        JOIN FETCH dpe.planDeEstudio pe
        JOIN FETCH dpe.asignatura a
        JOIN FETCH dpe.salon s
        WHERE pe.grado.idGrado = :idGrado
          AND s.idSalon = :idSalon
          AND dpe.docente.idpersona = :idDocente
          AND pe.estado = true
        """)
    List<DetallePlanDeEstudio> findAsignaturasConSalonPorGrado(
            @Param("idGrado") Integer idGrado,
            @Param("idSalon") Integer idSalon,
            @Param("idDocente") Integer idDocente);
}
