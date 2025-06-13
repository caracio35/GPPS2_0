package  ar.edu.unrn.seminario.persistencia;

import java.util.List;

import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.exception.DatosNoEncontradosException;
import ar.edu.unrn.seminario.exception.DuplicadaException;
import ar.edu.unrn.seminario.modelo.Usuario;

public interface UsuarioDao {
	void create(Usuario Usuario) throws DuplicadaException;

	void update(Usuario Usuario);

	void remove(Long id);

	void remove(Usuario Usuario);

	Usuario find(String username) throws DatosNoEncontradosException;

	List<Usuario> findAll() throws ConexionFallidaException;

}
