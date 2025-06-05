package ar.edu.unrn.seminario.modelo;

public class Propuesta {
    private String titulo;
    private String descripcion;
    private String areaDeInteres;
    private String objetivo;
    private String comentarios;
    private int aceptados;
    private Usuario creador;
    private Usuario alumno;
    private Usuario profesor;
    private Usuario tutor;


    public Propuesta(String titulo, String descripcion, String areaDeInteres, String objetivo, String comentarios, int aceptados, Usuario creador) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.areaDeInteres = areaDeInteres;
        this.objetivo = objetivo;
        this.comentarios = comentarios;
        this.aceptados = aceptados;
        this.creador = creador;
        this.alumno = null;
        this.profesor = null;
        this.tutor = null;
    }

    public Propuesta(String titulo, String descripcion, String areaDeInteres, String objetivo, String comentarios, int aceptados, Usuario creador, Usuario alumno, Usuario profesor, Usuario tutor) {
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAreaDeInteres() {
        return areaDeInteres;
    }

    public void setAreaDeInteres(String areaDeInteres) {
        this.areaDeInteres = areaDeInteres;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public int getAceptados() {
        return aceptados;
    }

    public void setAceptados(int aceptados) {
        this.aceptados = aceptados;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public Usuario getAlumno() {
        return alumno;
    }

    public void setAlumno(Usuario alumno) {
        this.alumno = alumno;
    }

    public Usuario getProfesor() {
        return profesor;
    }

    public void setProfesor(Usuario profesor) {
        this.profesor = profesor;
    }

    public Usuario getTutor() {
        return tutor;
    }

    public void setTutor(Usuario tutor) {
        this.tutor = tutor;
    }

    @Override
    public String toString() {
        return "Propuesta{" + "titulo=" + titulo + ", descripcion=" + descripcion + ", areaDeInteres=" + areaDeInteres + ", obgetivo=" + objetivo + ", comentarios=" + comentarios + ", aceptados=" + aceptados + ", creador=" + creador + ", alumno=" + alumno + ", profesor=" + profesor + ", tutor=" + tutor + '}';
    }

}
