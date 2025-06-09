package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;

public class ConvenioDTO {
	
	private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private byte[] archivo; // Contenido binario del PDF o DOC
    private String nombreArchivo;
    private PropuestaDTO propuesta;
    
    
    public ConvenioDTO(LocalDate fechaInicio, LocalDate fechaFin, byte[] archivo,
                       String nombreArchivo, String tipoArchivo, PropuestaDTO propuesta) {
        
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.archivo = archivo;
        this.nombreArchivo = nombreArchivo;
        this.propuesta = propuesta ; 

    }
    

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    
    public PropuestaDTO getPropuesta() {
    	return propuesta ;
		
	}
}
