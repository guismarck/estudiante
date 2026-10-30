package app.estudiante.utils;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeriodoEvaluativoRequestDTO {

    @NotBlank(message = "El nombre del período no puede estar vacío")
    @Size(max = 30, message = "El nombre del período no debe exceder los 30 caracteres")
    private String nombrePeriodo;

    @NotNull(message = "El número de período es obligatorio")
    @Min(value = 1, message = "El número de período debe ser mayor o igual a 1")
    private Integer numeroPeriodo;

    @NotNull(message = "El año escolar es obligatorio")
    private Integer anioEscolar;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;

    @NotNull(message = "La fecha de cierre docente es obligatoria")
    private LocalDateTime fechaCierreDocente;

    @NotNull(message = "El estado del período es obligatorio")
    private Boolean estado;

    @Size(max = 50, message = "El usuario de auditoría no debe exceder los 50 caracteres")
    private String usuarioAuditoria;
}