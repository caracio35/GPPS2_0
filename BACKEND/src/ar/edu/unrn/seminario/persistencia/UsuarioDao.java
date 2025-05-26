package  ar.edu.unrn.seminario.persistencia;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Usuario;

public interface UsuarioDao {
	void create(Usuario Usuario);

	void update(Usuario Usuario);

	void remove(Long id);

	void remove(Usuario Usuario);

	Usuario find(String username);

	List<Usuario> findAll();

}
