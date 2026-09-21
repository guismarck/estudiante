package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.CatalogoTarifa;
import app.estudiante.modelo.Grado;
import app.estudiante.modelo.NivelEducativo;
import app.estudiante.repositorio.CatalogoTarifaRepositorio;
import app.estudiante.repositorio.GradoRepositorio;
import app.estudiante.repositorio.NivelEducativoRepositorio;
import app.estudiante.servicio.InterfacesServicios.ICatalogoTarifaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogoTarifaServicioImpl implements ICatalogoTarifaServicio {

    private final CatalogoTarifaRepositorio tarifaRepository;
    private final GradoRepositorio gradoRepository;
    private final NivelEducativoRepositorio nivelEducativoRepository;

    @Override
    public List<CatalogoTarifa> obtenerTodas() {
        return tarifaRepository.findAll();
    }

    @Override
    public List<CatalogoTarifa> obtenerPorAnio(Integer anioLectivo) {
        return tarifaRepository.findByAnioLectivo(anioLectivo);
    }

    @Override
    public CatalogoTarifa obtenerPorId(Integer id) {
        return tarifaRepository.findById(id).orElse(null);
    }

    @Override
    public CatalogoTarifa crear(CatalogoTarifa tarifa) {
        // Verificar existencia del Grado antes de guardar
        Integer idNivel = tarifa.getIdnivel().getIdnivel();
        NivelEducativo nivel = nivelEducativoRepository.findById(idNivel).orElse(null);
        
        tarifa.setIdnivel(nivel);
        return tarifaRepository.save(tarifa);
    }

    @Override
    public CatalogoTarifa actualizar(Integer id, CatalogoTarifa tarifaActualizada) {
        CatalogoTarifa tarifaExistente = obtenerPorId(id);

        Integer idGrado = tarifaActualizada.getIdnivel().getIdnivel();
        NivelEducativo nivel = nivelEducativoRepository.findById(idGrado).orElse(null);

        tarifaExistente.setIdnivel(nivel);
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
    public CatalogoTarifa buscarPorGradoAnioYConcepto(Integer idNivel, Integer anioLectivo, String concepto) {
        CatalogoTarifa tarifa = tarifaRepository.buscarPorGradoAnioYConcepto(idNivel, anioLectivo, concepto.trim()).orElse(null);
    // Manejar el caso cuando no se encuentra la tarifa
                        //String.format("No se encontró tarifa para el Grado ID %d, Año Lectivo %s y Concepto %s", idGrado, anioLectivo, concepto)));
        return tarifa;
    }

    @Override
    public byte[] generarReporteTarifasPdf(Integer anioLectivo) {
        // Simulación de exportación a PDF vía Jaspersoft Engine
        return new byte[0]; 
    }
}