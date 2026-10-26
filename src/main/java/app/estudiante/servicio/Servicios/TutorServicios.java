package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Tutor;
import app.estudiante.repositorio.TutorRepositorio;
import app.estudiante.servicio.InterfacesServicios.ITutorServicio;
import app.estudiante.utils.NumerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutorServicios implements ITutorServicio {

    @Autowired
    private TutorRepositorio tutorRepositorio;
    @Override
    public List<Tutor> ListarTutor() {
        List<Tutor> tutores = tutorRepositorio.findAll();
        return tutores;
    }

    @Override
    public Tutor buscarTutorPorId(Integer idTutor) {
        Tutor tutores = tutorRepositorio.findById(idTutor).orElse(null);
        return tutores;
    }

    @Override
    public Tutor guardarTutor(Tutor tutor) {
        return tutorRepositorio.save(tutor);
    }


    @Override
    public List<Tutor> busquedaGeneral(String search) {
        var isInt = NumerUtils.isInt(search);
        if (isInt) {
            Optional<Tutor> tutor = tutorRepositorio.findById(Integer.parseInt(search));
            if (tutor.isPresent()) return List.of(tutor.get());
        }
        return tutorRepositorio.busquedaGeneral(search.trim());
    }



    @Override
    public void eliminarTutor(Tutor tutor) {
        tutorRepositorio.delete(tutor);

    }
}
