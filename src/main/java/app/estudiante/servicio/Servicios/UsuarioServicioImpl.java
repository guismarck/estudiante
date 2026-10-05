package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Rol;
import app.estudiante.modelo.Usuario;
import app.estudiante.repositorio.PermisoRolRepository;
import app.estudiante.repositorio.PersonaRepository;
import app.estudiante.repositorio.RolRepository;
import app.estudiante.repositorio.UsuarioRepositorio;
import app.estudiante.security.JwtTokenProvider;
import app.estudiante.servicio.InterfacesServicios.IUsuarioServicio;
import app.estudiante.utils.RecursoNoEncontradoException;
import app.estudiante.utils.UsuarioRequestDTO;
import app.estudiante.utils.UsuarioResponseDTO;
import app.estudiante.utils.seguridad.PasswordHasher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsuarioServicioImpl implements IUsuarioServicio {

    private final UsuarioRepositorio usuarioRepository;
    private final RolRepository rolRepository;
    private final PersonaRepository personaRepository;
    private final PermisoRolRepository permisoRolRepository;
    private final PasswordHasher passwordHasher;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;


    public UsuarioServicioImpl(
            UsuarioRepositorio usuarioRepository,
            RolRepository rolRepository,
            PersonaRepository personaRepository,
            PermisoRolRepository permisoRolRepository,
            PasswordHasher passwordHasher,
            AuthenticationManager authenticationManager,
            JwtTokenProvider tokenProvider) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.personaRepository = personaRepository;
        this.permisoRolRepository = permisoRolRepository;
        this.passwordHasher = passwordHasher;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Override
    @Transactional
    public UsuarioResponseDTO crear(UsuarioRequestDTO request) {

        if (usuarioRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("El username '" + request.username() + "' ya se encuentra en uso.");
        }

        Set<Rol> roles = new HashSet<>(rolRepository.findAllById(request.rolesIds()));
        if (roles.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontraron roles asignables para los IDs proporcionados.");
        }

        // CORREGIDO: Se aplica el hash directamente a la cadena plana de texto de la contraseña
        String hashedPassword = passwordHasher.hash(request.password());

        Usuario usuario = Usuario.builder()
                .idpersona(request.idPersona())
                .username(request.username())
                .password(hashedPassword)
                .creadoPor(request.creadoPor())
                .creadoEl(LocalDateTime.now())
                .estado(Integer.valueOf(request.estado()))
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

        Usuario usuario = usuarioRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));

        Set<Rol> roles = new HashSet<>(rolRepository.findAllById(request.rolesIds()));

        if (request.password() != null && !request.password().isBlank()) {
            String nuevoHash = passwordHasher.hash(request.password());
            usuario.setPassword(nuevoHash);
        }

        usuario.setIdpersona(request.idPersona());
        usuario.setUsername(request.username());
        usuario.setEstado(Integer.valueOf(request.estado()));
        usuario.setRoles(roles);

        return mapearAResponse(usuarioRepository.save(usuario));
    }


    private UsuarioResponseDTO mapearAResponse(Usuario usuario) {
        Set<String> codigosRoles = usuario.getRoles().stream()
                .map(Rol::getCodigo)
                .collect(Collectors.toSet());

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getIdpersona(),
                usuario.getUsername(),
                usuario.getEstado(),
                usuario.getCreadoEl(),
                codigosRoles
        );
    }
}
