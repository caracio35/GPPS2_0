package ar.edu.unrn.seminario.modelo;
import java.time.LocalDateTime;

public class Entrega {
    private int id;
    private int actividadId;
    private LocalDateTime fechaEntrega;
    private byte[] archivoEntregado;
    private String nombreArchivo;

    // Constructor vacío
    public Entrega() {}

    // Constructor con campos
    public Entrega(int actividadId, LocalDateTime fechaEntrega, byte[] archivoEntregado, String nombreArchivo) {
        this.actividadId = actividadId;
        this.fechaEntrega = fechaEntrega;
        this.archivoEntregado = archivoEntregado;
        this.nombreArchivo = nombreArchivo;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getActividadId() { return actividadId; }
    public void setActividadId(int actividadId) { this.actividadId = actividadId; }

    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public byte[] getArchivoEntregado() { return archivoEntregado; }
    public void setArchivoEntregado(byte[] archivoEntregado) { this.archivoEntregado = archivoEntregado; }

    public String getNombreArchivo() { return nombreArchivo; }
    public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }
}
