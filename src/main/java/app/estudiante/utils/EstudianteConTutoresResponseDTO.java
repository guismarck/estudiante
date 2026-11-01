package app.estudiante.utils;

import app.estudiante.modelo.Estudiante;
import app.estudiante.modelo.EstudianteTutor;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public record EstudianteConTutoresResponseDTO(
        Integer idpersona,
        String nombre_completo,
        String apellido_completo,
        String sexo,
        String direccion,
        String partida_nacimiento,
        Date fecha_nacimiento,
        String cedula,
        String email,
        String codEstudiante,
        String codigoMined,
        Boolean estado,
        List<EstudianteTutorDTO> tutores
) {
    public static EstudianteConTutoresResponseDTO from(
            Estudiante estudiante,
            List<EstudianteTutor> tutores) {
        return new EstudianteConTutoresResponseDTO(
                estudiante.getIdpersona(),
                estudiante.getNombre_completo(),
                estudiante.getApellido_completo(),
                estudiante.getSexo(),
                estudiante.getDireccion(),
                estudiante.getPartida_nacimiento(),
                estudiante.getFecha_nacimiento(),
                estudiante.getCedula(),
                estudiante.getEmail(),
                estudiante.getCodEstudiante(),
                estudiante.getCodigoMined(),
                estudiante.getEstado(),
                tutores.stream()
                        .map(relacion -> new EstudianteTutorDTO(
                                relacion.getTutor().getIdpersona(),
                                relacion.getTutor(),
                                relacion.getParentesco(),
                                relacion.getEstado()))
                        .collect(Collectors.toList())
        );
    }
}
