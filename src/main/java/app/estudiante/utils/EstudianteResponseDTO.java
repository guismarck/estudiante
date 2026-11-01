package app.estudiante.utils;

import app.estudiante.modelo.Estudiante;

import java.sql.Date;

public record EstudianteResponseDTO(
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
        Boolean estado
) {
    public static EstudianteResponseDTO from(Estudiante estudiante) {
        return new EstudianteResponseDTO(
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
                estudiante.getEstado()
        );
    }
}
