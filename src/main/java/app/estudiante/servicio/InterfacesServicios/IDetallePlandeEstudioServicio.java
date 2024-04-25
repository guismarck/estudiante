package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.DetallePlandeEstudio;

import java.util.List;

public interface IDetallePlandeEstudioServicio {
    public List<DetallePlandeEstudio> ListarDetallePlandeEstudio();
    public DetallePlandeEstudio buscarDetallePlandeEstudioPorId(Integer iddetalle_plan_de_estudio);
    public void guardarDetallePlandeEstudio (DetallePlandeEstudio detallePlandeEstudio);
    public  void eliminarDetallePlandeEstudio(DetallePlandeEstudio detallePlandeEstudio);

}
