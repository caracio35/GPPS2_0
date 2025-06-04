package ar.edu.unrn.seminario.modelo;

public class Rol {
	private Integer codigo;
	private String nombre;
	private String descripcion;

	public Rol(Integer codigo, String nombre, String descripcion) {

		this.codigo = codigo;
		this.nombre = nombre;
		this.descripcion = descripcion;

	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String toString() {

		return "Rol{" +
				"codigo=" + codigo +
				", nombre='" + nombre + '\'' +
				", descripcion='" + descripcion + '\'' +
				'}';
	}
}
