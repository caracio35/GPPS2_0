package ar.edu.unrn.seminario.persistencia;

import ar.edu.unrn.seminario.exception.ExcepcionPersistencia;
import ar.edu.unrn.seminario.modelo.Entrega;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class EntregaDAOJDBC implements EntregaDAO{

	@Override
    public void guardar(Entrega entrega) throws ExcepcionPersistencia {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();

            String sql = "INSERT INTO entrega (actividad_id, fecha_entrega, archivo_entregado, nombre_archivo) " +
                         "VALUES (?, ?, ?, ?)";

            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, entrega.getActividadId());
            stmt.setTimestamp(2, Timestamp.valueOf(entrega.getFechaEntrega()));
            stmt.setBytes(3, entrega.getArchivoEntregado());
            stmt.setString(4, entrega.getNombreArchivo());

            stmt.executeUpdate();


        } catch (SQLException e) {
            throw new ExcepcionPersistencia("Error al guardar entrega: " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
    }

	@Override
    public Entrega buscarPorId(int id) throws ExcepcionPersistencia {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Entrega entrega = null;

        try {
            conn = ConnectionManager.getConnection();

            String sql = "SELECT * FROM entrega WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            rs = stmt.executeQuery();
            if (rs.next()) {
                entrega = new Entrega();
                //entrega.setId(rs.getInt("id"));
                entrega.setActividadId(rs.getInt("actividad_id"));

                Timestamp timestamp = rs.getTimestamp("fecha_entrega");
                entrega.setFechaEntrega(timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

                entrega.setArchivoEntregado(rs.getBytes("archivo_entregado"));
                entrega.setNombreArchivo(rs.getString("nombre_archivo"));
            }

        }  catch (SQLException e) {
            throw new ExcepcionPersistencia("Error al buscar entrega: " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }

        return entrega;
    }
    
    
}
