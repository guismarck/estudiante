package app.estudiante.repositorio;
import app.estudiante.modelo.Calificacion;
import app.estudiante.servicio.InterfacesServicios.IMejorPromedioProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DashboardPromediosRepositorio extends  Repository<Calificacion, Long> {
    @Query(value = """
        SELECT
            CONCAT(
                p.nombre_completo,
                ' ',
                p.apellido_completo
            ) AS nombreCompleto,

            ROUND(AVG(c.nota_final), 2) AS promedio

        FROM calificaciones c

        INNER JOIN matricula m
            ON m.idmatricula = c.idmatricula

        INNER JOIN estudiante e
            ON e.idpersona = m.idpersona

        INNER JOIN persona p
            ON p.idpersona = e.idpersona

        INNER JOIN salon s
            ON s.idSalon = m.idSalon

        INNER JOIN grado g
            ON g.idGrado = s.idGrado

        INNER JOIN nivel_educativo n
            ON n.idnivel = g.idnivel

        INNER JOIN detalle_plan_de_estudio d
            ON d.iddetalle_plan_de_estudio =
               c.iddetalle_plan_de_estudio

        INNER JOIN plan_de_estudio pe
            ON pe.idPlan_de_estudio = d.idPlan_de_estudio

        INNER JOIN periodo_evaluativo periodo
            ON periodo.idperiodo_evaluativo =
               c.idperiodo_evaluativo

        WHERE
            m.anio_lectivo = :anio
            AND pe.anio_lectivo = :anio
            AND periodo.anio_escolar = :anio
            AND n.idnivel = :idNivel
            AND m.estado = 'ACTIVA'
            AND e.estado = 1

        GROUP BY
            e.idpersona,
            p.nombre_completo,
            p.apellido_completo

        ORDER BY
            promedio DESC,
            nombreCompleto ASC

        LIMIT 5
        """, nativeQuery = true)
    List<IMejorPromedioProjection> obtenerTopCincoPromedios(
            @Param("idNivel") Integer idNivel,
            @Param("anio") Integer anio
    );
}
