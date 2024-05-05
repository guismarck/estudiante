package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Usuario;
import app.estudiante.repositorio.UsuarioRepositorio;
import app.estudiante.servicio.InterfacesServicios.IUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UsuarioServicio implements IUsuarioServicio {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public List<Usuario> ListarUsuario() {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        return usuarios;
    }

    @Override
    public Usuario buscarUsuarioPorId(Integer idusuario) {
       Usuario usuario = usuarioRepositorio.findById(idusuario).orElse(null);
        return  usuario;
    }

    @Override
    public void guardarUsuario(Usuario usuario) {
    usuarioRepositorio.save(usuario);
    }

    @Override
    public void eliminarUsuario(Usuario usuario) {
   usuarioRepositorio.delete(usuario);
    }
}
