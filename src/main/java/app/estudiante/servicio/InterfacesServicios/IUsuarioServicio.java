package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.Usuario;

import java.util.List;

public interface IUsuarioServicio {
    public List<Usuario> ListarUsuario();
    public Usuario buscarUsuarioPorId(Integer idusuario);
    public void guardarUsuario (Usuario usuario);
    public  void eliminarUsuario(Usuario usuario);

}
