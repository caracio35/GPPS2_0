package ar.edu.unrn.seminario.modelo;

public class Actividad {
    private String nombre;
    private int horas;



    public Actividad(String nombre, int horas) {
        this.nombre = nombre;
        this.horas = horas;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setHoras(int horas) {
        this.horas = horas;
    }
    public int getHoras() {
        return horas;
    }
    @Override
    public String toString() {
        return "Actividad{" + "nombre=" + nombre + ", horas=" + horas + '}';
    }

}
