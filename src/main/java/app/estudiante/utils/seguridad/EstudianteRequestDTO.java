package app.estudiante.utils.seguridad;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstudianteRequestDTO {

    @NotBlank(message = "El nombre completo es requerido")
    private String nombreCompleto;

    private String codigoMined;

    @NotBlank(message = "La partida de nacimiento es requerida")
    private String partidaNacimiento;

    private Long tutorId;
}