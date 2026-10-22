package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Grado;
import app.estudiante.modelo.NivelEducativo;
import app.estudiante.repositorio.GradoRepositorio;
import app.estudiante.repositorio.NivelEducativoRepositorio;
import app.estudiante.servicio.InterfacesServicios.IGradoServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradoServicio implements IGradoServicio {

    @Autowired
    private GradoRepositorio gradoRepositorio;

    @Autowired
    private NivelEducativoRepositorio nivelEducativoRepositorio;

    @Override
    public List<Grado> ListarGrado() {
        return gradoRepositorio.findAll();
    }

    @Override
    public Grado buscarGradoPorId(Integer idGrado) {
        return gradoRepositorio.findById(idGrado).orElse(null);
    }

    @Override
    public void guardarGrado(Grado grado) {

        // Verificar que el grado tenga un nivel educativo seleccionado.
        if (grado.getNivelEducativo() == null ||
                grado.getNivelEducativo().getIdNivel() == null) {

            throw new IllegalArgumentException(
                    "Debe seleccionar un nivel educativo válido."
            );
        }

        // Obtener el ID del nivel educativo enviado desde React.
        Integer idNivel = grado.getNivelEducativo().getIdNivel();

        // Buscar el nivel que ya existe en la base de datos.
        NivelEducativo nivelExistente =
                nivelEducativoRepositorio.findById(idNivel)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "No existe el nivel educativo con ID: " + idNivel
                                )
                        );

        // Asociar la entidad persistente al grado.
        grado.setNivelEducativo(nivelExistente);

        // Guardar el grado con su relación correctamente establecida.
        gradoRepositorio.save(grado);
    }

    @Override
    public void eliminarGrado(Grado grado) {
        gradoRepositorio.delete(grado);
    }

    @Override
    public List<Grado> obtenerGradosPorNivel(Integer idNivel) {
        return gradoRepositorio.findByNivelEducativoIdNivel(idNivel);
    }
}