package ar.edu.unrn.seminario.exception;

public class ExcepcionAplicacion extends Exception {
    public ExcepcionAplicacion(String mensaje) {
        super(mensaje);
    }

    public ExcepcionAplicacion(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
