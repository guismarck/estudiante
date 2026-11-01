package app.estudiante.servicio.Servicios;
import app.estudiante.exception.NegocioException;
import app.estudiante.exception.recursosNoEncontradoException;
import app.estudiante.modelo.Persona;
import app.estudiante.modelo.RolEntity;
import app.estudiante.modelo.Usuario;
import app.estudiante.modelo.Docente;
import app.estudiante.repositorio.DocenteRepositorio;
import app.estudiante.repositorio.PersonaRepositorio;
import app.estudiante.repositorio.UsuarioRepositorio;
import app.estudiante.repositorio.IRolRepositorio;
import app.estudiante.servicio.InterfacesServicios.IUsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import app.estudiante.utils.AsignarRolesDTO;
import app.estudiante.utils.RolResponseDTO;
import app.estudiante.utils.UsuarioActualizarRequestDTO;
import app.estudiante.utils.UsuarioCrearRequestDTO;
import app.estudiante.utils.UsuarioResponseDTO;

import java.util.List;
import java.util.Set;
import java.sql.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UsuarioServicio implements IUsuarioServicio {
    private final UsuarioRepositorio usuarioRepositorio;
    private final IRolRepositorio rolRepositorio;
    private final PersonaRepositorio personaRepositorio;
    private final DocenteRepositorio docenteRepositorio;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UsuarioResponseDTO crearUsuario(UsuarioCrearRequestDTO dto, String usuarioCreador) {
        String usernameLimpio = dto.username().trim();

        if (usuarioRepositorio.existsByUsername(usernameLimpio)) {
            throw new NegocioException("El nombre de usuario '" + usernameLimpio + "' ya se encuentra registrado.");
        }

        if (dto.rolesIds().size() != 1) {
            throw new NegocioException("Debe asignar exactamente un rol al usuario.");
        }

        Short rolId = dto.rolesIds().iterator().next();
        RolEntity rol = rolRepositorio.findById(rolId)
                .orElseThrow(() -> new recursosNoEncontradoException("Rol no encontrado con ID: " + rolId));
        Persona persona = resolverPersona(dto, rol.getCodigo());

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setPersona(persona);
        nuevoUsuario.setUsername(usernameLimpio);
        nuevoUsuario.setPasswordHash(passwordEncoder.encode(dto.password()));
        nuevoUsuario.setEstado(1);
        nuevoUsuario.setRoles(Set.of(rol));

        return mapearADto(usuarioRepositorio.save(nuevoUsuario));
    }

    @Override
    @Transactional
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioActualizarRequestDTO dto) {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new recursosNoEncontradoException("Usuario no encontrado con ID: " + id));
        String username = dto.username().trim();

        if (!username.equals(usuario.getUsername())
                && usuarioRepositorio.existsByUsernameAndIdNot(username, id)) {
            throw new NegocioException("El nombre de usuario '" + username + "' ya se encuentra registrado.");
        }
        if (dto.rolesIds().size() != 1) {
            throw new NegocioException("Debe asignar exactamente un rol al usuario.");
        }

        Short rolId = dto.rolesIds().iterator().next();
        RolEntity rol = rolRepositorio.findById(rolId)
                .orElseThrow(() -> new recursosNoEncontradoException("Rol no encontrado con ID: " + rolId));
        String codigoRol = rol.getCodigo().trim().toUpperCase();
        Persona persona = usuario.getPersona();

        if (persona == null) {
            throw new NegocioException("El usuario no tiene una persona asociada.");
        }
        if ("DOCENTE".equals(codigoRol)) {
            persona = docenteRepositorio.findById(persona.getIdpersona())
                    .map(docente -> (Persona) docente)
                    .orElseThrow(() -> new NegocioException(
                            "El rol DOCENTE requiere una persona registrada previamente como docente."));
        } else if ("SECRETARIA".equals(codigoRol) || "DIRECTOR".equals(codigoRol)) {
            actualizarDatosPersona(persona, dto);
        } else {
            throw new NegocioException("El rol debe ser DOCENTE, SECRETARIA o DIRECTOR.");
        }

        usuario.setPersona(persona);
        usuario.setUsername(username);
        if (dto.password() != null && !dto.password().isBlank()) {
            if (dto.password().length() < 8) {
                throw new NegocioException("La contraseña debe contener al menos 8 caracteres.");
            }
            usuario.setPasswordHash(passwordEncoder.encode(dto.password()));
        }
        if (dto.estado() != null) {
            if (!Set.of(0, 1, 2).contains(dto.estado())) {
                throw new NegocioException("El estado debe ser 0 (inactivo), 1 (activo) o 2 (bloqueado).");
            }
            usuario.setEstado(dto.estado());
        }
        usuario.getRoles().clear();
        usuario.getRoles().add(rol);

        return mapearADto(usuarioRepositorio.save(usuario));
    }

    private void actualizarDatosPersona(Persona persona, UsuarioActualizarRequestDTO dto) {
        if (dto.nombres() == null || dto.nombres().isBlank()
                || dto.apellidos() == null || dto.apellidos().isBlank()
                || dto.sexo() == null || dto.sexo().isBlank()
                || dto.fechaNacimiento() == null
                || dto.direccion() == null || dto.direccion().isBlank()
                || dto.email() == null || dto.email().isBlank()) {
            throw new NegocioException(
                    "Nombres, apellidos, sexo, fecha de nacimiento, dirección y correo son obligatorios.");
        }
        persona.setNombre_completo(dto.nombres().trim());
        persona.setApellido_completo(dto.apellidos().trim());
        persona.setSexo(dto.sexo().trim());
        persona.setFecha_nacimiento(Date.valueOf(dto.fechaNacimiento()));
        persona.setDireccion(dto.direccion().trim());
        persona.setEmail(dto.email().trim());
        personaRepositorio.save(persona);
    }

    private Persona resolverPersona(UsuarioCrearRequestDTO dto, String codigoRol) {
        String rol = codigoRol.trim().toUpperCase();
        if ("DOCENTE".equals(rol)) {
            if (dto.idPersona() == null) {
                throw new NegocioException("Para crear un usuario DOCENTE debe indicar un docente previamente registrado.");
            }
            return docenteRepositorio.findById(dto.idPersona())
                    .map(docente -> (Persona) docente)
                    .orElseThrow(() -> new recursosNoEncontradoException(
                            "No existe un docente registrado con ID de persona: " + dto.idPersona()));
        }

        if (!"SECRETARIA".equals(rol) && !"DIRECTOR".equals(rol)) {
            throw new NegocioException("El rol debe ser DOCENTE, SECRETARIA o DIRECTOR.");
        }
        if (dto.email() == null || dto.email().isBlank()) {
            throw new NegocioException("El correo electrónico es obligatorio para SECRETARIA o DIRECTOR.");
        }
        String email = dto.email().trim();

        Persona personaPorId = dto.idPersona() == null
                ? null
                : personaRepositorio.findById(dto.idPersona()).orElseThrow(() ->
                        new recursosNoEncontradoException("No existe la persona con ID: " + dto.idPersona()));

        String cedula = normalizar(dto.cedula());
        Persona personaPorCedula = cedula == null
                ? null
                : personaRepositorio.findByCedula(cedula).orElse(null);

        if (personaPorId != null && personaPorCedula != null
                && !personaPorId.getIdpersona().equals(personaPorCedula.getIdpersona())) {
            throw new NegocioException("El ID y la cédula corresponden a personas diferentes.");
        }
        if (personaPorId != null && cedula != null
                && !cedula.equals(normalizar(personaPorId.getCedula()))) {
            throw new NegocioException("La cédula no corresponde a la persona indicada.");
        }

        Persona personaExistente = personaPorId != null ? personaPorId : personaPorCedula;
        if (personaExistente != null) {
            personaExistente.setEmail(email);
            return personaRepositorio.save(personaExistente);
        }

        validarDatosPersona(dto);
        Persona nuevaPersona = new Persona();
        nuevaPersona.setNombre_completo(dto.nombres().trim());
        nuevaPersona.setApellido_completo(dto.apellidos().trim());
        nuevaPersona.setSexo(dto.sexo().trim());
        nuevaPersona.setFecha_nacimiento(Date.valueOf(dto.fechaNacimiento()));
        nuevaPersona.setDireccion(dto.direccion().trim());
        nuevaPersona.setCedula(cedula);
        nuevaPersona.setEmail(email);
        return personaRepositorio.save(nuevaPersona);
    }

    private void validarDatosPersona(UsuarioCrearRequestDTO dto) {
        if (dto.nombres() == null || dto.nombres().isBlank()
                || dto.apellidos() == null || dto.apellidos().isBlank()
                || dto.sexo() == null || dto.sexo().isBlank()
                || dto.fechaNacimiento() == null
                || dto.direccion() == null || dto.direccion().isBlank()) {
            throw new NegocioException(
                    "Para crear una persona debe enviar nombres, apellidos, sexo, fecha de nacimiento y dirección.");
        }
    }

    private String normalizar(String valor) {
        return valor == null || valor.isBlank() ? null : valor.trim();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new recursosNoEncontradoException("Usuario no encontrado con ID: " + id));
        return mapearADto(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> obtenerTodos() {
        return usuarioRepositorio.findAll().stream()
                .map(this::mapearADto)
                .toList();
    }

    @Override
    @Transactional
    public UsuarioResponseDTO asignarRoles(AsignarRolesDTO dto, String usuarioCreador) {
        Usuario usuario = usuarioRepositorio.findById(dto.usuarioId())
                .orElseThrow(() -> new recursosNoEncontradoException("Usuario no encontrado con ID: " + dto.usuarioId()));

        Set<RolEntity> nuevosRoles = dto.rolesIds().stream().map(rolId -> {
            RolEntity rol = rolRepositorio.findById(rolId)
                    .orElseThrow(() -> new recursosNoEncontradoException("Rol no encontrado con ID: " + rolId));
            return rol;
        }).collect(Collectors.toSet());

        usuario.getRoles().clear();
        usuario.getRoles().addAll(nuevosRoles);

        return mapearADto(usuarioRepositorio.save(usuario));
    }

    @Override
    @Transactional
    public void cambiarEstado(Long id, Integer nuevoEstado) {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new recursosNoEncontradoException("Usuario no encontrado con ID: " + id));
        usuario.setEstado(nuevoEstado);
        usuarioRepositorio.save(usuario);
    }

    private UsuarioResponseDTO mapearADto(Usuario entidad) {
        Persona persona = entidad.getPersona();
        String nombreCompleto = persona == null
                ? null
                : Stream.of(persona.getNombre_completo(), persona.getApellido_completo())
                        .filter(nombre -> nombre != null && !nombre.isBlank())
                        .collect(Collectors.joining(" "));

        Set<RolResponseDTO> roles = entidad.getRoles().stream()
                .map(rol -> new RolResponseDTO(
                        rol.getId(),
                        rol.getCodigo(),
                        rol.getDescripcion()
                ))
                .collect(Collectors.toSet());

        return new UsuarioResponseDTO(
                entidad.getId(),
                persona == null ? null : persona.getIdpersona(),
                entidad.getUsername(),
                persona == null ? null : persona.getNombre_completo(),
                persona == null ? null : persona.getApellido_completo(),
                persona == null ? null : persona.getSexo(),
                persona == null || persona.getFecha_nacimiento() == null
                        ? null
                        : persona.getFecha_nacimiento().toLocalDate(),
                persona == null ? null : persona.getDireccion(),
                persona == null ? null : persona.getCedula(),
                nombreCompleto,
                persona == null ? null : persona.getEmail(),
                entidad.getEstado(),
                Integer.valueOf(1).equals(entidad.getEstado()),
                roles
        );
    }
}
