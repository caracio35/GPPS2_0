package ar.edu.unrn.seminario.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Persona;
import ar.edu.unrn.seminario.modelo.Usuario;

public class UsuarioDAOJDBC implements UsuarioDao {

	@Override
	public void create(Usuario usuario) {
		try {
			// 1. Crear la persona primero
			Persona persona = usuario.getPersona();
			PersonaDAOJDBC personaDAO = new PersonaDAOJDBC();
			personaDAO.create(persona);

			// 2. Crear el usuario y asociar el dni de la persona
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"INSERT INTO usuarios(usuario, contrasena, email, activo, rol, persona) VALUES (?, ?, ?, ?, ?, ?)");

			statement.setString(1, usuario.getUsuario());
			statement.setString(2, usuario.getContrasena());
			statement.setString(3, usuario.getEmail());
			statement.setBoolean(4, usuario.isActivo());
			statement.setInt(5, usuario.getRol().getCodigo()); // o getCodigo() según tu modelo
			statement.setString(6, persona.getDni());

			int cantidad = statement.executeUpdate();
			if (cantidad <= 0) {
				System.out.println("Error al insertar usuario");
				// TODO: disparar Exception propia
			}
		} catch (SQLException e) {
			System.out.println("Error al procesar consulta: " + e.getMessage());
			// TODO: disparar Exception propia
		} catch (Exception e) {
			System.out.println("Error al insertar un usuario: " + e.getMessage());
			// TODO: disparar Exception propia
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
		List<Usuario> usuarios = new java.util.ArrayList<>();
		Connection conn = null;
		PreparedStatement statement = null;
		java.sql.ResultSet rs = null;

		try {
			conn = ConnectionManager.getConnection();
			statement = conn.prepareStatement("SELECT usuario, contrasena, email, activo, rol, persona FROM usuarios");
			rs = statement.executeQuery();

			RolDAOJDBC rolDAO = new RolDAOJDBC();
			PersonaDAOJDBC personaDAO = new PersonaDAOJDBC();

			while (rs.next()) {
				String username = rs.getString("usuario");
				String contrasena = rs.getString("contrasena");
				String email = rs.getString("email");
				boolean activo = rs.getBoolean("activo");
				int rolId = rs.getInt("rol");
				String personaDni = rs.getString("persona");

				// Buscar el rol y la persona usando sus DAOs
				ar.edu.unrn.seminario.modelo.Rol rol = rolDAO.find(rolId);
				ar.edu.unrn.seminario.modelo.Persona persona = personaDAO.find(personaDni);

				Usuario usuario = new Usuario(username, contrasena, email, activo, rol, persona);
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			System.out.println("Error al obtener usuarios: " + e.getMessage());
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
}
