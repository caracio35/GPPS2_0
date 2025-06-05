package ar.edu.unrn.seminario.persistencia;

import ar.edu.unrn.seminario.modelo.Persona;

public class PersonaDAOJDBC {

    public void create(Persona persona) {
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
        } catch (java.sql.SQLException e) {
            System.out.println("Error al insertar persona: " + e.getMessage());
            // TODO: disparar Exception propia
            e.printStackTrace();
        } finally {
            ConnectionManager.disconnect();
        }
    }

    public Persona find(String personaDni) {
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
        } catch (java.sql.SQLException e) {
            System.out.println("Error al buscar persona: " + e.getMessage());
            // TODO: disparar Exception propia
            e.printStackTrace();
        } finally {
            ConnectionManager.disconnect();
        }
        return persona;
    }

}
