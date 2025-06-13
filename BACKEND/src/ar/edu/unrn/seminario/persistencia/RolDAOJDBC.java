package ar.edu.unrn.seminario.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.exception.DuplicadaException;
import ar.edu.unrn.seminario.modelo.Rol;

public class RolDAOJDBC implements RolDao {

	@Override
	public void create(Rol rol) throws DuplicadaException, ConexionFallidaException {
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = ConnectionManager.getConnection();
			statement = conn.prepareStatement(
					"INSERT INTO roles (nombre, descripcion) VALUES (?, ?)");
			statement.setString(1, rol.getNombre());
			statement.setString(2, rol.getDescripcion());
			statement.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new DuplicadaException("El nombre del rol ya existe.");
		} catch (SQLException e) {
			throw new ConexionFallidaException("Error al insertar rol: " + e.getMessage());
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
	public Rol find(Integer id) throws ConexionFallidaException {
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
			throw new ConexionFallidaException("Error al buscar rol: " + e.getMessage());
		} finally {
			ConnectionManager.disconnect();
		}
		return rol;
	}

	@Override
	public List<Rol> findAll() throws ConexionFallidaException {
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
		}catch (SQLException e) {
			throw new ConexionFallidaException("Error al obtener roles: " + e.getMessage());
			
		} finally {
			ConnectionManager.disconnect();
		}

		return listado;
	}

}
