package ar.edu.unrn.seminario.persistencia;

import ar.edu.unrn.seminario.exception.DatosNoEncontradosException;
import ar.edu.unrn.seminario.exception.DuplicadaException;
import ar.edu.unrn.seminario.exception.ExcepcionPersistencia;
import ar.edu.unrn.seminario.modelo.Actividad;
import ar.edu.unrn.seminario.modelo.Persona;
import ar.edu.unrn.seminario.modelo.Propuesta;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class PropuestaDAOJDBC implements PropuestaDAO{

    public void create(Propuesta propuesta) throws DuplicadaException, ExcepcionPersistencia {
    	 Connection conn = null;
         PreparedStatement statement = null;

         try {
             conn = ConnectionManager.getConnection();
             statement = conn.prepareStatement(
                 "INSERT INTO propuesta (titulo, descripcion, area_interes, objetivo, comentarios, aceptada, creador, alumno, tutor, profesor) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

             statement.setString(1, propuesta.getTitulo());
             statement.setString(2, propuesta.getDescripcion());
             statement.setString(3, propuesta.getAreaDeInteres());
             statement.setString(4, propuesta.getObjetivo());
             statement.setString(5, propuesta.getComentarios());
             statement.setInt(6, propuesta.getAceptados());
             statement.setString(7, propuesta.getCreador().getUsuario());

             // Estos pueden venir nulos
             if (propuesta.getAlumno() != null) {
                 statement.setString(8, propuesta.getAlumno().getUsuario());
             } else {
                 statement.setNull(8, java.sql.Types.VARCHAR);
             }

             if (propuesta.getTutor() != null) {
                 statement.setString(9, propuesta.getTutor().getUsuario());
             } else {
                 statement.setNull(9, java.sql.Types.VARCHAR);
             }

             if (propuesta.getProfesor() != null) {
                 statement.setString(10, propuesta.getProfesor().getUsuario());
             } else {
                 statement.setNull(10, java.sql.Types.VARCHAR);
             }

             statement.executeUpdate();

             //Guardamos las actividades desde este DAO directamente
             guardarActividades(conn, propuesta.getActividades(), propuesta.getTitulo());

         } catch (SQLIntegrityConstraintViolationException e) {
        	    throw new DuplicadaException("Ya existe una propuesta con el mismo identificador.");
        	} catch (SQLException e) {
        	    throw new ExcepcionPersistencia("Error al insertar propuesta: " + e.getMessage());
        	} finally {
        	    ConnectionManager.disconnect();
        	}
     }


    @Override
    public void update(Propuesta propuesta) throws DuplicadaException, ExcepcionPersistencia {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = ConnectionManager.getConnection();
            statement = conn.prepareStatement(
                    "UPDATE propuesta SET " +
                            "titulo = ?, " +
                            "descripcion = ?, " +
                            "area_interes = ?, " +
                            "objetivo = ?, " +
                            "comentarios = ?, " +
                            "aceptada = ?, " +
                            "creador = ?, " +
                            "alumno = ?, " +
                            "tutor = ?, " +
                            "profesor = ? " );

            // Establecemos los valores para cada campo
            statement.setString(1, propuesta.getTitulo());
            statement.setString(2, propuesta.getDescripcion());
            statement.setString(3, propuesta.getAreaDeInteres());
            statement.setString(4, propuesta.getObjetivo());
            statement.setString(5, propuesta.getComentarios());
            statement.setInt(6, propuesta.getAceptados());
            statement.setString(7, propuesta.getCreador().getUsuario());
            statement.setString(8, propuesta.getAlumno().getUsuario());
            statement.setString(9, propuesta.getTutor().getUsuario());
            statement.setString(10, propuesta.getProfesor().getUsuario());

            statement.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicadaException("Ya existe una propuesta con los mismos datos.");
        } catch (SQLException e) {
            throw new ExcepcionPersistencia("Error al actualizar propuesta: " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void remove(Propuesta propuesta) {

    }

    @Override
    public Propuesta find(Integer codigo) {
        return null;
    }
    @Override
    public List<Propuesta> findSoloConCreador() throws ExcepcionPersistencia {
        List<Propuesta> propuestas = new ArrayList<>();

        String sql = "SELECT p.*, per.dni AS creador_dni, per.nombre AS creador_nombre, per.apellido AS creador_apellido, " +
                     "u.email AS creador_email, u.activo AS creador_activo, u.rol AS creador_rol " +
                     "FROM propuesta p " +
                     "LEFT JOIN usuario u ON p.creador = u.usuario " +
                     "LEFT JOIN persona per ON u.persona = per.dni";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int propuestaId = rs.getInt("id");

                Persona persona = new Persona(
                    rs.getString("creador_nombre"),
                    rs.getString("creador_apellido"),
                    rs.getString("creador_dni")
                );

                Rol rol = obtenerRolPorId(conn ,rs.getInt("creador_rol"));

                Usuario creador = new Usuario(
                    rs.getString("creador"),
                    "", // contrasena no se necesita
                    rs.getString("creador_email"),
                    rs.getBoolean("creador_activo"),
                    rol,
                    persona
                );

                List<Actividad> actividades = obtenerActividadesPorPropuestaId(conn ,propuestaId);

                Propuesta propuesta = new Propuesta(
                    rs.getString("titulo"),
                    rs.getString("descripcion"),
                    rs.getString("area_interes"),
                    rs.getString("objetivo"),
                    rs.getString("comentarios"),
                    rs.getInt("aceptada"),
                    creador,
                    actividades
                );

                propuestas.add(propuesta);
            }

        } catch (SQLException e) {
            throw new ExcepcionPersistencia("Error al obtener propuestas básicas: " + e.getMessage());
        
        }

        return propuestas;
    }
    
    
    @Override
    public List<Propuesta> findAllCompleto() throws ExcepcionPersistencia {
        List<Propuesta> propuestas = new ArrayList<>();

        String sql = "SELECT p.*, " +
                     "uc.usuario AS creador, uc.email AS creador_email, uc.activo AS creador_activo, uc.rol AS creador_rol, c.dni AS creador_dni, c.nombre AS creador_nombre, c.apellido AS creador_apellido, " +
                     "ua.usuario AS alumno, ua.email AS alumno_email, ua.activo AS alumno_activo, ua.rol AS alumno_rol, a.dni AS alumno_dni, a.nombre AS alumno_nombre, a.apellido AS alumno_apellido, " +
                     "ut.usuario AS tutor, ut.email AS tutor_email, ut.activo AS tutor_activo, ut.rol AS tutor_rol, t.dni AS tutor_dni, t.nombre AS tutor_nombre, t.apellido AS tutor_apellido, " +
                     "up.usuario AS profesor, up.email AS profesor_email, up.activo AS profesor_activo, up.rol AS profesor_rol, pr.dni AS profesor_dni, pr.nombre AS profesor_nombre, pr.apellido AS profesor_apellido " +
                     "FROM propuesta p " +
                     "LEFT JOIN usuario uc ON p.creador = uc.usuario " +
                     "LEFT JOIN persona c ON uc.persona = c.dni " +
                     "LEFT JOIN usuario ua ON p.alumno = ua.usuario " +
                     "LEFT JOIN persona a ON ua.persona = a.dni " +
                     "LEFT JOIN usuario ut ON p.tutor = ut.usuario " +
                     "LEFT JOIN persona t ON ut.persona = t.dni " +
                     "LEFT JOIN usuario up ON p.profesor = up.usuario " +
                     "LEFT JOIN persona pr ON up.persona = pr.dni";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int propuestaId = rs.getInt("id");

                Usuario creador = construirUsuarioCompleto(conn ,rs, "creador");
                Usuario alumno = construirUsuarioCompleto(conn ,rs, "alumno");
                Usuario tutor = construirUsuarioCompleto(conn , rs, "tutor");
                Usuario profesor = construirUsuarioCompleto(conn,rs, "profesor");

                List<Actividad> actividades = obtenerActividadesPorPropuestaId(conn ,propuestaId);

                Propuesta propuesta = new Propuesta(
                    rs.getString("titulo"),
                    rs.getString("descripcion"),
                    rs.getString("area_interes"),
                    rs.getString("objetivo"),
                    rs.getString("comentarios"),
                    rs.getInt("aceptada"),
                    creador,
                    alumno,
                    profesor,
                    tutor,
                    actividades
                );

                propuestas.add(propuesta);
            }

        }catch (SQLException e) {
            throw new ExcepcionPersistencia("Error al obtener propuestas completas: " + e.getMessage());
        }

        return propuestas;
    }
    
    @Override
    public boolean asignarAlumnoAPropuesta(Usuario usuario, Propuesta propuesta) throws ExcepcionPersistencia{
    	  String buscarUsuario = "SELECT usuario FROM usuario WHERE usuario = ?";
    	    String buscarIdPropuesta = "SELECT id FROM propuesta WHERE titulo = ?";
    	    String verificarInscripcion = "SELECT COUNT(*) FROM propuesta WHERE alumno = ?";
    	    String updatePropuesta = "UPDATE propuesta SET alumno = ? WHERE id = ?";

    	    try (Connection conn = ConnectionManager.getConnection()) {

    	        // Paso 1: Verificar que el usuario alumno exista
    	        try (PreparedStatement stmt = conn.prepareStatement(buscarUsuario)) {
    	            stmt.setString(1, usuario.getUsuario());
    	            ResultSet rs = stmt.executeQuery();
    	            if (!rs.next()) {
    	            	  throw new DatosNoEncontradosException("Usuario no encontrado: " + usuario.getUsuario());
    	                
    	            }
    	        }

    	        // Paso 2: Buscar ID de la propuesta
    	        int propuestaId;
    	        try (PreparedStatement stmt = conn.prepareStatement(buscarIdPropuesta)) {
    	            stmt.setString(1, propuesta.getTitulo());
    	            ResultSet rs = stmt.executeQuery();
    	            if (rs.next()) {
    	                propuestaId = rs.getInt("id");
    	            } else {
    	            	throw new DatosNoEncontradosException("Propuesta no encontrada: " + propuesta.getTitulo());
    	               
    	            }
    	        }

    	        // Paso 3: Verificar si el alumno ya está asignado
    	        try (PreparedStatement stmt = conn.prepareStatement(verificarInscripcion)) {
    	            stmt.setString(1, usuario.getUsuario());
    	            ResultSet rs = stmt.executeQuery();
    	            if (rs.next() && rs.getInt(1) > 0) {
    	            	  throw new DuplicadaException("El alumno ya está inscrito en una propuesta.");
    	            }
    	        }

    	        // Paso 4: Actualizar la propuesta
    	        try (PreparedStatement stmt = conn.prepareStatement(updatePropuesta)) {
    	            stmt.setString(1, usuario.getUsuario());
    	            stmt.setInt(2, propuestaId);
    	            int filas = stmt.executeUpdate();
    	            if (filas == 0) {
    	                throw new ExcepcionPersistencia("No se pudo asignar el alumno a la propuesta.");
    	            }
    	        }

    	    } catch (SQLException e) {
    	        throw new ExcepcionPersistencia("Error al asignar alumno a propuesta: " + e.getMessage());
    	    }
			return false;
    	}
    
    
    
    private void guardarActividades(Connection conn, List<Actividad> actividades, String tituloPropuesta) throws SQLException {
    	 // Obtener el ID de la propuesta
        String sqlBuscarId = "SELECT id FROM propuesta WHERE titulo = ?";
        int propuestaId;

        try (PreparedStatement buscarIdStmt = conn.prepareStatement(sqlBuscarId)) {
            buscarIdStmt.setString(1, tituloPropuesta);
            try (ResultSet rs = buscarIdStmt.executeQuery()) {
                if (rs.next()) {
                    propuestaId = rs.getInt("id");
                } else {
                	 throw new DatosNoEncontradosException("No se encontró propuesta con título: " + tituloPropuesta);
                }
            }
        }

        // Insertar actividades usando el ID de la propuesta
        String sqlInsertar = "INSERT INTO actividad (nombre_actividad, horas, propuesta_id) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sqlInsertar)) {
            for (Actividad a : actividades) {
                stmt.setString(1, a.getNombre());
                stmt.setInt(2, a.getHoras());
                stmt.setInt(3, propuestaId); 
                stmt.executeUpdate();
            }
        }
    }
    
    private List<Actividad> obtenerActividadesPorPropuestaId(Connection conn, int propuestaId) throws SQLException {
        List<Actividad> actividades = new ArrayList<>();
        String sql = "SELECT id, nombre_actividad, horas FROM actividad WHERE propuesta_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, propuestaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Actividad actividad = new Actividad(
                        rs.getString("nombre_actividad"),
                        rs.getInt("horas")
                    );
                    actividades.add(actividad);
                }
            }
        }
        return actividades;
    }
    
    private Rol obtenerRolPorId(Connection conn, int idRol) throws SQLException {
        String sql = "SELECT id, nombre, descripcion FROM roles WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idRol);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Rol(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                    );
                }
            }
        }
        return null;
    }
    private Usuario construirUsuarioCompleto(Connection conn, ResultSet rs, String alias) throws SQLException {
        String usuario = rs.getString(alias);
        if (usuario == null) return null;

        String nombre = rs.getString(alias + "_nombre");
        String apellido = rs.getString(alias + "_apellido");
        String dni = rs.getString(alias + "_dni");
        String email = rs.getString(alias + "_email");
        boolean activo = rs.getBoolean(alias + "_activo");
        int rolId = rs.getInt(alias + "_rol");

        Persona persona = new Persona(nombre, apellido, dni);
        Rol rol = obtenerRolPorId(conn, rolId);

        return new Usuario(usuario, "", email, activo, rol, persona);
    }


    @Override
    public Propuesta findPropuestaPorAlumno(Usuario alumno) throws ExcepcionPersistencia {
        Propuesta propuesta = null;

        String sql = "SELECT p.*, " +
                     "uc.usuario AS creador, uc.email AS creador_email, uc.activo AS creador_activo, uc.rol AS creador_rol, c.dni AS creador_dni, c.nombre AS creador_nombre, c.apellido AS creador_apellido, " +
                     "ua.usuario AS alumno, ua.email AS alumno_email, ua.activo AS alumno_activo, ua.rol AS alumno_rol, a.dni AS alumno_dni, a.nombre AS alumno_nombre, a.apellido AS alumno_apellido, " +
                     "ut.usuario AS tutor, ut.email AS tutor_email, ut.activo AS tutor_activo, ut.rol AS tutor_rol, t.dni AS tutor_dni, t.nombre AS tutor_nombre, t.apellido AS tutor_apellido, " +
                     "up.usuario AS profesor, up.email AS profesor_email, up.activo AS profesor_activo, up.rol AS profesor_rol, pr.dni AS profesor_dni, pr.nombre AS profesor_nombre, pr.apellido AS profesor_apellido " +
                     "FROM propuesta p " +
                     "LEFT JOIN usuario uc ON p.creador = uc.usuario " +
                     "LEFT JOIN persona c ON uc.persona = c.dni " +
                     "LEFT JOIN usuario ua ON p.alumno = ua.usuario " +
                     "LEFT JOIN persona a ON ua.persona = a.dni " +
                     "LEFT JOIN usuario ut ON p.tutor = ut.usuario " +
                     "LEFT JOIN persona t ON ut.persona = t.dni " +
                     "LEFT JOIN usuario up ON p.profesor = up.usuario " +
                     "LEFT JOIN persona pr ON up.persona = pr.dni " +
                     "WHERE p.alumno = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, alumno.getUsuario());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int propuestaId = rs.getInt("id");

                    Usuario creador = construirUsuarioCompleto(conn, rs, "creador");
                    Usuario alumnoEncontrado = construirUsuarioCompleto(conn, rs, "alumno");
                    Usuario tutor = construirUsuarioCompleto(conn, rs, "tutor");
                    Usuario profesor = construirUsuarioCompleto(conn, rs, "profesor");

                    List<Actividad> actividades = obtenerActividadesPorPropuestaId(conn, propuestaId);

                    propuesta = new Propuesta(
                        rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getString("area_interes"),
                        rs.getString("objetivo"),
                        rs.getString("comentarios"),
                        rs.getInt("aceptada"),
                        creador,
                        alumnoEncontrado,
                        profesor,
                        tutor,
                        actividades
                    );
                }
            }

        } catch (SQLException e) {
            throw new ExcepcionPersistencia("Error al buscar propuesta por alumno: " + e.getMessage());
       
        }

        return propuesta;
    }
    
    public boolean asignarProfesorYTutorAPropuesta(String tituloPropuesta, String usuarioProfesor, String usuarioTutor) throws ExcepcionPersistencia {
        String sql = "UPDATE propuesta SET profesor = ?, tutor = ? WHERE titulo = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuarioProfesor);
            stmt.setString(2, usuarioTutor);
            stmt.setString(3, tituloPropuesta);

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;

        } catch (SQLException e) {
            throw new ExcepcionPersistencia("Error al asignar profesor y tutor: " + e.getMessage());
        }
    }
    
    @Override
    public void actualizarEstado(String titulo, int estado) throws ExcepcionPersistencia {
        String sql = "UPDATE propuesta SET aceptada = ? WHERE titulo = ?";
        
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, estado);
            stmt.setString(2, titulo);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new ExcepcionPersistencia("Error al actualizar estado de propuesta: " + e.getMessage());
        }
    }
	
}
