package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Salon;
import app.estudiante.modelo.Grado;
import app.estudiante.modelo.CatalogoSalon;
import app.estudiante.exception.recursosNoEncontradoException;
import app.estudiante.repositorio.CatalogoSalonRepositorio;
import app.estudiante.repositorio.DetallePlandeEstudioRepositorio;
import app.estudiante.repositorio.DocentePlandeEstudioRepositorio;
import app.estudiante.repositorio.GradoRepositorio;
import app.estudiante.repositorio.MatriculaRepositorio;
import app.estudiante.repositorio.SalonRepositorio;
import app.estudiante.servicio.InterfacesServicios.ISalonServicio;
import app.estudiante.utils.SalonRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class SalonServicio implements ISalonServicio {
   @Autowired
   private SalonRepositorio salonRepositorio;

   @Autowired
   private GradoRepositorio gradoRepositorio;

   @Autowired
   private CatalogoSalonRepositorio catalogoSalonRepositorio;

   @Autowired
   private MatriculaRepositorio matriculaRepositorio;

   @Autowired
   private DetallePlandeEstudioRepositorio detallePlandeEstudioRepositorio;

   @Autowired
   private DocentePlandeEstudioRepositorio docentePlandeEstudioRepositorio;

    @Override
    public List<Salon> ListarSalon() {
        return salonRepositorio.findAll();
    }

    @Override
    public List<Salon> listarSalones() {
        return ListarSalon();
    }

    @Override
    public Salon buscarSalonPorId(Integer idSalon) {
        Salon salon = salonRepositorio.findById(idSalon).orElse(null);
        return salon;
    }

    @Override
    public Salon obtenerSalonPorId(Integer idSalon) {
        return salonRepositorio.findById(idSalon)
                .orElseThrow(() -> new recursosNoEncontradoException(
                        "No se encontró el salón con Id " + idSalon));
    }

    @Override
    public void guardarSalon(Salon salon) {
        salonRepositorio.save(salon);
    }

    @Override
    public void eliminarSalon(Salon salon) {
        salonRepositorio.delete(salon);
    }

    @Override
    @Transactional
    public Salon crearSalon(SalonRequestDTO request) {
        Salon salon = new Salon();
        aplicarDatos(salon, request);
        validarUnicidad(salon, null);
        return salonRepositorio.save(salon);
    }

    @Override
    @Transactional
    public Salon actualizarSalon(Integer idSalon, SalonRequestDTO request) {
        Salon salon = obtenerSalonPorId(idSalon);
        aplicarDatos(salon, request);
        validarUnicidad(salon, idSalon);
        return salonRepositorio.save(salon);
    }

    @Override
    @Transactional
    public void eliminarSalonPorId(Integer idSalon) {
        Salon salon = obtenerSalonPorId(idSalon);
        if (matriculaRepositorio.existsBySalon_IdSalon(idSalon)
                || detallePlandeEstudioRepositorio.existsBySalon_IdSalon(idSalon)
                || docentePlandeEstudioRepositorio.existsBySalon_IdSalon(idSalon)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar el salón porque tiene matrículas o asignaciones relacionadas.");
        }
        salonRepositorio.delete(salon);
    }

    private void aplicarDatos(Salon salon, SalonRequestDTO request) {
        Grado grado = gradoRepositorio.findById(request.idGrado())
                .orElseThrow(() -> new recursosNoEncontradoException(
                        "No se encontró el grado con Id " + request.idGrado()));
        CatalogoSalon catalogoSalon = catalogoSalonRepositorio.findById(request.idCatalogoSalon())
                .orElseThrow(() -> new recursosNoEncontradoException(
                        "No se encontró el catálogo de salón con Id " + request.idCatalogoSalon()));

        salon.setGrado(grado);
        salon.setCatalogoSalon(catalogoSalon);
        salon.setAnioLectivo(request.anioLectivo());
        salon.setTurno(request.turno().trim());
        salon.setSeccion(request.seccion() == null || request.seccion().isBlank()
                ? "A" : request.seccion().trim());
    }

    private void validarUnicidad(Salon salon, Integer idSalon) {
        boolean duplicadoCatalogo = idSalon == null
                ? salonRepositorio.existsByCatalogoSalon_IdCatalogoSalonAndTurnoAndAnioLectivo(
                        salon.getCatalogoSalon().getIdCatalogoSalon(),
                        salon.getTurno(), salon.getAnioLectivo())
                : salonRepositorio.existsByCatalogoSalon_IdCatalogoSalonAndTurnoAndAnioLectivoAndIdSalonNot(
                        salon.getCatalogoSalon().getIdCatalogoSalon(),
                        salon.getTurno(), salon.getAnioLectivo(), idSalon);
        boolean duplicadoSeccion = idSalon == null
                ? salonRepositorio.existsByGrado_IdGradoAndSeccionAndTurnoAndAnioLectivo(
                        salon.getGrado().getIdGrado(), salon.getSeccion(),
                        salon.getTurno(), salon.getAnioLectivo())
                : salonRepositorio.existsByGrado_IdGradoAndSeccionAndTurnoAndAnioLectivoAndIdSalonNot(
                        salon.getGrado().getIdGrado(), salon.getSeccion(),
                        salon.getTurno(), salon.getAnioLectivo(), idSalon);

        if (duplicadoCatalogo || duplicadoSeccion) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe un salón con el catálogo, sección, turno y año lectivo indicados.");
        }
    }

    @Override
    public List<Salon> obtenerSalonesDisponiblesPorGrado(Integer idGrado) {
        List<Salon> salones = salonRepositorio.obtenerSalonesDisponiblesPorGrado(idGrado);
        return salones;
    }
}
