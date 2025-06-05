package ar.edu.unrn.seminario.exception;

import java.sql.SQLException;

public class ConexionFallidaException extends SQLException {
    public ConexionFallidaException(String mensaje) {
        super(mensaje);
    }

    public ConexionFallidaException() {
        super("Conexion fallida con la base de datos");
    }
}


