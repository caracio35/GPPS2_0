package ar.edu.unrn.seminario.persistencia;

import java.sql.SQLException;

import ar.edu.unrn.seminario.exception.ExcepcionPersistencia;
import ar.edu.unrn.seminario.modelo.Persona;

public class PersonaDAOJDBC {

    public void create(Persona persona) throws ExcepcionPersistencia {
        java.sql.Connection conn = null;
        java.sql.PreparedStatement statement = null;
        try {
            conn = ConnectionManager.getConnection();
            statement = conn.prepareStatement(
                    "INSERT INTO persona (dni, nombre, apellido) VALUES (?, ?, ?)");
            statement.setString(1, persona.getDni());
            statement.setString(2, persona.getNombre());
            statement.setString(3, persona.getApellido());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ExcepcionPersistencia("Error al insertar persona: " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
    }

    public Persona find(String personaDni) throws ExcepcionPersistencia {
        Persona persona = null;
        java.sql.Connection conn = null;
        java.sql.PreparedStatement statement = null;
        java.sql.ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            statement = conn.prepareStatement(
                    "SELECT dni, nombre, apellido FROM persona WHERE dni = ?");
            statement.setString(1, personaDni);
            rs = statement.executeQuery();
            if (rs.next()) {
                persona = new Persona(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"));
            }
        } catch (SQLException e) {
            throw new ExcepcionPersistencia("Error al buscar persona: " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
        return persona;
    }

}
