package app.estudiante.utils;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class MatriculaRequestDTO {

    private Long idPersona;

    private Long idPersonaEstudiante;

    private String nombresEstudiante;

    private String apellidosEstudiante;

    private String codigoMined;

    private Long idPersonaTutor;

    private String nombreCompletoTutor;

    private String cedulaTutor;

    private String telefonoTutor;

    private String correoTutor;

    private String parentesco;

    private Integer representanteLegalFinanciero;

    private Long idSalon;

    private String anioLectivo;

    private String tipoIngreso;

    private BigDecimal costoMatricula;

    private String estado;

    private List<Integer> requisitosPresentados;

    private LocalDate fechaInscripcion;

    private String usuario;
}