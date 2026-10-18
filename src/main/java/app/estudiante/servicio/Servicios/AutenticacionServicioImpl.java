package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.RolEntity;
import app.estudiante.modelo.Usuario;
import app.estudiante.repositorio.UsuarioRepository;
import app.estudiante.servicio.InterfacesServicios.IAutenticacionServicio;
import app.estudiante.utils.CredencialesInvalidasException;
import app.estudiante.utils.LoginRequestDTO;
import app.estudiante.utils.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AutenticacionServicioImpl implements IAutenticacionServicio {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public LoginResponseDTO verificarCredenciales(LoginRequestDTO request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CredencialesInvalidasException("Usuario o contraseña incorrectos"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash())) {
            throw new CredencialesInvalidasException("Usuario o contraseña incorrectos");
        }

        if (usuario.getEstado() == null || usuario.getEstado() != 1) {
            String motivo = switch (usuario.getEstado() != null ? usuario.getEstado() : 0) {
                case 0 -> "La cuenta se encuentra inactiva.";
                case 2 -> "La cuenta se encuentra bloqueada.";
                default -> "El usuario no tiene acceso al sistema.";
            };
            throw new CredencialesInvalidasException(motivo);
        }

        String nombreCompleto = usuario.getPersona() != null
                ? usuario.getPersona().getNombre_completo()
                : usuario.getUsername();

        List<String> rolesList = usuario.getRoles().stream()
                .map(RolEntity::getCodigo)
                .toList();

        return LoginResponseDTO.builder()
                .id(usuario.getPersona().getIdpersona())
                .username(usuario.getUsername())
                .nombreCompleto(nombreCompleto)
                .roles(rolesList)
                .build();
    }
}