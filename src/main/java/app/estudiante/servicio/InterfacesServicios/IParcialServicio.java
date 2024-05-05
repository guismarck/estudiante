package app.estudiante.servicio.InterfacesServicios;

import app.estudiante.modelo.Parcial;

import java.util.List;

public interface IParcialServicio {
    public List<Parcial> ListarParcial();
    public Parcial buscarParcialPorId(Integer idParcial);
    public void guardarParcial(Parcial parcial);
    public  void eliminarParcial(Parcial parcial);
}
