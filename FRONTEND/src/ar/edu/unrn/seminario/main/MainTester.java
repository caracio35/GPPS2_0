package ar.edu.unrn.seminario.main;

import java.util.List;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.PersonaDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;

public class MainTester {
	
	public static void main(String[] args) {
		
	IApi api = new PersistenceApi();
	
	//prueva que tae todo los roles de la base de datos 
	List<RolDTO> roles = api.obtenerRoles();
	for (RolDTO rol : roles) {
        System.out.println("ID: " + rol.getCodigo());
        System.out.println("Nombre: " + rol.getNombre());
        System.out.println("Descripción: " + rol.getDescripcion());
        System.out.println("-----------------------------");
    }
	
	//prueva de crear un usuario
	PersonaDTO persona = new PersonaDTO("lucas", "juanfer","40707082" );
	UsuarioDTO usuario = new UsuarioDTO("juanfer", "10254", persona, "juan@unr", 1, true ,null);
	
	api.registrarUsuario(usuario);
	//prueva que traiga todos los usuarios de la base de datos
	
	List<UsuarioDTO> usuarioDt = api.obtenerUsuarios();
	 for (UsuarioDTO usuario1 : usuarioDt) {
		 
         System.out.println("🔹 Usuario: " + usuario1.getUsername());
         System.out.println("🔐 Contraseña: " + usuario1.getPassword());
         System.out.println("📧 Email: " + usuario1.getEmail());
         System.out.println("🧑 Rol: " + usuario1.getRol());
         System.out.println("✅ Activo: " + usuario1.isActivo());
         System.out.println("📌 Estado: " + usuario1.getEstado());

         PersonaDTO persona1 = usuario1.getPersona();
         if (persona1 != null) {
             System.out.println("👤 Nombre: " + persona1.getNombre() + " " + persona1.getApellido());
             System.out.println("🆔 DNI: " + persona1.getDni());
         }

         System.out.println("-----------------------------");
     }
 }
	
}

