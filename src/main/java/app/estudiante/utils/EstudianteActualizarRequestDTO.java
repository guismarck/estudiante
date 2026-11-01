package app.estudiante.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EstudianteActualizarRequestDTO(
        String nombre_completo,
        String apellido_completo,
        String sexo,
        String direccion,
        String partida_nacimiento,
        Date fecha_nacimiento,
        String cedula,
        String codEstudiante,
        String codigoMined,
        Boolean estado
) {
}
