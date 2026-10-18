package app.estudiante.api;

import app.estudiante.exception.recursosNoEncontradoException;
import app.estudiante.modelo.Usuario;
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
    public ResponseEntity<Usuario> getUsuarioId(@PathVariable Long id){
        if(id == null){
            throw  new recursosNoEncontradoException("No se encontro el Usuario con Id"+id);
        }
        return  ResponseEntity.ok(usuarioServicio.buscarUsuarioPorId(id));
    }

    @PutMapping(path = "/usuarios/{id}")// actulizar estudiante
    public ResponseEntity<Usuario>ActulizarId(@PathVariable Long id,
                                              @RequestBody Usuario usuarioResivido){
        Usuario usuario = usuarioServicio.buscarUsuarioPorId(id);
        if(usuario == null){
            throw  new recursosNoEncontradoException("No exite el a actualizar con Id"+id);
        }
        usuario.setUsername(usuarioResivido.getUsername());
        usuario.setPasswordHash(usuarioResivido.getPasswordHash());
        usuario.setEstado(usuarioResivido.getEstado());
        usuario.setPersona(usuarioResivido.getPersona());
        usuario.setRoles(usuarioResivido.getRoles());
        usuarioServicio.guardarUsuario(usuario);
        return ResponseEntity.ok(usuario);
    }

   @DeleteMapping(path = "/usuarios/{id}")// eliminar docente
    public  void eliminarDocenteID(@PathVariable Long id){
        usuarioServicio.eliminarUsuario(usuarioServicio.buscarUsuarioPorId(id));
    }



}
