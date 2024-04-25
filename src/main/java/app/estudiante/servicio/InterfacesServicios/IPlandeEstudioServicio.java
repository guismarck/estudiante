package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.PlandeEstudio;

import java.util.List;

public interface IPlandeEstudioServicio {
    public List<PlandeEstudio> ListarPlandeEstudio();
    public PlandeEstudio buscarPlandeEstudioPorId(Integer idPlan_de_estudio);
    public void guardarPlandeEstudio(PlandeEstudio plandeEstudio);
    public  void eliminarPlandeEstudio(PlandeEstudio plandeEstudio);
}
