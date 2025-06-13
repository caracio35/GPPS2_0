package ar.edu.unrn.seminario.api;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.unrn.seminario.persistencia.ActividadDAO;
import ar.edu.unrn.seminario.persistencia.ActividadDAOJDBC;
import ar.edu.unrn.seminario.persistencia.ConvenioDAO;
import ar.edu.unrn.seminario.persistencia.ConvenioDAOJDBC;
import ar.edu.unrn.seminario.persistencia.EntregaDAO;
import ar.edu.unrn.seminario.persistencia.PersonaDAOJDBC;
import ar.edu.unrn.seminario.persistencia.PropuestaDAO;
import ar.edu.unrn.seminario.persistencia.PropuestaDAOJDBC;
import  ar.edu.unrn.seminario.persistencia.RolDAOJDBC;
import ar.edu.unrn.seminario.persistencia.RolDao;
import ar.edu.unrn.seminario.persistencia.UsuarioDAOJDBC;
import ar.edu.unrn.seminario.persistencia.UsuarioDao;
import ar.edu.unrn.seminario.servicios.GeneradorConvenioWord;
import ar.edu.unrn.seminario.dto.ActividadDTO;
import ar.edu.unrn.seminario.dto.PersonaDTO;
import ar.edu.unrn.seminario.dto.PropuestaDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.exception.DatosNoEncontradosException;
import ar.edu.unrn.seminario.exception.DuplicadaException;
import ar.edu.unrn.seminario.exception.ExcepcionAplicacion;
import ar.edu.unrn.seminario.exception.ExcepcionPersistencia;
import ar.edu.unrn.seminario.modelo.Actividad;
import ar.edu.unrn.seminario.modelo.Convenio;
import ar.edu.unrn.seminario.modelo.Persona;
import ar.edu.unrn.seminario.modelo.Propuesta;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;
import java.nio.file.Files;
import java.time.LocalDateTime;

import ar.edu.unrn.seminario.modelo.Entrega;
import ar.edu.unrn.seminario.persistencia.EntregaDAOJDBC;

public class PersistenceApi implements IApi {

	private RolDao rolDao;
	private UsuarioDao usuarioDao;
	private PropuestaDAO propuestaDao;
	private ConvenioDAO convenioDao ;
	private EntregaDAO entregaDao ;
	private ActividadDAO actividadDao ;
	 

	public PersistenceApi() {
		rolDao = new RolDAOJDBC();
		usuarioDao = new UsuarioDAOJDBC();
		propuestaDao = new PropuestaDAOJDBC();
		convenioDao = new ConvenioDAOJDBC();
		entregaDao = new EntregaDAOJDBC();
		actividadDao = new ActividadDAOJDBC();
	}

