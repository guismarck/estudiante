package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.DetallePlanDeEstudio;
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
    public List<DetallePlanDeEstudio> ListarDetallePlandeEstudio() {
        List<DetallePlanDeEstudio> detallePlandeEstudios = detallePlandeEstudioRepositorio.findAll();
        return detallePlandeEstudios;
    }

    @Override
    public DetallePlanDeEstudio buscarDetallePlandeEstudioPorId(Integer iddetalle_plan_de_estudio) {
        DetallePlanDeEstudio detallePlandeEstudio = detallePlandeEstudioRepositorio.findById(iddetalle_plan_de_estudio).orElse(null);
        return detallePlandeEstudio; 
    }

    @Override
    public void guardarDetallePlandeEstudio(DetallePlanDeEstudio detallePlandeEstudio) {
    detallePlandeEstudioRepositorio.save(detallePlandeEstudio);
    }

    @Override
    public void eliminarDetallePlandeEstudio(DetallePlanDeEstudio detallePlandeEstudio) {
        detallePlandeEstudioRepositorio.delete(detallePlandeEstudio);
    }
}
