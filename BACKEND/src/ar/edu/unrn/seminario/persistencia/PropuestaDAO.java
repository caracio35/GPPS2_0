package ar.edu.unrn.seminario.persistencia;


import java.util.List;

import ar.edu.unrn.seminario.modelo.Propuesta;
import ar.edu.unrn.seminario.modelo.Usuario;

public interface PropuestaDAO {

        void create(Propuesta propuesta);
    
        void update(Propuesta propuesta);
    
        void remove(Propuesta propuesta);
    
        Propuesta find(Integer codigo);
    
 
        List<Propuesta> findSoloConCreador();

        List<Propuesta> findAllCompleto();

        boolean asignarAlumnoAPropuesta(Usuario usuario, Propuesta propuesta);
        
        Propuesta findPropuestaPorAlumno(Usuario alumno);
        
        boolean asignarProfesorYTutorAPropuesta(String tituloPropuesta, String usuarioProfesor, String usuarioTutor);
        
        void actualizarEstado(String titulo, int estado);
}
    
    