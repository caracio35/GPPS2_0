package ar.edu.unrn.seminario.exception;

public class ExcepcionPersistencia extends Exception {
    public ExcepcionPersistencia(String mensaje) {
        super(mensaje);
    }

    public ExcepcionPersistencia() {
        super("Error en la capa de persistencia.");
    }
}
