package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.PeriodoEvaluativo;

import java.util.List;

public interface IPeridoEvaluativoServicio {
    public List<PeriodoEvaluativo> ListarPeriodoEvaluativo();
    public PeriodoEvaluativo buscarPeriodoEvaluativoPorId(Integer idperiodo_evaluativo);
    public void guardarPeriodoEvaluativo(PeriodoEvaluativo periodoEvaluativo);
    public  void eliminarPeriodoEvaluativo(PeriodoEvaluativo periodoEvaluativo);

}
