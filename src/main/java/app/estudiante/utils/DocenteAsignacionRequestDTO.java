package app.estudiante.utils;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DocenteAsignacionRequestDTO(
        @NotNull Integer idDocente,
        @NotNull Integer idPlanDeEstudio,
        @NotNull Integer idAsignatura,
        @NotNull Integer idSalon,
        @NotBlank @Size(max = 50) String usuarioCreacion
) {
}
