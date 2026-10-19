package app.estudiante.utils;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SalonRequestDTO(
        @NotNull Integer idGrado,
        @NotNull Long idCatalogoSalon,
        @NotNull Short anioLectivo,
        @NotBlank @Size(max = 20) String turno,
        @Size(max = 5) String seccion
) {
}
