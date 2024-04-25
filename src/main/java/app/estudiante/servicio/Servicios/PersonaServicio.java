//package app.estudiante.servicio;
//
//import app.estudiante.modelo.Persona;
//import app.estudiante.repositorio.PersonaRepositorio;
//import app.estudiante.servicio.InterfacesServicios.IPersonaServicio;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class PersonaServicio implements IPersonaServicio {
//
//    @Autowired
//    private PersonaRepositorio personaRepositorio;
//
//    @Override
//    public List<Persona> ListarPersona() {
////        List<Persona> personas= personaRepositorio.findAll();
//        return List.of();//personas;
//    }
//
//    @Override
//    public Persona buscarPersonaPorId(Integer idpersona) {
////        Persona  persona = personaRepositorio.findById(idpersona).orElse(null);
//        return null;//persona;
//    }
//
//    @Override
//    public void guardarPersona(Persona persona) {
////     personaRepositorio.save(persona);
//    }
//
//    @Override
//    public void eliminarPersona(Persona persona) {
////     personaRepositorio.delete(persona);
//    }
//}
