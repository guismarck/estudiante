package app.estudiante.api;

import app.estudiante.exception.recurosNoEncontradoException;
import app.estudiante.modelo.Usuario;
import app.estudiante.servicio.InterfacesServicios.IDocenteServicio;
import app.estudiante.servicio.InterfacesServicios.IUsuarioServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("estudiante-app")
@CrossOrigin(value = "http://localhost:3000")
public class UsuarioControler {

    private static final
    Logger logger = LoggerFactory.getLogger(UsuarioControler.class);//Infomacion de la consol

    @Autowired
    private IUsuarioServicio usuarioServicio;
    private IDocenteServicio docenteServicio;

    //http://localhost:8080/estudiante-app/usuarios
    @GetMapping(path = "/usuarios")
    public List<Usuario> obternerUsuarios(){
        var usuarios = usuarioServicio.ListarUsuario();
        usuarios.forEach((usuario -> logger.info(usuarios.toString())));
        return usuarios;
    }

   @PostMapping(path = "/usuarios/create") // agregar Usuario
    public void agregarUsuario(@RequestBody Usuario usuario ){
        logger.info("el usuario a agregar " + usuario);
        System.out.println(usuario);
        usuarioServicio.guardarUsuario(usuario);
        //docenteServicio.guardarDocente(docente);
    }

    @GetMapping(path = "/usuarios/{id}")// buscar por ID
    public ResponseEntity<Usuario> getUsuarioId(@PathVariable Integer id){
        if(id == null){
            throw  new recurosNoEncontradoException("No se encontro el Usuario con Id"+id);
        }
        return  ResponseEntity.ok(usuarioServicio.buscarUsuarioPorId(id));
    }

    @PutMapping(path = "/usuarios/{id}")// actulizar estudiante
    public ResponseEntity<Usuario>ActulizarId(@PathVariable Integer id,
                                              @RequestBody Usuario usuarioResivido){
        Usuario usuario = usuarioServicio.buscarUsuarioPorId(id);
        if(usuario == null){
            throw  new recurosNoEncontradoException("No exite el a actualizar con Id"+id);
        }
        usuario.setNombre(usuarioResivido.getNombre());
        usuario.setContraseña(usuarioResivido.getContraseña());
        usuario.setTipo_usuario(usuarioResivido.getTipo_usuario());
        usuario.setCorreo(usuarioResivido.getCorreo());
        usuario.setEstado(usuarioResivido.isEstado());
        var docente = usuario.getDocente();

        docente.setNombre_completo(usuarioResivido.getDocente().getNombre_completo());
        docente.setApellido_completo(usuarioResivido.getDocente().getApellido_completo());
        docente.setSexo(usuarioResivido.getDocente().getSexo());
        docente.setDireccion(usuarioResivido.getDocente().getDireccion());
        docente.setPartidad_nacimiento(usuarioResivido.getDocente().getPartidad_nacimiento());
        docente.setFecha_nacimiento(usuarioResivido.getDocente().getFecha_nacimiento());
        docente.setCedula(usuarioResivido.getDocente().getCedula());
        docente.setEstado(usuarioResivido.getDocente().isEstado());
        usuarioServicio.guardarUsuario(usuario);
        return ResponseEntity.ok(usuario);
    }

   @DeleteMapping(path = "/usuarios/{id}")// eliminar docente
    public  void eliminarDocenteID(@PathVariable Integer id){
        usuarioServicio.eliminarUsuario(usuarioServicio.buscarUsuarioPorId(id));
    }



}
