package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.DetallePlandeEstudio;
import app.estudiante.repositorio.DetallePlandeEstudioRepositorio;
import app.estudiante.servicio.InterfacesServicios.IDetallePlandeEstudioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DetallePlandeEstudioServicio implements IDetallePlandeEstudioServicio {
  @Autowired
   private DetallePlandeEstudioRepositorio detallePlandeEstudioRepositorio;
    @Override
    public List<DetallePlandeEstudio> ListarDetallePlandeEstudio() {
        List<DetallePlandeEstudio> detallePlandeEstudios = detallePlandeEstudioRepositorio.findAll();
        return detallePlandeEstudios;
    }

    @Override
    public DetallePlandeEstudio buscarDetallePlandeEstudioPorId(Integer iddetalle_plan_de_estudio) {
        DetallePlandeEstudio detallePlandeEstudio = detallePlandeEstudioRepositorio.findById(iddetalle_plan_de_estudio).orElse(null);
        return null;
    }

    @Override
    public void guardarDetallePlandeEstudio(DetallePlandeEstudio detallePlandeEstudio) {
    detallePlandeEstudioRepositorio.save(detallePlandeEstudio);
    }

    @Override
    public void eliminarDetallePlandeEstudio(DetallePlandeEstudio detallePlandeEstudio) {
        detallePlandeEstudioRepositorio.delete(detallePlandeEstudio);
    }
}
