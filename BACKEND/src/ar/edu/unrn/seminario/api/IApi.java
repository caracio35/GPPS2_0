package ar.edu.unrn.seminario.api;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import ar.edu.unrn.seminario.dto.PropuestaDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;

public interface IApi {

	void registrarUsuario(UsuarioDTO usuario);

	UsuarioDTO obtenerUsuario(String username);

	void eliminarUsuario(String username);

	List<RolDTO> obtenerRoles();

	List<RolDTO> obtenerRolesActivos();

	void guardarRol(Integer codigo, String descripcion, boolean estado); // crear el objeto de dominio �Rol�

	RolDTO obtenerRolPorCodigo(Integer codigo); // recuperar el rol almacenado

	void activarRol(Integer codigo); // recuperar el objeto Rol, implementar el comportamiento de estado.

	void desactivarRol(Integer codigo); // recuperar el objeto Rol, imp

	List<UsuarioDTO> obtenerUsuarios(); // recuperar todos los usuarios

	void activarUsuario(String username); // recuperar el objeto Usuario, implementar el comportamiento de estado.

	void desactivarUsuario(String username); // recuperar el objeto Usuario, implementar el comportamiento de estado.
	
	void crearPropuesta(PropuestaDTO propuesta);
	
	List<PropuestaDTO> buscarPropuestas();

	boolean generarIncripcionDeAlumnoApropuesta(UsuarioDTO usuario, PropuestaDTO propuesta);
	
	List<PropuestaDTO> buscarPropuestasParaAsignarTutores();

	boolean asignarProfesorYTutorAPropuesta(PropuestaDTO propuesta, UsuarioDTO profesor, UsuarioDTO tutor);

	List<PropuestaDTO> buscarPropuestasParaConvenio();
	
	void generarYGuardarConvenio(PropuestaDTO propuesta, LocalDate fechaInicio, LocalDate fechaFin, File destino) throws Exception;
	
	void registrarEntrega(String nombreActividad, File archivo);
	
	PropuestaDTO obtenerPropuestaDeAlumno(String username);
	
	void actualizarEstadoPropuesta(PropuestaDTO propuesta);
	
	List<PropuestaDTO> findTodasConDetalles();
}
