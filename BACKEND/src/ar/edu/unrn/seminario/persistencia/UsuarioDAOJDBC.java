package ar.edu.unrn.seminario.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Persona;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;

public class UsuarioDAOJDBC implements UsuarioDao {

	@Override
	public void create(Usuario usuario) {
		try {
			// 1. Crear la persona primero
			//Persona persona = usuario.getPersona();
			//PersonaDAOJDBC personaDAO = new PersonaDAOJDBC();
			//personaDAO.create(persona);

			// 2. Crear el usuario y asociar el dni de la persona
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"INSERT INTO usuario(usuario, contrasena, email, activo, rol, persona) VALUES (?, ?, ?, ?, ?, ?)");

			statement.setString(1, usuario.getUsuario());
			statement.setString(2, usuario.getContrasena());
			statement.setString(3, usuario.getEmail());
			statement.setBoolean(4, usuario.isActivo());
			statement.setInt(5, usuario.getRol().getCodigo()); // o getCodigo() según tu modelo
			statement.setString(6, usuario.getPersona().getDni());

			int cantidad = statement.executeUpdate();
			if (cantidad <= 0) {
				System.out.println("Error al insertar usuario");
				// TODO: disparar Exception propia
			}
		} catch (SQLException e) {
			System.out.println("Error al procesar consulta: " + e.getMessage());
			// TODO: disparar Exception propia
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error al insertar un usuario: " + e.getMessage());
			// TODO: disparar Exception propia
			e.printStackTrace();
		} finally {
			ConnectionManager.disconnect();
		}

	}

	@Override
	public void update(Usuario usuario) {
		// TODO Auto-generated method stub

		// if (e instanceof SQLIntegrityConstraintViolationException) {
		// // Duplicate entry
		// } else {
		// // Other SQL Exception
		// }

	}

	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Usuario find(String username) {
		return null;
	}

	@Override
	public List<Usuario> findAll() {
		 List<Usuario> usuarios = new ArrayList<>();
		    Connection conn = null;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;

		    try {
		        conn = ConnectionManager.getConnection();
		        stmt = conn.prepareStatement("SELECT usuario, contrasena, email, activo, rol, persona FROM usuario");
		        rs = stmt.executeQuery();

		        while (rs.next()) {
		            String username = rs.getString("usuario");
		            String password = rs.getString("contrasena");
		            String email = rs.getString("email");
		            boolean activo = rs.getBoolean("activo");
		            int rolId = rs.getInt("rol");
		            String dni = rs.getString("persona");

		            Rol rol = buscarRolPorId(conn,rolId);
		            Persona persona = buscarPersonaPorDni(conn,dni);

		            Usuario usuario = new Usuario(username, password, email, activo, rol, persona);
		            usuarios.add(usuario);
		        }

		    } catch (SQLException e) {
		        System.out.println("❌ Error al obtener usuarios: " + e.getMessage());
		        e.printStackTrace();
		    } finally {
		        ConnectionManager.disconnect();
		    }

		    return usuarios;
		}

	@Override
	public void remove(Usuario Usuario) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'remove'");
	}
	
	private Rol buscarRolPorId(Connection conn, int id) {
	    Rol rol = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        stmt = conn.prepareStatement("SELECT id, nombre, descripcion FROM roles WHERE id = ?");
	        stmt.setInt(1, id);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            rol = new Rol(
	                rs.getInt("id"),
	                rs.getString("nombre"),
	                rs.getString("descripcion")
	            );
	        }
	    } catch (SQLException e) {
	        System.out.println("❌ Error al buscar rol: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	        } catch (SQLException e) {
	            System.out.println("❌ Error cerrando recursos: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }

	    return rol;
	}
	
	private Persona buscarPersonaPorDni(Connection conn, String dni) {
	    Persona persona = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        stmt = conn.prepareStatement("SELECT dni, nombre, apellido FROM persona WHERE dni = ?");
	        stmt.setString(1, dni);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            persona = new Persona(
	                rs.getString("nombre"),
	                rs.getString("apellido"),
	                rs.getString("dni")
	            );
	        }
	    } catch (SQLException e) {
	        System.out.println("❌ Error al buscar persona: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	        } catch (SQLException e) {
	            System.out.println("❌ Error cerrando recursos: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }

	    return persona;
	}
}
