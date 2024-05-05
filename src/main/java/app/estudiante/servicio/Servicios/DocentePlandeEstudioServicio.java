package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.DocentePlandeEstudio;
import app.estudiante.repositorio.DocentePlandeEstudioRepositorio;
import app.estudiante.servicio.InterfacesServicios.IDocentePlandeEstudioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DocentePlandeEstudioServicio implements IDocentePlandeEstudioServicio {
    @Autowired
    private DocentePlandeEstudioRepositorio docentePlandeEstudioRepositorio;
    @Override
    public List<DocentePlandeEstudio> ListarDocentePlandeEstudio() {
        List<DocentePlandeEstudio> docentePlandeEstudios = docentePlandeEstudioRepositorio.findAll();
        return docentePlandeEstudios;
    }

    @Override
    public DocentePlandeEstudio buscarDocentePlandeEstudioPorId(Integer idDocente_plan_de_estudio) {
        DocentePlandeEstudio docentePlandeEstudio = docentePlandeEstudioRepositorio.findById(idDocente_plan_de_estudio).orElse(null);
        return null;
    }

    @Override
    public void guardarDocentePlandeEstudio(DocentePlandeEstudio docentePlandeEstudio) {
      docentePlandeEstudioRepositorio.save(docentePlandeEstudio);
    }

    @Override
    public void eliminarDocentePlandeEstudio(DocentePlandeEstudio docentePlandeEstudio) {
    docentePlandeEstudioRepositorio.delete(docentePlandeEstudio);
    }
}
