package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.EstudianteTutor;
import app.estudiante.modelo.EstudianteTutorId;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IEstudianteTutorServicio {

    List<EstudianteTutor> listarEstudianteTutor();
    EstudianteTutor buscarEstudianteTutorPorId(EstudianteTutorId id);
    EstudianteTutor guardarEstudianteTutor (EstudianteTutor estudianteTutor);
    void eliminarEstudianteTutor(EstudianteTutor estudianteTutor);
    List<EstudianteTutor> busquedaGeneral(  String search);


}
