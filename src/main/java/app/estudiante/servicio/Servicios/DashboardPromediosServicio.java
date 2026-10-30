package app.estudiante.servicio.Servicios;

import app.estudiante.repositorio.DashboardPromediosRepositorio;
import app.estudiante.servicio.InterfacesServicios.IMejorPromedioProjection;
import app.estudiante.utils.MejorPromedioDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;

@Service
public class DashboardPromediosServicio {

    private final DashboardPromediosRepositorio repositorio;

    public DashboardPromediosServicio(
            DashboardPromediosRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Transactional(readOnly = true)
    public List<MejorPromedioDTO> obtenerMejoresPromedios(
            String nivel) {

        Integer idNivel = obtenerIdNivel(nivel);

        Integer anio = Year.now().getValue();

        List<IMejorPromedioProjection> resultados =
                repositorio.obtenerTopCincoPromedios(
                        idNivel,
                        anio
                );

        return resultados.stream()
                .map(item -> new MejorPromedioDTO(
                        item.getNombreCompleto(),
                        item.getPromedio()
                ))
                .toList();
    }

    private Integer obtenerIdNivel(String nivel) {

        if (nivel == null) {
            throw new IllegalArgumentException(
                    "Debe especificar el nivel educativo."
            );
        }

        return switch (nivel.trim().toUpperCase()) {
            case "PRIMARIA" -> 1;
            case "SECUNDARIA" -> 2;

            default -> throw new IllegalArgumentException(
                    "Nivel educativo no válido: " + nivel
            );
        };
    }
}
