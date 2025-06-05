package ar.edu.unrn.seminario.exception;

import java.sql.SQLException;

public class DatosNoEncontradosException extends SQLException {

    public DatosNoEncontradosException(String mensaje) {
        super(mensaje);
    }

    public DatosNoEncontradosException() {
        super("Datos no encontrados en la base de datos");
    }
}
