package app.estudiante.servicio.Servicios;

import app.estudiante.modelo.Usuario;
import app.estudiante.repositorio.UsuarioRepositorio;
import app.estudiante.servicio.InterfacesServicios.IDocenteServicio;
import app.estudiante.servicio.InterfacesServicios.IUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServicio implements IUsuarioServicio {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
   private IDocenteServicio docenteServicio;
    @Override
    public List<Usuario> ListarUsuario() {
        List<Usuario> usuarios = usuarioRepositorio.findAll().stream().filter(f-> f.isEstado()).toList();

        return usuarios;
    }

    @Override
    public Usuario buscarUsuarioPorId(Integer idusuario) {
       Usuario usuario = usuarioRepositorio.findById(idusuario).orElse(null);
        return  usuario;
    }

    @Transactional(rollbackFor =Exception.class )//regresa un punto de inicio mantine el mismo inicio
    @Override
    public void guardarUsuario(Usuario usuario ) {
        docenteServicio.guardarDocente(usuario.getDocente());
      usuarioRepositorio.save(usuario);
    }

    @Override
    public void eliminarUsuario(Usuario usuario) {
        usuario.setEstado(false);
        usuario.getDocente().setEstado(false);
        docenteServicio.guardarDocente(usuario.getDocente());
        usuarioRepositorio.save(usuario);

       //usuarioRepositorio.delete(usuario);
    }
}
