package app.estudiante.utils;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MatriculaRequestDTO {

    private Long idPersona;

    private Long idSalon;

    private String anioLectivo;

    private LocalDate fechaInscripcion;

    private String usuario;
}