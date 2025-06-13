package ar.edu.unrn.seminario.persistencia;

import ar.edu.unrn.seminario.exception.ExcepcionPersistencia;
import ar.edu.unrn.seminario.modelo.Actividad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActividadDAOJDBC implements ActividadDAO{
    public void create(Actividad actividad, String nombreDePropuesta) throws ExcepcionPersistencia {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();

            // Primero obtenemos el ID de la propuesta
            statement = conn.prepareStatement(
                    "SELECT id FROM propuesta WHERE titulo = ?");
            statement.setString(1, nombreDePropuesta);
            rs = statement.executeQuery();

            if (rs.next()) {
                int propuestaId = rs.getInt("id");

                // Ahora creamos la actividad
                statement = conn.prepareStatement(
                        "INSERT INTO actividad (nombre_actividad, horas) " +
                                "VALUES (?, ?, ?)");

                statement.setString(1, actividad.getNombre());
                statement.setInt(2, actividad.getHoras());


                statement.executeUpdate();
            } else {
                throw new RuntimeException("No se encontró la propuesta con título: " + nombreDePropuesta);
            }

        }catch (SQLException e) {
            throw new ExcepcionPersistencia("Error al crear actividad: " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void update(Actividad actividad) {

    }

    @Override
    public void remove(Actividad actividad) {

    }

    public Actividad find(int id) throws ExcepcionPersistencia {
            Actividad actividad = null;
            Connection conn = null;
            PreparedStatement statement = null;
            ResultSet rs = null;

            try {
                conn = ConnectionManager.getConnection();
                statement = conn.prepareStatement(
                        "SELECT id, nombre_actividad, horas, propuesta_id " +
                                "FROM actividad " +
                                "WHERE id = ?");

                statement.setInt(1, id);
                rs = statement.executeQuery();

                if (rs.next()) {
                    actividad = new Actividad(
                            rs.getString("nombre_actividad"),
                            rs.getInt("horas")
                    );
                }

            } catch (SQLException e) {
                throw new ExcepcionPersistencia("Error al buscar actividad: " + e.getMessage());
            } finally {
                ConnectionManager.disconnect();
            }

            return actividad;
        }

    @Override
    public List<Actividad> findAllPorPropuesta(String nombrePropuesta) throws ExcepcionPersistencia {
        List<Actividad> actividades = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();
            statement = conn.prepareStatement(
                    "SELECT a.id, a.nombre_actividad, a.horas, a.propuesta_id " +
                            "FROM actividad a " +
                            "INNER JOIN propuesta p ON a.propuesta_id = p.id " +
                            "WHERE p.titulo = ?");

            statement.setString(1, nombrePropuesta);
            rs = statement.executeQuery();

            while (rs.next()) {
                Actividad actividad = new Actividad(
                        rs.getString("nombre_actividad"),
                        rs.getInt("horas")
                );
                actividades.add(actividad);
            }

        } catch (SQLException e) {
            throw new ExcepcionPersistencia("Error al buscar actividades por propuesta: " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }

        return actividades;
    }

	@Override
	public int obtenerIdPorNombre(String nombre) throws ExcepcionPersistencia {
		 int id = -1;
		    Connection conn = null;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;

		    try {
		        conn = ConnectionManager.getConnection();
		        String sql = "SELECT id FROM actividad WHERE nombre_actividad = ?";
		        stmt = conn.prepareStatement(sql);
		        stmt.setString(1, nombre);
		        rs = stmt.executeQuery();

		        if (rs.next()) {
		            id = rs.getInt("id");
		        }
		    } catch (SQLException e) {
		        throw new ExcepcionPersistencia("Error al buscar ID de actividad: " + e.getMessage());
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (stmt != null) stmt.close();
		            if (conn != null) conn.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    return id;
		}
}



