package app.estudiante.repositorio;

import app.estudiante.modelo.Salon;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalonRepositorio extends JpaRepository<Salon, Integer> {
    boolean existsByCatalogoSalon_IdCatalogoSalonAndTurnoAndAnioLectivo(
            Long idCatalogoSalon, String turno, Short anioLectivo);

    boolean existsByCatalogoSalon_IdCatalogoSalonAndTurnoAndAnioLectivoAndIdSalonNot(
            Long idCatalogoSalon, String turno, Short anioLectivo, Integer idSalon);

    boolean existsByGrado_IdGradoAndSeccionAndTurnoAndAnioLectivo(
            Integer idGrado, String seccion, String turno, Short anioLectivo);

    boolean existsByGrado_IdGradoAndSeccionAndTurnoAndAnioLectivoAndIdSalonNot(
            Integer idGrado, String seccion, String turno, Short anioLectivo, Integer idSalon);

    /**
     * Retorna la entidad Salon directamente.
     * Utiliza JOIN FETCH para traer el CatalogoSalon y el Grado en la misma consulta,
     * evitando errores de Lazy Initialization al serializar a JSON.
     */
    @Query("""
        SELECT s FROM Salon s
        JOIN FETCH s.catalogoSalon cs
        JOIN FETCH s.grado g
        WHERE g.idGrado = :idGrado
        GROUP BY s
    """)

    List<Salon> obtenerSalonesDisponiblesPorGrado(@Param("idGrado") Integer idGrado);

    @Query("""
        SELECT s FROM Salon s
        JOIN FETCH s.catalogoSalon cs
        JOIN FETCH s.grado g
        WHERE g.idGrado = :idGrado
          AND s.anioLectivo = :anioLectivo
        ORDER BY s.seccion, s.turno
        """)
    List<Salon> obtenerSalonesPorGradoYAnio(
            @Param("idGrado") Integer idGrado,
            @Param("anioLectivo") Short anioLectivo);


    @Query ("select s from Salon s where s.anioLectivo = :anio and s.turno = :turno and s.catalogoSalon.idCatalogoSalon = :salonId")
    List<Salon> findSalonByAnioTurnoAnio(int anio, String turno, long salonId);
}
