package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Usuario;
import app.estudiante.repositorio.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario o código MINED no encontrado: " + username));

        // Retorna la clase concreta org.springframework.security.core.userdetails.User que implementa UserDetails
        return new User(
                usuario.getUsername(),
                usuario.getPasswordHash(),
                usuario.getEstado() == 1, // enabled
                true,                     // accountNonExpired
                true,                     // credentialsNonExpired
                usuario.getEstado() != 2, // accountNonLocked
                usuario.getRoles().stream()
                        .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getDescripcion()))
                        .toList()
        );
    }
}
