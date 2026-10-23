package app.estudiante.utils;

import jakarta.validation.constraints.NotBlank;

public record DocenteAsignacionRequestDTO(
        Integer idDocente,
        Integer idPlanDeEstudio,
        Integer idAsignatura,
        Integer idSalon,
        String usuarioCreacion
) {
}
