package ar.edu.unrn.seminario.modelo;

public class Usuario {
	private String usuario;
	private String contrasena;
	private Persona persona;
	private String email;
	private Rol rol;
	private boolean activo;

	public Usuario(String usuario, String contrasena, String email, boolean activo2, Rol rol, Persona persona) {

		this.usuario = usuario;
		this.contrasena = contrasena;
		this.persona = persona;
		this.email = email;
		this.rol = rol;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public boolean isActivo() {
		return activo;
	}

	public String obtenerEstado() {
		return isActivo() ? "ACTIVO" : "INACTIVO";
	}

	public void activar() {
		if (!isActivo())
			this.activo = true;
	}

	public void desactivar() {
		if (isActivo())
			this.activo = false;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@Override
	public String toString() {
		return "Usuario{" +
				"usuario='" + usuario + '\'' +
				", contrasena='" + contrasena + '\'' +
				", persona=" + persona +
				", email='" + email + '\'' +
				", rol=" + rol +
				", activo=" + activo +
				'}';
	}

}
