package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.CatalogoTarifa;
import java.util.List;

public interface ICatalogoTarifaServicio {
    List<CatalogoTarifa> obtenerTodas();
    List<CatalogoTarifa> obtenerPorAnio(Integer anioLectivo);
    CatalogoTarifa obtenerPorId(Integer id);
    CatalogoTarifa crear(CatalogoTarifa tarifa);
    CatalogoTarifa actualizar(Integer id, CatalogoTarifa tarifa);
    CatalogoTarifa buscarPorGradoAnioYConcepto(Integer idNivel, Integer anioLectivo, String concepto);
    void eliminar(Integer id);
    byte[] generarReporteTarifasPdf(Integer  anioLectivo);
}