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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class SalonServicio implements ISalonServicio {
    private final MatriculaRepositorio matriculaRepositorio;
    private final DetallePlandeEstudioRepositorio detallePlandeEstudioRepositorio;  
    private final DocentePlandeEstudioRepositorio docentePlandeEstudioRepositorio;
    private final SalonRepositorio salonRepositorio;
    private final GradoRepositorio gradoRepositorio;
    private final CatalogoSalonRepositorio catalogoSalonRepositorio;

    public SalonServicio(
            SalonRepositorio salonRepositorio,
            GradoRepositorio gradoRepositorio,
            CatalogoSalonRepositorio catalogoSalonRepositorio,
            MatriculaRepositorio matriculaRepositorio,
            DetallePlandeEstudioRepositorio detallePlandeEstudioRepositorio,
            DocentePlandeEstudioRepositorio docentePlandeEstudioRepositorio) {

        this.salonRepositorio = salonRepositorio;
        this.gradoRepositorio = gradoRepositorio;
        this.catalogoSalonRepositorio = catalogoSalonRepositorio;
        this.matriculaRepositorio = matriculaRepositorio;
        this.detallePlandeEstudioRepositorio = detallePlandeEstudioRepositorio;
        this.docentePlandeEstudioRepositorio = docentePlandeEstudioRepositorio;
    }


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
        return salonRepositorio.findById(idSalon).orElse(null);
    }

    @Override
    public Salon obtenerSalonPorId(Integer idSalon) {
        return salonRepositorio.findById(idSalon)
                .orElseThrow(() -> new recursosNoEncontradoException(
                        "No se encontró el salón con Id " + idSalon));
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

        // Un salón sin ID es nuevo; con ID es una actualización.
        boolean esNuevo = salon.getIdSalon() == null;

        // Los datos de creación solo se asignan al crear.
        // Al actualizar se conservan los que ya tenía el registro.
        if (esNuevo) {
            salon.setCreadoPor("webapi-user");//TODO MODIFICAR POR EL USUARIO AUTENTICADO TOMANDOLO DESDE EL CONTEXTO DE LA SESSION DE SPRING
            salon.setCreadoEl(LocalDateTime.now());
        }

        String seccionNueva = textoNormalizado(salon.getSeccion());
        String turnoNuevo = textoNormalizado(salon.getTurno());

        // 0. Salón exactamente igual: mismo nombre de salón, grado, sección, turno y año.
        boolean registradoIgual = salonRepositorio.findAll().stream()
                .anyMatch(existente ->
                        esOtroSalon(existente, salon)
                                && existente.getGrado() != null
                                && Objects.equals(existente.getGrado().getIdGrado(), grado.getIdGrado())
                                && existente.getCatalogoSalon() != null
                                && Objects.equals(existente.getCatalogoSalon().getIdCatalogoSalon(),
                                catalogo.getIdCatalogoSalon())
                                && textoNormalizado(existente.getSeccion()).equals(seccionNueva)
                                && textoNormalizado(existente.getTurno()).equals(turnoNuevo)
                                && Objects.equals(existente.getAnioLectivo(), salon.getAnioLectivo()));

        if (registradoIgual) {
            throw new RuntimeException("Este salón ya está registrado: "
                    + catalogo.getNombreSalon()
                    + " - " + grado.getNombre()
                    + ", sección " + salon.getSeccion()
                    + ", turno " + salon.getTurno()
                    + ", año " + salon.getAnioLectivo() + ".");
        }

        // 1. El salón del catálogo no puede repetirse en el mismo turno y año
        //    (se ignora el propio registro cuando se está actualizando).
        var salonesList = salonRepositorio.findSalonByAnioTurnoAnio(
                salon.getAnioLectivo(),
                salon.getTurno(),
                salon.getCatalogoSalon().getIdCatalogoSalon());

        boolean salonOcupado = salonesList.stream()
                .anyMatch(existente -> esOtroSalon(existente, salon));

        if (salonOcupado) {
            throw new RuntimeException("El salon " + salon.getCatalogoSalon().getNombreSalon()
                    + " ya se encuentra asignado para el turno "
                    + salon.getTurno()
                    + " en el año "
                    + salon.getAnioLectivo());
        }

        // 2. No puede haber dos salones con el mismo grado, sección, turno y año
        //    (coincide con la restricción única de la tabla salon).
        boolean gradoRepetido = salonRepositorio.findAll().stream()
                .anyMatch(existente ->
                        esOtroSalon(existente, salon)
                                && existente.getGrado() != null
                                && Objects.equals(existente.getGrado().getIdGrado(), grado.getIdGrado())
                                && textoNormalizado(existente.getSeccion()).equals(seccionNueva)
                                && textoNormalizado(existente.getTurno()).equals(turnoNuevo)
                                && Objects.equals(existente.getAnioLectivo(), salon.getAnioLectivo()));

        if (gradoRepetido) {
            throw new RuntimeException("Ya existe un salón para el grado " + grado.getNombre()
                    + ", sección " + salon.getSeccion()
                    + ", turno " + salon.getTurno()
                    + " en el año " + salon.getAnioLectivo());
        }

        salonRepositorio.save(salon);
    }

    // true si "existente" es un registro distinto al que se está guardando.
    // En un salón nuevo (sin ID) cualquier registro existente es "otro".
    private boolean esOtroSalon(Salon existente, Salon actual) {
        return actual.getIdSalon() == null
                || !actual.getIdSalon().equals(existente.getIdSalon());
    }

    private String textoNormalizado(String texto) {
        return texto == null ? "" : texto.trim().toLowerCase();
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
    public List<Salon> obtenerSalonesDisponiblesPorGrado(
            Integer idGrado) {
        return salonRepositorio
                .obtenerSalonesDisponiblesPorGrado(idGrado);
    }
}