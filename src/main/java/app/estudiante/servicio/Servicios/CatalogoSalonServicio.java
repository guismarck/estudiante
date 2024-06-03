package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.CatalogoSalon;
import app.estudiante.repositorio.CatalogoSalonRepositorio;
import app.estudiante.servicio.InterfacesServicios.ICatalogoSalonServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CatalogoSalonServicio  implements ICatalogoSalonServicio {
    @Autowired
    private CatalogoSalonRepositorio catalogoSalonRepositorio;
    @Override
    public List<CatalogoSalon> ListarCatalogoSalon() {
        List<CatalogoSalon> catalogoSalons = catalogoSalonRepositorio.findAll();
        return catalogoSalons;
    }

    @Override
    public CatalogoSalon buscarCatalogoSalonPorId(Integer idcatalaogo_salon) {
        CatalogoSalon catalogoSalon = catalogoSalonRepositorio.findById(idcatalaogo_salon).orElse(null);
        return catalogoSalon;
    }

    @Override
    public void guardarCatalogoSalon(CatalogoSalon catalogoSalon) {
      catalogoSalonRepositorio.save(catalogoSalon);
    }

    @Override
    public void eliminarCatalogoSalon(CatalogoSalon catalogoSalon) {
   catalogoSalonRepositorio.delete(catalogoSalon);
    }
}
