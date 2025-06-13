package ar.edu.unrn.seminario.persistencia;


import java.util.List;

import ar.edu.unrn.seminario.exception.DuplicadaException;
import ar.edu.unrn.seminario.exception.ExcepcionPersistencia;
import ar.edu.unrn.seminario.modelo.Propuesta;
import ar.edu.unrn.seminario.modelo.Usuario;

public interface PropuestaDAO {

        void create(Propuesta propuesta) throws DuplicadaException, ExcepcionPersistencia;
    
        void update(Propuesta propuesta) throws DuplicadaException, ExcepcionPersistencia;
    
        void remove(Propuesta propuesta);
    
        Propuesta find(Integer codigo);
    
 
        List<Propuesta> findSoloConCreador() throws ExcepcionPersistencia;

        List<Propuesta> findAllCompleto() throws ExcepcionPersistencia;

        boolean asignarAlumnoAPropuesta(Usuario usuario, Propuesta propuesta) throws ExcepcionPersistencia;
        
        Propuesta findPropuestaPorAlumno(Usuario alumno) throws ExcepcionPersistencia;
        
        boolean asignarProfesorYTutorAPropuesta(String tituloPropuesta, String usuarioProfesor, String usuarioTutor) throws ExcepcionPersistencia;
        
        void actualizarEstado(String titulo, int estado) throws ExcepcionPersistencia;
}
    
    