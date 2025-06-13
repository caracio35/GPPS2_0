package ar.edu.unrn.seminario.persistencia;


import ar.edu.unrn.seminario.exception.ExcepcionPersistencia;
import ar.edu.unrn.seminario.modelo.Actividad;

import java.util.List;

public interface ActividadDAO {
    public void create(Actividad actividad ,String nombreDePropuesta) throws ExcepcionPersistencia;
    void update(Actividad actividad);
    void remove(Actividad actividad);
    Actividad find(int id) throws ExcepcionPersistencia;
    List<Actividad> findAllPorPropuesta(String nombre) throws ExcepcionPersistencia;
    int obtenerIdPorNombre(String nombre) throws ExcepcionPersistencia;

}
