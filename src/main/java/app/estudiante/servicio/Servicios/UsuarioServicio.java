package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Usuario;
import app.estudiante.repositorio.UsuarioRepositorio;
import app.estudiante.servicio.InterfacesServicios.IUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServicio implements IUsuarioServicio {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Override
    public List<Usuario> ListarUsuario() {
        return usuarioRepositorio.findAll().stream()
                .filter(usuario -> Integer.valueOf(1).equals(usuario.getEstado()))
                .toList();
    }

    @Override
    public Usuario buscarUsuarioPorId(Long idusuario) {
       Usuario usuario = usuarioRepositorio.findById(idusuario).orElse(null);
        return  usuario;
    }

    @Transactional(rollbackFor =Exception.class )//regresa un punto de inicio mantine el mismo inicio
    @Override
    public void guardarUsuario(Usuario usuario ) {
        usuarioRepositorio.save(usuario);
    }

    @Override
    public void eliminarUsuario(Usuario usuario) {
        usuario.setEstado(0);
        usuarioRepositorio.save(usuario);

       //usuarioRepositorio.delete(usuario);
    }
}
