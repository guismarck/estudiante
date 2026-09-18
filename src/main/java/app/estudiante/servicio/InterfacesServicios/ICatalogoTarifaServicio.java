package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.CatalogoTarifa;

import java.time.Year;
import java.util.List;

public interface ICatalogoTarifaServicio {
    List<CatalogoTarifa> obtenerTodas();
    List<CatalogoTarifa> obtenerPorAnio(Year anioLectivo);
    CatalogoTarifa obtenerPorId(Integer id);
    CatalogoTarifa crear(CatalogoTarifa tarifa);
    CatalogoTarifa actualizar(Integer id, CatalogoTarifa tarifa);
    CatalogoTarifa obtenerTarifaPorGradoYConcepto(Integer idNivel, Year anioLectivo, String concepto);
    void eliminar(Integer id);
    byte[] generarReporteTarifasPdf(Year anioLectivo);
}