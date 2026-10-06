package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.DetallePlanDeEstudio;

import java.util.List;

public interface IDetallePlandeEstudioServicio {
    public List<DetallePlanDeEstudio> ListarDetallePlandeEstudio();
    public DetallePlanDeEstudio buscarDetallePlandeEstudioPorId(Integer iddetalle_plan_de_estudio);
    public void guardarDetallePlandeEstudio (DetallePlanDeEstudio detallePlandeEstudio);
    public  void eliminarDetallePlandeEstudio(DetallePlanDeEstudio detallePlandeEstudio);

}
