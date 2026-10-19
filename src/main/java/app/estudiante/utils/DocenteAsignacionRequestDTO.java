package app.estudiante.utils;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DocenteAsignacionRequestDTO(
        @NotNull Integer idDocente,
        @NotNull Integer idPlanDeEstudio,
        @NotNull Integer idAsignatura,
        @NotNull Integer idSalon,
        @NotBlank String usuarioCreacion
) {
}
