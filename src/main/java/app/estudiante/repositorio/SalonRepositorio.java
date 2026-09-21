package app.estudiante.repositorio;

import app.estudiante.modelo.Salon;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalonRepositorio extends JpaRepository<Salon, Integer> {
    /**
     * Retorna la entidad Salon directamente.
     * Utiliza JOIN FETCH para traer el CatalogoSalon y el Grado en la misma consulta,
     * evitando errores de Lazy Initialization al serializar a JSON.
     */
    @Query("""
        SELECT s FROM Salon s
        JOIN FETCH s.catalogoSalon cs
        JOIN FETCH s.grado g
        LEFT JOIN Estudiante e ON e.salon.idSalon = s.idSalon
        WHERE g.idGrado = :idGrado
        GROUP BY s
        HAVING COUNT(e.idEstudiante) < cs.capacidad
    """)
    List<Salon> obtenerSalonesDisponiblesPorGrado(@Param("idGrado") Integer idGrado);
}
