package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.DocentePlandeEstudio;

import java.util.List;

public interface IDocentePlandeEstudioServicio {
    public List<DocentePlandeEstudio> ListarDocentePlandeEstudio();
    public DocentePlandeEstudio buscarDocentePlandeEstudioPorId(Integer idDocente_plan_de_estudio);
    public void guardarDocentePlandeEstudio(DocentePlandeEstudio docentePlandeEstudio);
    public  void eliminarDocentePlandeEstudio(DocentePlandeEstudio docentePlandeEstudio);
}
