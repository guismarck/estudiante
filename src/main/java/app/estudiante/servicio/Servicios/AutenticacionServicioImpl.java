package app.estudiante.servicio.Servicios;

import app.estudiante.exception.recursosNoEncontradoException;
import app.estudiante.modelo.Docente;
import app.estudiante.modelo.RolEntity;
import app.estudiante.modelo.Usuario;
import app.estudiante.repositorio.DocenteRepositorio;
import app.estudiante.repositorio.UsuarioRepository;
import app.estudiante.servicio.InterfacesServicios.IAutenticacionServicio;
import app.estudiante.servicio.InterfacesServicios.INotificacionEmailServicio;
import app.estudiante.servicio.InterfacesServicios.ITokenStatelessServicio;
import app.estudiante.utils.CredencialesInvalidasException;
import app.estudiante.utils.LoginRequestDTO;
import app.estudiante.utils.LoginResponseDTO;
import app.estudiante.utils.RespuestaOTPDTO;
import app.estudiante.utils.SolicitudOTPDTO;
import app.estudiante.utils.VerificarOTPDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class AutenticacionServicioImpl implements IAutenticacionServicio {

    private final UsuarioRepository usuarioRepository;
    private final DocenteRepositorio docenteRepositorio;
    private final PasswordEncoder passwordEncoder;
    private final INotificacionEmailServicio emailServicio;
    private final ITokenStatelessServicio tokenStatelessServicio;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

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

    @Override
    @Transactional(readOnly = true)
    public RespuestaOTPDTO solicitarCodigoOTP(SolicitudOTPDTO solicitud) {
        String identificacion = solicitud.identificacion().trim();
        String username = solicitud.username().trim();
        Docente docente = docenteRepositorio.buscarParaRecuperacion(identificacion, username)
                .orElseThrow(() -> new recursosNoEncontradoException(
                        "No se encontró un docente activo con la información proporcionada."));

        String email = docente.getEmail();
        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(
                    BAD_REQUEST,
                    "El docente no cuenta con un correo electrónico registrado en el expediente.");
        }

        String otpCode = String.format("%06d", SECURE_RANDOM.nextInt(900000) + 100000);
        long expiracionEpochMilli = System.currentTimeMillis() + (5 * 60 * 1000);

        String tokenRecuperacion = tokenStatelessServicio.generarTokenRecuperacion(
                docente.getIdpersona().longValue(),
                otpCode,
                expiracionEpochMilli
        );

        emailServicio.enviarOTPDocente(email, nombreCompleto(docente), otpCode);

        String emailOculto = enmascararEmail(email);

        return new RespuestaOTPDTO(
                "Código de seguridad enviado exitosamente a " + emailOculto,
                docente.getIdpersona().longValue(),
                tokenRecuperacion,
                emailOculto
        );
    }

    @Override
    @Transactional
    public void verificarOTPYCambiarPassword(VerificarOTPDTO verificarDto) {
        boolean esValido = tokenStatelessServicio.validarTokenYOTP(
                verificarDto.tokenRecuperacion(),
                verificarDto.docenteId(),
                verificarDto.otpCode()
        );

        if (!esValido) {
            throw new ResponseStatusException(
                    BAD_REQUEST,
                    "El código de seguridad o el token ingresado es incorrecto.");
        }

        Integer idpersona = Math.toIntExact(verificarDto.docenteId());
        Docente docente = docenteRepositorio.findById(idpersona)
                .filter(d -> Boolean.TRUE.equals(d.getEstado()))
                .orElseThrow(() -> new recursosNoEncontradoException(
                        "La solicitud no es válida o el docente se encuentra inactivo."));
        Usuario usuario = usuarioRepository.findByPersona_Idpersona(idpersona)
                .filter(u -> Integer.valueOf(1).equals(u.getEstado()))
                .orElseThrow(() -> new recursosNoEncontradoException(
                        "La solicitud no es válida o la cuenta se encuentra inactiva."));

        if (!usuario.getPersona().getIdpersona().equals(docente.getIdpersona())) {
            throw new recursosNoEncontradoException("La solicitud de recuperación no es válida.");
        }

        usuario.setPasswordHash(passwordEncoder.encode(verificarDto.nuevaPassword()));
        usuarioRepository.save(usuario);
    }

    private String nombreCompleto(Docente docente) {
        String nombre = docente.getNombre_completo() == null ? "" : docente.getNombre_completo().trim();
        String apellido = docente.getApellido_completo() == null ? "" : docente.getApellido_completo().trim();
        return (nombre + " " + apellido).trim();
    }

    private String enmascararEmail(String email) {
        if (email == null || !email.contains("@")) return email;
        int atIndex = email.indexOf("@");
        if (atIndex <= 2) {
            return email.charAt(0) + "***" + email.substring(atIndex);
        }
        String localPart = email.substring(0, atIndex);
        String domain = email.substring(atIndex);
        return localPart.substring(0, 2) + "*".repeat(localPart.length() - 2) + domain;
    }
}