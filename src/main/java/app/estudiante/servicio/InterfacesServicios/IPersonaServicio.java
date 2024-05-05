package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.Persona;

import java.util.List;

public interface IPersonaServicio {
    public List<Persona>ListarPersona();
    public Persona buscarPersonaPorId(Integer idEstudiante );
    public void guardarPersona (Persona persona);
    public  void eliminarPersona(Persona persona);

}
