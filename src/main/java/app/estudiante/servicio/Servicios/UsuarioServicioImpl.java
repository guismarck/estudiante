package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Persona;
import app.estudiante.modelo.Rol;
import app.estudiante.modelo.Usuario;
import app.estudiante.repositorio.PersonaRepository;
import app.estudiante.repositorio.RolRepository;
import app.estudiante.repositorio.UsuarioRepositorio;
import app.estudiante.servicio.InterfacesServicios.IUsuarioServicio;
import app.estudiante.utils.RecursoNoEncontradoException;
import app.estudiante.utils.UsuarioRequestDTO;
import app.estudiante.utils.UsuarioResponseDTO;
import app.estudiante.utils.seguridad.AuthRequestDTO;
import app.estudiante.utils.seguridad.AuthResponseDTO;
import app.estudiante.utils.seguridad.PasswordHasher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServicioImpl implements IUsuarioServicio {
    private final UsuarioRepositorio usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordHasher passwordHasher;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PersonaRepository  personaRepository;

    @Override
    @Transactional
    public UsuarioResponseDTO crear(UsuarioRequestDTO request) {
        Optional<Persona> persona = personaRepository.findById(request.idPersona());
        if (usuarioRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("El username '" + request.username() + "' ya se encuentra en uso.");
        }

        Set<Rol> roles = new HashSet<>(rolRepository.findAllById(request.rolesIds()));
        if (roles.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontraron roles asignables para los IDs proporcionados.");
        }

        // Generar hash seguro utilizando PasswordHasher (char[])
        String hashedPassword = passwordHasher.hash(request.password().toCharArray());

        Usuario usuario = Usuario.builder()
                .idPersona(persona.get())
                .username(request.username())
                .passwordHash(hashedPassword)
                .creadoPor(request.creadoPor())
                .creadoEl(LocalDateTime.now())
                .estado(request.estado())
                .roles(roles)
                .build();

        Usuario guardado = usuarioRepository.save(usuario);
        return mapearAResponse(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO obtenerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findByIdConRoles(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));
        return mapearAResponse(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::mapearAResponse)
                .toList();
    }

    @Override
    @Transactional
    public UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO request) {

        Optional<Persona> persona = personaRepository.findById(request.idPersona());
        Usuario usuario = usuarioRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));

        Set<Rol> roles = new HashSet<>(rolRepository.findAllById(request.rolesIds()));

        // Actualizar contraseña si viene una nueva y re-hashizar
        if (request.password() != null && !request.password().isBlank()) {
            String nuevoHash = passwordHasher.hash(request.password().toCharArray());
            usuario.setPasswordHash(nuevoHash);
        }

        usuario.setIdPersona(persona.get());
        usuario.setUsername(request.username());
        usuario.setEstado(request.estado());
        usuario.setRoles(roles);

        return mapearAResponse(usuarioRepository.save(usuario));
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponseDTO autenticar(AuthRequestDTO request) {
        // 1. Delegar la autenticación de credenciales (BCrypt + Usuario)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // 2. Obtener el UserDetails nativo de Spring Security
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 3. Buscar la entidad Usuario en la BD
        Usuario usuario = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Usuario no encontrado con username: " + userDetails.getUsername()
                ));

        // 4. Generar Token JWT
        String jwtToken = jwtService.generarToken(usuario);

        // 5. Extraer roles de forma segura
        String rolesString = usuario.getRoles() == null ? "" : usuario.getRoles().stream()
                .map(Rol::getCodigo)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(","));

        // 6. Retornar DTO de respuesta
        return AuthResponseDTO.builder()
                .token(jwtToken)
                .bearer("Bearer")
                .username(usuario.getUsername())
                .rol(rolesString)
                .build();
    }


        private UsuarioResponseDTO mapearAResponse(Usuario usuario) {

        Set<String> codigosRoles = usuario.getRoles().stream()
                .map(Rol::getCodigo)
                .collect(Collectors.toSet());

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getIdPersona().getIdPersona(),
                usuario.getUsername(),
                usuario.getEstado(),
                usuario.getCreadoEl(),
                codigosRoles
        );
    }
}
