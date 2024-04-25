package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.Grado;

import java.util.List;

public interface IGradoServicio {
    public List<Grado> ListarGrado();
    public Grado buscarGradoPorId(Integer idGrado);
    public void guardarGrado(Grado grado);
    public  void eliminarGrado(Grado grado);
}
