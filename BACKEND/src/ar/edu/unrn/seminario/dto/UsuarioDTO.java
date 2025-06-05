package ar.edu.unrn.seminario.dto;

public class UsuarioDTO {
	private String username;
	private String password;
	private PersonaDTO persona;
	private String email;
	private int rol;
	private boolean activo;
	private String estado;

	public UsuarioDTO(String username, String password, PersonaDTO persona, String email, int rol, boolean activo,
			String estado) {
		super();
		this.username = username;
		this.password = password;
		this.persona = persona ;
		this.email = email;
		this.rol = rol;
		this.activo = activo;
		this.estado = estado;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getRol() {
		return rol;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public PersonaDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}

	@Override
	public String toString() {
		return "UsuarioDTO{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				", persona=" + persona +
				", email='" + email + '\'' +
				", rol='" + rol + '\'' +
				", activo=" + activo +
				", estado='" + estado + '\'' +
				'}';
	}
}
