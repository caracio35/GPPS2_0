package ar.edu.unrn.seminario.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ar.edu.unrn.seminario.exception.ConexionFallidaException;

public class ConnectionManager {
	private static String DRIVER = "com.mysql.jdbc.Driver";
	private static String URL_DB = "jdbc:mysql://localhost:3306/";
	protected static String DB = "gpps2_0";
	protected static String user = "root";
	protected static String pass = "";
	protected static Connection conn = null;

	public static void connect() throws ConexionFallidaException {
		try {
			conn = DriverManager.getConnection(URL_DB + DB, user, pass);
		} catch (SQLException sqlEx) {
			throw new ConexionFallidaException("No se pudo conectar: " + sqlEx.getMessage());
		}
	}

	public static void disconnect() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void reconnect() throws ConexionFallidaException {
		disconnect();
		connect();
	}

	public static Connection getConnection() throws ConexionFallidaException {
		try {
			if (conn == null || conn.isClosed()) {
				connect(); 
			}
		} catch (SQLException e) {
			throw new ConexionFallidaException("Error al verificar el estado de la conexión: " + e.getMessage());
		}
		return conn;
	}
	}
