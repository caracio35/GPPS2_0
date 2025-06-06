package ar.edu.unrn.seminario.persistencia;


import ar.edu.unrn.seminario.modelo.Actividad;

import java.util.List;

public interface ActividadDAO {
    public void create(Actividad actividad ,String nombreDePropuesta);
    void update(Actividad actividad);
    void remove(Actividad actividad);
    Actividad find(int id);
    public List<Actividad> findAllPorPropuesta(String nombre);

}
