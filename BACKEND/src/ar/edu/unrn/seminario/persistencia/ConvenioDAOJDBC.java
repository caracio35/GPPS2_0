package ar.edu.unrn.seminario.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ar.edu.unrn.seminario.exception.ExcepcionAplicacion;
import ar.edu.unrn.seminario.exception.ExcepcionPersistencia;
import ar.edu.unrn.seminario.modelo.Convenio;

public class ConvenioDAOJDBC implements ConvenioDAO{

	@Override
	public void create(Convenio convenio) throws ExcepcionPersistencia, ExcepcionAplicacion {
	    Connection conn = null;
	    PreparedStatement stmt = null;

	    try {
	        conn = ConnectionManager.getConnection();

	        Long idPropuesta = obtenerIdPropuestaPorTitulo(conn, convenio.getPropuesta().getTitulo());
	        if (idPropuesta == null) {
	            throw new RuntimeException("Propuesta no encontrada con título: " + convenio.getPropuesta().getTitulo());
	        }

	        stmt = conn.prepareStatement(
	            "INSERT INTO convenio (fecha_inicio, fecha_fin, archivo, nombre_archivo, propuesta_id) VALUES (?, ?, ?, ?, ?)"
	        );

	        stmt.setDate(1, java.sql.Date.valueOf(convenio.getFechaInicio()));
	        stmt.setDate(2, java.sql.Date.valueOf(convenio.getFechaFin()));
	        stmt.setBytes(3, convenio.getArchivo());
	        stmt.setString(4, convenio.getNombreArchivo());
	        stmt.setLong(5, idPropuesta);

	        int cantidad = stmt.executeUpdate();
	        if (cantidad <= 0) {
	            System.out.println("❌ Error al insertar el convenio.");
	        }

	    }  catch (SQLException e) {
	        throw new ExcepcionPersistencia("Error al procesar la consulta SQL al crear el convenio: " + e.getMessage());
	    } catch (Exception e) {
	        throw new ExcepcionAplicacion("Error inesperado al crear convenio: " + e.getMessage(), e);
	    } finally {
	        ConnectionManager.disconnect();
	    }
	}

	private Long obtenerIdPropuestaPorTitulo(Connection conn, String titulo) throws SQLException {
	    Long idPropuesta = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    stmt = conn.prepareStatement("SELECT id FROM propuesta WHERE titulo = ?");
	    stmt.setString(1, titulo);
	    rs = stmt.executeQuery();

	    if (rs.next()) {
	        idPropuesta = rs.getLong("id");
	    } else {
	        System.out.println("⚠️ No se encontró ninguna propuesta con el título: " + titulo);
	    }

	    // stmt y rs se cierran automáticamente si usás try-with-resources (opcional)
	    if (rs != null) rs.close();
	    if (stmt != null) stmt.close();

	    return idPropuesta;
	}
}
