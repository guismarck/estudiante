package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.EstudianteTutor;
import app.estudiante.modelo.EstudianteTutorId;
import app.estudiante.repositorio.EstudianteTutorRepositorio;
import org.springframework.transaction.annotation.Transactional;
import app.estudiante.servicio.InterfacesServicios.IEstudianteTutorServicio;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EstudianteTutorServicios  implements IEstudianteTutorServicio {

    private final EstudianteTutorRepositorio estudianteTutorRepositorio;

    public EstudianteTutorServicios(
            EstudianteTutorRepositorio estudianteTutorRepositorio) {
        this.estudianteTutorRepositorio = estudianteTutorRepositorio;
    }
    @Override
    @Transactional(readOnly = true)
    public List<EstudianteTutor> listarEstudianteTutor() {
        return estudianteTutorRepositorio.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public EstudianteTutor buscarEstudianteTutorPorId(
            EstudianteTutorId id) {
        if (id == null) {
            throw new IllegalArgumentException(
                    "La clave del estudiante y tutor es obligatoria");
        }

        return estudianteTutorRepositorio.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstudianteTutor> busquedaGeneral(String search) {

        if (search == null || search.trim().isEmpty()) {
            return estudianteTutorRepositorio.findAll();
        }

        return estudianteTutorRepositorio.busquedaGeneral(
                search.trim()
        );
    }

    @Override
    public void guardarEstudianteTutor(EstudianteTutor estudianteTutor) {
        if (estudianteTutor == null || estudianteTutor.getId() == null) {
            throw new IllegalArgumentException(
                    "Los datos del estudiante y tutor son obligatorios");
        }

        if (estudianteTutor.getTutor() == null
                || estudianteTutor.getPersona() == null) {
            throw new IllegalArgumentException(
                    "Debe especificar el tutor y el estudiante");
        }

        estudianteTutorRepositorio.save(estudianteTutor);
    }

    @Override
    public void eliminarEstudianteTutor(EstudianteTutor estudianteTutor) {
        if (estudianteTutor == null || estudianteTutor.getId() == null) {
            throw new IllegalArgumentException(
                    "Debe especificar la relación que desea eliminar");
        }

        estudianteTutorRepositorio.deleteById(estudianteTutor.getId());
    }


}
