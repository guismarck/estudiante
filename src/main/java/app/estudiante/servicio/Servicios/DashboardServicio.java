
package app.estudiante.servicio.Servicios;

import app.estudiante.repositorio.*;
import app.estudiante.utils.DashboardResumenDTO;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
public class DashboardServicio {

    private final EstudianteRepositorio estudianteRepositorio;
    private final DocenteRepositorio docenteRepositorio;
    private final GradoRepositorio gradoRepositorio;
    private final SalonRepositorio salonRepositorio;
    private final TutorRepositorio tutorRepositorio;
    private final AsignaturaRepositorio asignaturaRepositorio;
    private final MatriculaRepositorio matriculaRepositorio;

    public DashboardServicio(
            EstudianteRepositorio estudianteRepositorio,
            DocenteRepositorio docenteRepositorio,
            GradoRepositorio gradoRepositorio,
            SalonRepositorio salonRepositorio,
            TutorRepositorio tutorRepositorio,
            AsignaturaRepositorio asignaturaRepositorio,
            MatriculaRepositorio matriculaRepositorio) {

        this.estudianteRepositorio = estudianteRepositorio;
        this.docenteRepositorio = docenteRepositorio;
        this.gradoRepositorio = gradoRepositorio;
        this.salonRepositorio = salonRepositorio;
        this.tutorRepositorio = tutorRepositorio;
        this.asignaturaRepositorio = asignaturaRepositorio;
        this.matriculaRepositorio = matriculaRepositorio;
    }

    public DashboardResumenDTO obtenerResumen() {

        Short anioLectivo = (short) Year.now().getValue();

        // Totales generales
        long estudiantes = estudianteRepositorio.count();
        long docentes = docenteRepositorio.count();
        long grados = gradoRepositorio.count();
        long salones = salonRepositorio.count();
        long tutores = tutorRepositorio.count();
        long asignaturas = asignaturaRepositorio.count();

        // Estados de estudiantes
        long activos = estudianteRepositorio.contarEstudiantesActivos();
        long inactivos = estudianteRepositorio.contarEstudiantesInactivos();

        // Matrículas del año lectivo actual
        long matriculados =
                matriculaRepositorio.contarEstudiantesMatriculadosPorAnio(anioLectivo);

        return new DashboardResumenDTO(
                estudiantes,
                docentes,
                grados,
                salones,
                tutores,
                asignaturas,
                activos,
                inactivos,
                matriculados
        );
    }
}
