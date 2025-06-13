package ar.edu.unrn.seminario.api;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import ar.edu.unrn.seminario.dto.PropuestaDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.exception.DatosNoEncontradosException;
import ar.edu.unrn.seminario.exception.DuplicadaException;
import ar.edu.unrn.seminario.exception.ExcepcionPersistencia;

public interface IApi {

	void registrarUsuario(UsuarioDTO usuario) throws ConexionFallidaException, DuplicadaException, ExcepcionPersistencia;

	UsuarioDTO obtenerUsuario(String username);

	void eliminarUsuario(String username);

	List<RolDTO> obtenerRoles() throws ConexionFallidaException;

	List<RolDTO> obtenerRolesActivos();

	void guardarRol(Integer codigo, String descripcion, boolean estado); // crear el objeto de dominio �Rol�

	RolDTO obtenerRolPorCodigo(Integer codigo) throws ConexionFallidaException; // recuperar el rol almacenado

	void activarRol(Integer codigo); // recuperar el objeto Rol, implementar el comportamiento de estado.

	void desactivarRol(Integer codigo); // recuperar el objeto Rol, imp

	List<UsuarioDTO> obtenerUsuarios() throws ConexionFallidaException; // recuperar todos los usuarios

	void activarUsuario(String username); // recuperar el objeto Usuario, implementar el comportamiento de estado.

	void desactivarUsuario(String username); // recuperar el objeto Usuario, implementar el comportamiento de estado.
	
	void crearPropuesta(PropuestaDTO propuesta) throws ConexionFallidaException, DuplicadaException, ExcepcionPersistencia;
	
	List<PropuestaDTO> buscarPropuestas() throws ExcepcionPersistencia;

	boolean generarIncripcionDeAlumnoApropuesta(UsuarioDTO usuario, PropuestaDTO propuesta) throws ConexionFallidaException, ExcepcionPersistencia;
	
	List<PropuestaDTO> buscarPropuestasParaAsignarTutores() throws ExcepcionPersistencia;

	boolean asignarProfesorYTutorAPropuesta(PropuestaDTO propuesta, UsuarioDTO profesor, UsuarioDTO tutor) throws ExcepcionPersistencia;

	List<PropuestaDTO> buscarPropuestasParaConvenio() throws ExcepcionPersistencia;
	
	void generarYGuardarConvenio(PropuestaDTO propuesta, LocalDate fechaInicio, LocalDate fechaFin, File destino) throws Exception;
	
	void registrarEntrega(String nombreActividad, File archivo) throws ExcepcionPersistencia;
	
	PropuestaDTO obtenerPropuestaDeAlumno(String username) throws DatosNoEncontradosException, ExcepcionPersistencia;
	
	void actualizarEstadoPropuesta(PropuestaDTO propuesta) throws ExcepcionPersistencia;
	
	List<PropuestaDTO> findTodasConDetalles() throws ExcepcionPersistencia;
}
