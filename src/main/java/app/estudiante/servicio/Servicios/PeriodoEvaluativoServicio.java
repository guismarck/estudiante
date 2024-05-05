package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.PeriodoEvaluativo;
import app.estudiante.repositorio.PeriodoEvaluativoRepositorio;
import app.estudiante.servicio.InterfacesServicios.IPeridoEvaluativoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PeriodoEvaluativoServicio implements IPeridoEvaluativoServicio {
  @Autowired
  private PeriodoEvaluativoRepositorio periodoEvaluativoRepositorio;
    @Override
    public List<PeriodoEvaluativo> ListarPeriodoEvaluativo() {
        List<PeriodoEvaluativo>   periodoEvaluativos = periodoEvaluativoRepositorio.findAll();
        return periodoEvaluativos;
    }

    @Override
    public PeriodoEvaluativo buscarPeriodoEvaluativoPorId(Integer idperiodo_evaluativo) {
        PeriodoEvaluativo periodoEvaluativo = periodoEvaluativoRepositorio.findById(idperiodo_evaluativo).orElse(null);
        return periodoEvaluativo;
    }

    @Override
    public void guardarPeriodoEvaluativo(PeriodoEvaluativo periodoEvaluativo) {
    periodoEvaluativoRepositorio.save(periodoEvaluativo);
    }

    @Override
    public void eliminarPeriodoEvaluativo(PeriodoEvaluativo periodoEvaluativo) {
        periodoEvaluativoRepositorio.delete(periodoEvaluativo);
    }
}