	@Override
	public void registrarUsuario(UsuarioDTO usuario) throws ConexionFallidaException, DuplicadaException, ExcepcionPersistencia {
		
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
	public List<UsuarioDTO> obtenerUsuarios() throws ConexionFallidaException {
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
	public List<RolDTO> obtenerRoles() throws ConexionFallidaException {
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
	public RolDTO obtenerRolPorCodigo(Integer codigo) throws ConexionFallidaException {
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

	@Override
	public void crearPropuesta(PropuestaDTO propuesta) throws ConexionFallidaException, DuplicadaException, ExcepcionPersistencia {
		
		 // Convertir el creador (único usuario que viene cargado)
	    Usuario creador = convertir(propuesta.getCreador());

	    // Convertir actividades
	    List<Actividad> actividades = new ArrayList<>();
	    for (ActividadDTO actDTO : propuesta.getActividades()) {
	        actividades.add(new Actividad(
	            actDTO.getNombre(),
	            actDTO.getHoras()
	        ));
	    }

	    // Crear propuesta con alumno, profesor y tutor en null
	    Propuesta propuest = new Propuesta(
	       propuesta.getTitulo(),
	        propuesta.getDescripcion(),
	        propuesta.getAreaDeInteres(),
	        propuesta.getObjetivo(),
	        propuesta.getComentarios(),
	        propuesta.getAceptados(),
	        creador,
	        actividades
	    );

	    // Persistir la propuesta
	    propuestaDao.create(propuest);
		
	}
	

	@Override
	public List<PropuestaDTO> buscarPropuestas() throws ExcepcionPersistencia {
		 List<Propuesta> propuestas = propuestaDao.findSoloConCreador();
		    List<PropuestaDTO> dtos = new ArrayList<>();

		    for (Propuesta p : propuestas) {
		        PropuestaDTO dto = new PropuestaDTO(
		            p.getTitulo(),
		            p.getDescripcion(),
		            p.getAreaDeInteres(),
		            p.getObjetivo(),
		            p.getComentarios(),
		            p.getAceptados(),
		            usuarioADTO(p.getCreador()),
		            listaActividadesDTO(p.getActividades())
		        );
		        dtos.add(dto);
		    }

		    return dtos;
		}
	
	@Override
	public boolean generarIncripcionDeAlumnoApropuesta(UsuarioDTO usuario, PropuestaDTO propuesta) throws ConexionFallidaException, ExcepcionPersistencia {
		
		Usuario usu = convertir(usuario);
		Usuario creador = convertir(propuesta.getCreador());
		List<ActividadDTO> actividadesDto = propuesta.getActividades();
		List<Actividad> actividad = convertirActividades(actividadesDto);
		
		  // Crear entidad Propuesta con constructor que incluye solo los datos necesarios
	    Propuesta propues = new Propuesta(
	        propuesta.getTitulo(),
	        propuesta.getDescripcion(),
	        propuesta.getAreaDeInteres(),
	        propuesta.getObjetivo(),
	        propuesta.getComentarios(),
	        propuesta.getAceptados(),
	        creador,
	        actividad
	    );

		
	 // Delegar al DAO la actualización
	    return propuestaDao.asignarAlumnoAPropuesta(usu, propues);
	}
	
	@Override
	public List<PropuestaDTO> buscarPropuestasParaAsignarTutores() throws ExcepcionPersistencia {
	    List<Propuesta> propuestas = propuestaDao.findAllCompleto();
	    List<PropuestaDTO> propuestasFiltradas = new ArrayList<>();

	    for (Propuesta p : propuestas) {
	        boolean esAprobada = p.getAceptados() == 1;
	        boolean tieneAlumno = p.getAlumno() != null;
	        boolean sinProfesor = p.getProfesor() == null;

	        if (esAprobada && tieneAlumno && sinProfesor) {
	            PropuestaDTO dto = new PropuestaDTO(
	                p.getTitulo(),
	                p.getDescripcion(),
	                p.getAreaDeInteres(),
	                p.getObjetivo(),
	                p.getComentarios(),
	                p.getAceptados(),
	                usuarioADTO(p.getCreador()),   // creador
	                usuarioADTO(p.getAlumno()),    // alumno
	                null,                          // profesor aún no asignado
	                null,                          // tutor aún no asignado
	                listaActividadesDTO(p.getActividades())
	            );

	            propuestasFiltradas.add(dto);
	        }
	    }

	    return propuestasFiltradas;
	}
	
	
	
	@Override
	public boolean asignarProfesorYTutorAPropuesta(PropuestaDTO propuesta, UsuarioDTO profesor, UsuarioDTO tutor) throws ExcepcionPersistencia {
	    return propuestaDao.asignarProfesorYTutorAPropuesta(propuesta.getTitulo(), profesor.getUsername(), tutor.getUsername());
	}
	
	@Override
	public List<PropuestaDTO> buscarPropuestasParaConvenio() throws ExcepcionPersistencia {
		List<Propuesta> propuestas = propuestaDao.findAllCompleto(); // recupera todas las propuestas con joins
	    List<PropuestaDTO> propuestasFiltradas = new ArrayList<>();

	    for (Propuesta p : propuestas) {
	        boolean esAceptada = p.getAceptados() == 1;
	        boolean tieneAlumno = p.getAlumno() != null;
	        boolean tieneProfesor = p.getProfesor() != null;
	        boolean tieneTutor = p.getTutor() != null;

	        if (esAceptada && tieneAlumno && tieneProfesor && tieneTutor) {
	            PropuestaDTO dto = new PropuestaDTO(
	                p.getTitulo(),
	                p.getDescripcion(),
	                p.getAreaDeInteres(),
	                p.getObjetivo(),
	                p.getComentarios(),
	                p.getAceptados(),
	                usuarioADTO(p.getCreador()),
	                usuarioADTO(p.getAlumno()),
	                usuarioADTO(p.getProfesor()),
	                usuarioADTO(p.getTutor()),
	                listaActividadesDTO(p.getActividades())
	            );

	            propuestasFiltradas.add(dto);
	        }
	    }

	    return propuestasFiltradas;
	}
	
	@Override
	public void generarYGuardarConvenio(PropuestaDTO propuesta, LocalDate fechaInicio, LocalDate fechaFin,
			File destino) throws Exception {
		 // Armar datos
	    Map<String, String> datos = new HashMap<>();
	    datos.put("entidad", propuesta.getCreador().getUsername());
	    datos.put("dni_entidad", propuesta.getCreador().getPersona().getDni());
	    datos.put("nombre_estudiante", propuesta.getAlumno().getPersona().getNombre() + " " + propuesta.getAlumno().getPersona().getApellido());
	    datos.put("dni_estudiante", propuesta.getAlumno().getPersona().getDni());
	    datos.put("nombre_tutor", propuesta.getTutor().getPersona().getNombre() + " " + propuesta.getTutor().getPersona().getApellido());
	    datos.put("dni_tutor", propuesta.getTutor().getPersona().getDni());
	    datos.put("nombre_supervisor", propuesta.getProfesor().getPersona().getNombre() + " " + propuesta.getProfesor().getPersona().getApellido());
	    datos.put("dni_supervisor", propuesta.getProfesor().getPersona().getDni());
	    datos.put("objetivo", propuesta.getObjetivo());
	    datos.put("Descripcion", propuesta.getDescripcion());

	    int i = 1;
	    for (ActividadDTO act : propuesta.getActividades()) {
	        datos.put("actividad" + i, act.getNombre());
	        datos.put("horas" + i, String.valueOf(act.getHoras()));
	        i++;
	        if (i > 3) break;
	    }

	    byte[] archivoBytes = GeneradorConvenioWord.generar("PlantillaDoc/plantilla_acta_pps.docx", datos, destino);

	    // Guardar en BD
	    this.crearConvenio(
	        propuesta ,
	        fechaInicio,
	        fechaFin,
	        archivoBytes,
	        destino.getName()
	       // "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
	    );
	}
	
	private void crearConvenio(PropuestaDTO propuestaDTO , LocalDate fechaInicio , LocalDate fechaFin , byte[] archivoBytes , String detino) throws ConexionFallidaException, ExcepcionPersistencia, ExcepcionAplicacion {
		
		Propuesta propuesta = new Propuesta(
		        propuestaDTO.getTitulo(),
		        propuestaDTO.getDescripcion(),
		        propuestaDTO.getAreaDeInteres(),
		        propuestaDTO.getObjetivo(),
		        propuestaDTO.getComentarios(),
		        propuestaDTO.getAceptados(),
		        convertir(propuestaDTO.getCreador()),
		        convertir(propuestaDTO.getAlumno()),
		        convertir(propuestaDTO.getProfesor()),
		        convertir(propuestaDTO.getTutor()),
		        convertirActividades(propuestaDTO.getActividades())
		    );
	
	    Convenio convenio = new Convenio(
	        fechaInicio,
	        fechaFin,
	        archivoBytes,
	        detino ,
	        propuesta 
	    );
	    
	    convenioDao.create(convenio);
	}
	
	@Override
	public void registrarEntrega(String nombreActividad, File archivo) throws ExcepcionPersistencia {
	    try {
	    	
	    	int idActividad = actividadDao.obtenerIdPorNombre(nombreActividad);
	        byte[] bytes = Files.readAllBytes(archivo.toPath());
	        Entrega entrega = new Entrega();
	        entrega.setActividadId(idActividad);
	        entrega.setFechaEntrega(LocalDateTime.now());
	        entrega.setArchivoEntregado(bytes);
	        entrega.setNombreArchivo(archivo.getName());

	        entregaDao.guardar(entrega);
	    } catch (IOException e) {
	        throw new RuntimeException("Error al leer el archivo para entrega", e);
	    }
	}
	
	@Override
	public PropuestaDTO obtenerPropuestaDeAlumno(String username) throws DatosNoEncontradosException, ExcepcionPersistencia {
		Usuario alumno = usuarioDao.find(username);
	    if (alumno == null) {
	        System.out.println("No se encontró el usuario con username: " + username);
	        return null;
	    }

	    Propuesta propuesta = propuestaDao.findPropuestaPorAlumno(alumno);
	    if (propuesta == null) {
	        System.out.println("El alumno no está asignado a ninguna propuesta. Usuario: " + username);
	        return null;
	    }

		    return new PropuestaDTO(
		        propuesta.getTitulo(),
		        propuesta.getDescripcion(),
		        propuesta.getAreaDeInteres(),
		        propuesta.getObjetivo(),
		        propuesta.getComentarios(),
		        propuesta.getAceptados(),
		        usuarioADTO(propuesta.getCreador()),
		        usuarioADTO(propuesta.getAlumno()),
		        usuarioADTO(propuesta.getProfesor()),
		        usuarioADTO(propuesta.getTutor()),
		        listaActividadesDTO(propuesta.getActividades())
		    );
		}
	@Override
	public void actualizarEstadoPropuesta(PropuestaDTO propuesta) throws ExcepcionPersistencia {
		  
		    propuestaDao.actualizarEstado(propuesta.getTitulo(), propuesta.getAceptados());
		
	}

		
	
	//////////////////////////////////////////Metodos privados que se pueden usuar /////////////////////////////////////
	//metodo para crear para pasar de persona a personaDTO
	private PersonaDTO personaADTO(Persona persona) {
	    if (persona == null) return null;
	    return new PersonaDTO(persona.getNombre(), persona.getApellido(), persona.getDni());
	}
	
	//metodo privado para pasar usuarios a usuariosDTO 
	private UsuarioDTO usuarioADTO(Usuario usuario) {
	    if (usuario == null) return null;
	    return new UsuarioDTO(
	        usuario.getUsuario(),
	        usuario.getContrasena(), 
	        personaADTO(usuario.getPersona()),
	        usuario.getEmail(),
	        usuario.getRol().getCodigo(),
	        usuario.isActivo(),
	        usuario.isActivo() ? "Activo" : "Inactivo"
	    );
	}
	
	//metoddo para pasar actividades a tipo actividaddedDTO
	private ActividadDTO actividadADTO(Actividad actividad) {
	    if (actividad == null) return null;
	    return new ActividadDTO(actividad.getNombre(), actividad.getHoras());
	}
	
	//metodo para pasar una lista de activiodades a una listas de actividadesDTO
	private List<ActividadDTO> listaActividadesDTO(List<Actividad> actividades) {
	    List<ActividadDTO> dtos = new ArrayList<>();
	    for (Actividad a : actividades) {
	        dtos.add(actividadADTO(a));
	    }
	    return dtos;
	}
	
	//convierte un ususario a usuarioDTO
	private Usuario convertir(UsuarioDTO dto) throws ConexionFallidaException {
	    Persona persona = new Persona(
	        dto.getPersona().getNombre(),
	        dto.getPersona().getApellido(),
	        dto.getPersona().getDni()
	    );
	    Rol rol = rolDao.find(dto.getRol());
	    return new Usuario(
	        dto.getUsername(),
	        dto.getPassword(),
	        dto.getEmail(),
	        true,
	        rol,
	        persona
	    );
	}
	
	//convierte a una lista de actividades dto en una lista de actividades entiti 
	private List<Actividad> convertirActividades(List<ActividadDTO> actividadDTOs) {
	    List<Actividad> actividades = new ArrayList<>();
	    for (ActividadDTO actDto : actividadDTOs) {
	        actividades.add(new Actividad(actDto.getNombre(), actDto.getHoras()));
	    }
	    return actividades;
	}
	@Override
	public List<PropuestaDTO> findTodasConDetalles() throws ExcepcionPersistencia {
	    List<Propuesta> propuestas = propuestaDao.findAllCompleto();
	    List<PropuestaDTO> dtos = new ArrayList<>();

	    for (Propuesta p : propuestas) {
	        PropuestaDTO dto = new PropuestaDTO(
	            p.getTitulo(),
	            p.getDescripcion(),
	            p.getAreaDeInteres(),
	            p.getObjetivo(),
	            p.getComentarios(),
	            p.getAceptados(),
	            usuarioADTO(p.getCreador()),
	            usuarioADTO(p.getAlumno()),
	            usuarioADTO(p.getProfesor()),
	            usuarioADTO(p.getTutor()),
	            listaActividadesDTO(p.getActividades())
	        );
	        dtos.add(dto);
	    }

	    return dtos;
	}

	@Override
	public boolean convenioYaExiste(PropuestaDTO propuesta) throws ExcepcionPersistencia {
	    return convenioDao.existeConvenioParaPropuesta(propuesta.getTitulo());
	}

	@Override
	public LocalDate obtenerFechaInicioConvenio(PropuestaDTO propuesta) throws ExcepcionPersistencia {
	    return convenioDao.obtenerFechaInicio(propuesta.getTitulo());
	}

	@Override
	public LocalDate obtenerFechaFinConvenio(PropuestaDTO propuesta) throws ExcepcionPersistencia {
	    return convenioDao.obtenerFechaFin(propuesta.getTitulo());
	}

	@Override
	public void generarWordDesdeConvenioExistente(PropuestaDTO propuesta, LocalDate fechaInicio, LocalDate fechaFin, File destino) throws Exception {
	    Map<String, String> datos = new HashMap<>();
	    datos.put("entidad", propuesta.getCreador().getUsername());
	    datos.put("dni_entidad", propuesta.getCreador().getPersona().getDni());
	    datos.put("nombre_estudiante", propuesta.getAlumno().getPersona().getNombre() + " " + propuesta.getAlumno().getPersona().getApellido());
	    datos.put("dni_estudiante", propuesta.getAlumno().getPersona().getDni());
	    datos.put("nombre_tutor", propuesta.getTutor().getPersona().getNombre() + " " + propuesta.getTutor().getPersona().getApellido());
	    datos.put("dni_tutor", propuesta.getTutor().getPersona().getDni());
	    datos.put("nombre_supervisor", propuesta.getProfesor().getPersona().getNombre() + " " + propuesta.getProfesor().getPersona().getApellido());
	    datos.put("dni_supervisor", propuesta.getProfesor().getPersona().getDni());
	    datos.put("objetivo", propuesta.getObjetivo());
	    datos.put("Descripcion", propuesta.getDescripcion());

	    int i = 1;
	    for (ActividadDTO act : propuesta.getActividades()) {
	        datos.put("actividad" + i, act.getNombre());
	        datos.put("horas" + i, String.valueOf(act.getHoras()));
	        i++;
	        if (i > 3) break;
	    }

	    GeneradorConvenioWord.generar("PlantillaDoc/plantilla_acta_pps.docx", datos, destino);
	}
	

	
	
	

	

	

	

	
	
}
