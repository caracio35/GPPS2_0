package ar.edu.unrn.seminario.dto;

import java.util.List;

public class PropuestaDTO {
    private String titulo;
    private String descripcion;
    private String areaDeInteres;
    private String objetivo;
    private String comentarios;
    private int aceptados;  //-1 Rechazada , 1 aprobada , 0 desaprobada
    private UsuarioDTO creador;
    private UsuarioDTO alumno;
    private UsuarioDTO profesor;
    private UsuarioDTO tutor;
    private List<ActividadDTO> actividades;

    public PropuestaDTO(String titulo, String descripcion, String areaDeInteres,
                        String objetivo, String comentarios, int aceptados,
                        UsuarioDTO creador, UsuarioDTO alumno,
                        UsuarioDTO profesor, UsuarioDTO tutor, List<ActividadDTO> actividades) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.areaDeInteres = areaDeInteres;
        this.objetivo = objetivo;
        this.comentarios = comentarios;
        this.aceptados = aceptados;
        this.creador = creador;
        this.alumno = alumno;
        this.profesor = profesor;
        this.tutor = tutor;
        this.actividades = actividades;
    }

    public PropuestaDTO(String titulo, String descripcion, String areaDeInteres,
                        String objetivo, String comentarios, int aceptados,
                        UsuarioDTO creador,List<ActividadDTO> actividades) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.areaDeInteres = areaDeInteres;
        this.objetivo = objetivo;
        this.comentarios = comentarios;
        this.aceptados = aceptados;
        this.creador = creador;
        this.actividades = actividades;
        this.alumno = null;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getAreaDeInteres() {
        return areaDeInteres;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public String getComentarios() {
        return comentarios;
    }

    public int getAceptados() {
        return aceptados;
    }

    public UsuarioDTO getCreador() {
        return creador;
    }

    public UsuarioDTO getAlumno() {
        return alumno;
    }

    public UsuarioDTO getProfesor() {
        return profesor;
    }

    public UsuarioDTO getTutor() {
        return tutor;
    }
    public List<ActividadDTO> getActividades(){
    	return actividades ;
    }

    @Override
    public String toString() {
        return "PropuestaDTO{" +
                "titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", areaDeInteres='" + areaDeInteres + '\'' +
                ", objetivo='" + objetivo + '\'' +
                ", comentarios='" + comentarios + '\'' +
                ", aceptados=" + aceptados +
                ", creador=" + creador +
                ", alumno=" + alumno +
                ", profesor=" + profesor +
                ", tutor=" + tutor +
                '}';
    }
}