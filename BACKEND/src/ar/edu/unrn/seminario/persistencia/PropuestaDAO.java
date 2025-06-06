package ar.edu.unrn.seminario.persistencia;


import java.util.List;

import ar.edu.unrn.seminario.modelo.Propuesta;

public interface PropuestaDAO {

        void create(Propuesta propuesta);
    
        void update(Propuesta propuesta);
    
        void remove(Propuesta propuesta);
    
        Propuesta find(Integer codigo);
    
        List<Propuesta> findAll();
    
    }
    