package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.CatalogoSalon;

import java.util.List;

public interface ICatalogoSalonServicio {
    public List<CatalogoSalon> ListarCatalogoSalon();
    public CatalogoSalon buscarCatalogoSalonPorId(Integer idcatalaogo_salon);
    public void guardarCatalogoSalon(CatalogoSalon catalogoSalon);
    public  void eliminarCatalogoSalon(CatalogoSalon catalogoSalon);
}
