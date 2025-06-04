package ar.edu.unrn.seminario.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Rol;

public class RolDAOJDBC implements RolDao {

	@Override
	public void create(Rol rol) {
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = ConnectionManager.getConnection();
			statement = conn.prepareStatement(
					"INSERT INTO roles (nombre, descripcion) VALUES (?, ?)");
			statement.setString(1, rol.getNombre());
			statement.setString(2, rol.getDescripcion());
			statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error al insertar rol: " + e.getMessage());
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}
	}

	@Override
	public void update(Rol rol) {
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
	public void remove(Rol rol) {
		// TODO Auto-generated method stub

	}

	@Override
	public Rol find(Integer id) {
		Rol rol = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"SELECT id, nombre, descripcion FROM roles WHERE id = ?");
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				rol = new Rol(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"));
			}
		} catch (SQLException e) {
			System.out.println("Error al procesar consulta");
			// TODO: disparar Exception propia
		} catch (Exception e) {
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}
		return rol;
	}

	@Override
	public List<Rol> findAll() {
		List<Rol> listado = new ArrayList<Rol>();
		Statement sentencia = null;
		ResultSet resultado = null;
		try {
			sentencia = ConnectionManager.getConnection().createStatement();
			resultado = sentencia.executeQuery("SELECT id, nombre, descripcion FROM roles");

			while (resultado.next()) {
				Rol rol = new Rol(
						resultado.getInt("id"),
						resultado.getString("nombre"),
						resultado.getString("descripcion"));
				listado.add(rol);
			}
		} catch (SQLException e) {
			System.out.println("Error de mySql\n" + e.toString());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.disconnect();
		}

		return listado;
	}

}
