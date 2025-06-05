package ar.edu.unrn.seminario.api;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.persistencia.PersonaDAOJDBC;
import  ar.edu.unrn.seminario.persistencia.RolDAOJDBC;
import ar.edu.unrn.seminario.persistencia.RolDao;
import ar.edu.unrn.seminario.persistencia.UsuarioDAOJDBC;
import ar.edu.unrn.seminario.persistencia.UsuarioDao;
import ar.edu.unrn.seminario.dto.PersonaDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.modelo.Persona;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;

public class PersistenceApi implements IApi {

	private RolDao rolDao;
	private UsuarioDao usuarioDao;

	public PersistenceApi() {
		rolDao = new RolDAOJDBC();
		usuarioDao = new UsuarioDAOJDBC();
		
	}

	@Override
	public void registrarUsuario(UsuarioDTO usuario) {
		
		//buscar el rol en la base de datos a trabas de find
		Rol rol = rolDao.find(usuario.getRol());
		
		//crea la persona atraves de PersonaDao pasado desde el usuario dao
		Persona persona = new Persona(usuario.getPersona().getNombre(),usuario.getPersona().getApellido(),usuario.getPersona().getDni());
		//pasa a persistencia a persona
		PersonaDAOJDBC personaDao = new PersonaDAOJDBC();
		personaDao.create(persona);
		
		//crea el usuario ppara pasarlo a persistencia 
		Usuario usuario1 = new Usuario(usuario.getUsername() , usuario.getPassword() ,usuario.getEmail(), true , rol , persona);
		
		this.usuarioDao.create(usuario1);
	}

	@Override
	public List<UsuarioDTO> obtenerUsuarios() {
		  List<UsuarioDTO> dtos = new ArrayList<>();
		    List<Usuario> usuarios = usuarioDao.findAll();

		    for (Usuario u : usuarios) {
		        Persona persona = u.getPersona();
		        Rol rol = u.getRol();

		        PersonaDTO personaDTO = new PersonaDTO(
		            persona.getNombre(),
		            persona.getApellido(),
		            persona.getDni()
		        );

		        UsuarioDTO dto = new UsuarioDTO(  
		        		u.getUsuario(),
		        		u.getContrasena(),
		        		personaDTO,
		        		u.getEmail(),
		        		u.getRol().getCodigo(),
		        		u.isActivo(),
		        		null //preguntar esto a jose
		        );

		        dtos.add(dto);
		    }

		    return dtos;
		}

	@Override
	public UsuarioDTO obtenerUsuario(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminarUsuario(String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<RolDTO> obtenerRoles() {
		List<Rol> roles = rolDao.findAll();
		List<RolDTO> rolesDTO = new ArrayList<>(0);
		for (Rol rol : roles) {
			rolesDTO.add(new RolDTO(rol.getCodigo(), rol.getNombre(), rol.getDescripcion()));
		}
		return rolesDTO;
	}

	@Override
	public List<RolDTO> obtenerRolesActivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void guardarRol(Integer codigo, String descripcion, boolean estado) {
		// TODO Auto-generated method stub

	}

	@Override
	public RolDTO obtenerRolPorCodigo(Integer codigo) {
		Rol rol = rolDao.find(codigo);
		RolDTO rolDTO = new RolDTO(rol.getCodigo(), rol.getNombre(), rol.getDescripcion());
		return rolDTO;
	}

	@Override
	public void activarRol(Integer codigo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void desactivarRol(Integer codigo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void activarUsuario(String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public void desactivarUsuario(String username) {
		// TODO Auto-generated method stub

	}

}
