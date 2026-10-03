package app.estudiante.utils;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class MatriculaResponseDTO {
    private Long idMatricula;
    private Long estudianteId;
    private String nombreSalon;
    private String anioLectivo;
    private String estadoMatricula;
    private LocalDateTime fechaRegistro;
}
