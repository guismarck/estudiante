package app.estudiante.utils;

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
public class PeriodoEvaluativoResponseDTO {

private Integer idPeriodoEvaluativo;
    private String nombrePeriodo;
    private Integer numeroPeriodo;
    private Integer anioEscolar;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDateTime fechaCierreDocente;
    private Boolean estado;
    private String creadoPor;
    private LocalDateTime creadoEl;
    private String actualizadoPor;
    private LocalDateTime actualizadoEl;
}