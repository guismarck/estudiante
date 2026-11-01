package app.estudiante.utils;

import java.time.LocalDate;
import java.util.Set;

public record UsuarioResponseDTO(
    Long id,
    Integer idPersona,
    String username,
    String nombres,
    String apellidos,
    String sexo,
    LocalDate fechaNacimiento,
    String direccion,
    String cedula,
    String nombreCompleto,
    String email,
    Integer estado,
    boolean activo,
    Set<RolResponseDTO> roles
) {}