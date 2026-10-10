package app.estudiante.servicio.Servicios;


import app.estudiante.modelo.Estudiante;
import app.estudiante.repositorio.EstudianteRepositorio;
import app.estudiante.servicio.InterfacesServicios.IEstudianteServicio;
import app.estudiante.utils.NumerUtils;
import app.estudiante.utils.ValidacionCedula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class EstudianteServicio implements IEstudianteServicio {

 @Autowired
 private EstudianteRepositorio estudianteRepositorio;
    @Override
    public List<Estudiante> ListarEstudiantes() {
        List<Estudiante> estudiantes = estudianteRepositorio.findAll();
         return estudiantes;
    }

    @Override
    public Estudiante buscarEstudinatePorId(Integer idEstudiante) {
        Estudiante estudiante = estudianteRepositorio.findById(idEstudiante).orElse(null);
        return estudiante;
    }

    public List<Estudiante> busquedaGeneral(String search){
        var isInt = NumerUtils.isInt(search);
        if (isInt) return List.of(buscarEstudinatePorId(Integer.parseInt(search)));
        return estudianteRepositorio.busquedaGeneral(search);
    }

    @Override
    public void guardarEstudiante(Estudiante estudiante) {

        /*if (estudiante.getCedula() != null && !estudiante.getCedula().trim().isEmpty()) {
            LocalDate fechaCalculada = ValidacionCedula.extraerFechaNacimiento(estudiante.getCedula());
            if (fechaCalculada != null) {
                // Sobrescribir/asegurar la fecha derivada de la cédula
                estudiante.setFecha_nacimiento(Date.valueOf(fechaCalculada));
            }
        }

        if (estudiante.getFecha_nacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no es válida ni pudo ser derivada de la Cédula.");
        }

        Estudiante entidad = new Estudiante();

        entidad.setNombre_completo(estudiante.getNombre_completo());
        entidad.setApellido_completo(estudiante.getApellido_completo());
        entidad.setFecha_nacimiento(estudiante.getFecha_nacimiento());
        entidad.setCedula(estudiante.getCedula());
        entidad.setSexo(estudiante.getSexo());
        entidad.setDireccion(estudiante.getDireccion());
        entidad.setPartida_nacimiento(estudiante.getPartida_nacimiento());
        entidad.setCodEstudiante(estudiante.getCodEstudiante());
        entidad.setCodigoMined(estudiante.getCodigoMined());
        entidad.setEstado(estudiante.getEstado());

        Estudiante guardado = estudianteRepositorio.save(entidad);//agregar o modificar
        estudiante.setIdpersona(guardado.getIdpersona());*/
        estudianteRepositorio.save(estudiante);
    }

    @Override
    public void eliminarEstudinate(Estudiante estudiante) {
      estudianteRepositorio.delete(estudiante);
    }
}
