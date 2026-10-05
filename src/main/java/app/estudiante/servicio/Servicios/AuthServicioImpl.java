package app.estudiante.servicio.Servicios;


import app.estudiante.modelo.Usuario;
import app.estudiante.repositorio.PermisoRolRepository;
import app.estudiante.repositorio.UsuarioRepositorio;
import app.estudiante.servicio.InterfacesServicios.IAuthServicio;
import app.estudiante.utils.seguridad.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServicioImpl implements IAuthServicio {

    private final AuthenticationManager authenticationManager;
    private final JwtService tokenProvider;
    private final UsuarioRepositorio usuarioRepository;
    private final PermisoRolRepository permisoRolRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthServicioImpl(
            AuthenticationManager authenticationManager,
            JwtService tokenProvider,
            UsuarioRepositorio usuarioRepository,
            PermisoRolRepository permisoRolRepository,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.usuarioRepository = usuarioRepository;
        this.permisoRolRepository = permisoRolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponse autenticar(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsernameWithRoles(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Usuario o contraseña incorrectos"));

        if (!usuario.isActivo()) {
            throw new DisabledException("El usuario se encuentra inactivo");
        }

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new BadCredentialsException("Usuario o contraseña incorrectos");
        }

        List<String> roles = (usuario.getRoles() != null)
                ? usuario.getRoles().stream()
                .map(rol -> rol.getCodigo() != null ? rol.getCodigo() : "USER")
                .collect(Collectors.toList())
                : Collections.emptyList();

        String token = tokenProvider.generarToken(usuario);

        return LoginResponse.builder()
                .token(token)
                .username(usuario.getUsername())
                .id(usuario.getId())
                .roles(roles)
                .build();
     }

    }
