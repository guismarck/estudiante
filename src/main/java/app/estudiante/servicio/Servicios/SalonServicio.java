
package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Salon;
import app.estudiante.modelo.Grado;
import app.estudiante.modelo.CatalogoSalon;
import app.estudiante.repositorio.SalonRepositorio;
import app.estudiante.repositorio.GradoRepositorio;
import app.estudiante.repositorio.CatalogoSalonRepositorio;
import app.estudiante.servicio.InterfacesServicios.ISalonServicio;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SalonServicio implements ISalonServicio {

    private final SalonRepositorio salonRepositorio;
    private final GradoRepositorio gradoRepositorio;
    private final CatalogoSalonRepositorio catalogoSalonRepositorio;

    public SalonServicio(
            SalonRepositorio salonRepositorio,
            GradoRepositorio gradoRepositorio,
            CatalogoSalonRepositorio catalogoSalonRepositorio) {

        this.salonRepositorio = salonRepositorio;
        this.gradoRepositorio = gradoRepositorio;
        this.catalogoSalonRepositorio = catalogoSalonRepositorio;
    }

    @Override
    public List<Salon> ListarSalon() {
        return salonRepositorio.findAll();
    }

    @Override
    public Salon buscarSalonPorId(Integer idSalon) {
        return salonRepositorio.findById(idSalon).orElse(null);
    }

    @Override
    @Transactional
    public void guardarSalon(Salon salon) {

        if (salon.getGrado() == null
                || salon.getGrado().getIdGrado() == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar un grado.");
        }

        if (salon.getCatalogoSalon() == null
                || salon.getCatalogoSalon().getIdCatalogoSalon() == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar un catálogo de salón.");
        }

        Integer idGrado = salon.getGrado().getIdGrado();

        Integer idCatalogo = Math.toIntExact(salon.getCatalogoSalon().getIdCatalogoSalon());


        Grado grado = gradoRepositorio.findById(idGrado)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No existe el grado con ID: " + idGrado));

        CatalogoSalon catalogo =
                catalogoSalonRepositorio.findById(idCatalogo)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "No existe el catálogo de salón con ID: "
                                                + idCatalogo));

        salon.setGrado(grado);
        salon.setCatalogoSalon(catalogo);
        salon.setCreadoPor("webapi-user");//TODO MODIFICAR POR EL USUARIO AUTENTICADO TOMANDOLO DESDE EL CONTEXTO DE LA SESSION DE SPRING
        salon.setCreadoEl(LocalDateTime.now());

        var salonesList = salonRepositorio.findSalonByAnioTurnoAnio(salon.getAnioLectivo(), salon.getTurno(), salon.getCatalogoSalon().getIdCatalogoSalon());
        if(!salonesList.isEmpty()){
            throw new RuntimeException("El salon " + salon.getCatalogoSalon().getNombreSalon()
                    + " ya se encuentra asignado para el turno "
                    + salon.getTurno()
                    + " en el año "
                    + salon.getAnioLectivo());
        }
        salonRepositorio.save(salon);
    }

    @Override
    public void eliminarSalon(Salon salon) {
        salonRepositorio.delete(salon);
    }

    @Override
    public List<Salon> obtenerSalonesDisponiblesPorGrado(
            Integer idGrado) {
        return salonRepositorio
                .obtenerSalonesDisponiblesPorGrado(idGrado);
    }
}
