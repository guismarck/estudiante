package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.CatalogoTarifa;
import app.estudiante.modelo.Grado;
import app.estudiante.repositorio.CatalogoTarifaRepositorio;
import app.estudiante.repositorio.GradoRepositorio;
import app.estudiante.servicio.InterfacesServicios.ICatalogoTarifaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogoTarifaServicioImpl implements ICatalogoTarifaServicio {

    private final CatalogoTarifaRepositorio tarifaRepository;
    private final GradoRepositorio gradoRepository;

    @Override
    public List<CatalogoTarifa> obtenerTodas() {
        return tarifaRepository.findAll();
    }

    @Override
    public List<CatalogoTarifa> obtenerPorAnio(Year anioLectivo) {
        return tarifaRepository.findByAnioLectivo(anioLectivo);
    }

    @Override
    public CatalogoTarifa obtenerPorId(Integer id) {
        return tarifaRepository.findById(id).orElse(null);
    }

    @Override
    public CatalogoTarifa crear(CatalogoTarifa tarifa) {
        // Verificar existencia del Grado antes de guardar
        Integer idGrado = tarifa.getGrado().getIdGrado();
        Grado grado = gradoRepository.findById(idGrado).orElse(null);
        
        tarifa.setGrado(grado);
        return tarifaRepository.save(tarifa);
    }

    @Override
    public CatalogoTarifa actualizar(Integer id, CatalogoTarifa tarifaActualizada) {
        CatalogoTarifa tarifaExistente = obtenerPorId(id);

        Integer idGrado = tarifaActualizada.getGrado().getIdGrado();
        Grado grado = gradoRepository.findById(idGrado).orElse(null);

        tarifaExistente.setGrado(grado);
        tarifaExistente.setAnioLectivo(tarifaActualizada.getAnioLectivo());
        tarifaExistente.setConcepto(tarifaActualizada.getConcepto());
        tarifaExistente.setMonto(tarifaActualizada.getMonto());

        return tarifaRepository.save(tarifaExistente);
    }

    @Override
    public void eliminar(Integer id) {
        tarifaRepository.deleteById(id);
    }

    @Override
    public CatalogoTarifa obtenerTarifaPorGradoYConcepto(Integer idNivel, Year anioLectivo, String concepto) {
        CatalogoTarifa tarifa = tarifaRepository.findByGradoIdGradoAndAnioLectivoAndConcepto(idNivel, anioLectivo, concepto)
                .orElse(null); // Manejar el caso cuando no se encuentra la tarifa
                        //String.format("No se encontró tarifa para el Grado ID %d, Año Lectivo %s y Concepto %s", idGrado, anioLectivo, concepto)));
        return tarifa;
    }

    @Override
    public byte[] generarReporteTarifasPdf(Year anioLectivo) {
        // Simulación de exportación a PDF vía Jaspersoft Engine
        return new byte[0]; 
    }
}