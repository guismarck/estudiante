package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.PlandeEstudio;
import app.estudiante.repositorio.PlandeEstudioRepositorio;
import app.estudiante.servicio.InterfacesServicios.IPlandeEstudioServicio;
import app.estudiante.utils.NumerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PlandeEstudioServicio implements IPlandeEstudioServicio {
    @Autowired
    private PlandeEstudioRepositorio plandeEstudioRepositorio;
    @Override
    public List<PlandeEstudio> ListarPlandeEstudio() {
        List<PlandeEstudio> plandeEstudios = plandeEstudioRepositorio.findAll();
        return plandeEstudios;
    }

   /* @Override
    public List<PlandeEstudio> busquedaGeneral(String search) {
        var isInt = NumerUtils.isInt(search);
        if (isInt) return List.of(buscarPlandeEstudioPorId(Integer.parseInt(search)));
        return plandeEstudioRepositorio.busquedaGeneral(search);
    }*/

    @Override
    public PlandeEstudio buscarPlandeEstudioPorId(Integer idPlan_de_estudio) {
        PlandeEstudio plandeEstudio = plandeEstudioRepositorio.findById(idPlan_de_estudio).orElse(null);
        return plandeEstudio;
    }

    @Override
    public void guardarPlandeEstudio(PlandeEstudio plandeEstudio) {
        plandeEstudioRepositorio.save(plandeEstudio);
    }

    @Override
    public void eliminarPlandeEstudio(PlandeEstudio plandeEstudio) {
        plandeEstudioRepositorio.delete(plandeEstudio);
    }
}
