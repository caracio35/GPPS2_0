package ar.edu.unrn.seminario.dto;

public class PropuestaDTO {
    private String titulo;
    private String descripcion;
    private String areaDeInteres;
    private String objetivo;
    private String comentarios;
    private int aceptados;
    private UsuarioDTO creador;
    private UsuarioDTO alumno;
    private UsuarioDTO profesor;
    private UsuarioDTO tutor;

    public PropuestaDTO(String titulo, String descripcion, String areaDeInteres,
                        String objetivo, String comentarios, int aceptados,
                        UsuarioDTO creador, UsuarioDTO alumno,
                        UsuarioDTO profesor, UsuarioDTO tutor) {
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
    }

    public PropuestaDTO(String titulo, String descripcion, String areaDeInteres,
                        String objetivo, String comentarios, int aceptados,
                        UsuarioDTO creador) {
        this(titulo, descripcion, areaDeInteres, objetivo, comentarios,
                aceptados, creador, null, null, null);
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