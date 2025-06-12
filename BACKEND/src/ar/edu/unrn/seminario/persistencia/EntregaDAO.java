package ar.edu.unrn.seminario.persistencia;

import ar.edu.unrn.seminario.modelo.Entrega;

public interface EntregaDAO {

	void guardar(Entrega entrega);
	
	Entrega buscarPorId(int id);
}
