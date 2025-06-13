package ar.edu.unrn.seminario.persistencia;

import ar.edu.unrn.seminario.exception.ExcepcionPersistencia;
import ar.edu.unrn.seminario.modelo.Entrega;

public interface EntregaDAO {

	void guardar(Entrega entrega) throws ExcepcionPersistencia;
	
	Entrega buscarPorId(int id) throws ExcepcionPersistencia;
}
