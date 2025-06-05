package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;

    public class ListadoUsuario extends JFrame {

        private JComboBox<UsuarioDTO> comboUsuarios;
        private JButton btnSeleccionar;
        

        public ListadoUsuario(IApi api) {
            setTitle("Seleccionar Usuario");
            setSize(400, 150);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());

            comboUsuarios = new JComboBox<>();
            cargarUsuarios(api);

            btnSeleccionar = new JButton("Seleccionar Usuario");
            btnSeleccionar.addActionListener(e -> {
                UsuarioDTO seleccionado = (UsuarioDTO) comboUsuarios.getSelectedItem();
                if (seleccionado != null) {
                   ingresarSegunRol(api);
                }
                dispose();
            });

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.add(comboUsuarios, BorderLayout.CENTER);
            panel.add(btnSeleccionar, BorderLayout.SOUTH);

            add(panel, BorderLayout.CENTER);

            setVisible(true);
        }

        private void cargarUsuarios(IApi api) {
            List<UsuarioDTO> usuarios = api.obtenerUsuarios();

            for (UsuarioDTO usuario : usuarios) {
                RolDTO rol = api.obtenerRolPorCodigo(usuario.getRol());

                String nombre = (usuario.getPersona() != null)
                        ? usuario.getPersona().getNombre()
                        : "(sin persona)";

                // Modificamos el toString dinámicamente (solo para mostrar en combo)
                UsuarioDTO copia = new UsuarioDTO(
                        usuario.getUsername(),
                        usuario.getPassword(),
                        usuario.getPersona(),
                        usuario.getEmail(),
                        rol.getCodigo(),
                        usuario.isActivo(),
                        usuario.getEstado()
                        
                ) {
                    @Override
                    public String toString() {
                        return getUsername() + " - " + nombre + " - " + getEmail() + " - " + rol.getNombre();
                    }
                };

                comboUsuarios.addItem(copia);
            }
        }
        private void ingresarSegunRol(IApi api ) {
    	    UsuarioDTO usuario = (UsuarioDTO) comboUsuarios.getSelectedItem();

    	    if (usuario == null) {
    	        JOptionPane.showMessageDialog(this, "Por favor, seleccioná un usuario.");
    	        return;
    	    }

    	    // Obtener el nombre del rol desde la base usando el codigo de rol
    	    RolDTO rolDTO = api.obtenerRolPorCodigo(usuario.getRol()); 
    	    if (rolDTO == null) {
    	        JOptionPane.showMessageDialog(this, "❌ Rol no encontrado para el usuario.");
    	        return;
    	    }

    	    String rol = rolDTO.getNombre().toLowerCase();

    	    switch (rol) {
    	        case "institución":
    	        	VentanaPrincipal ventana = new VentanaPrincipal(api, usuario);
    	        	ventana.setVisible(true);
    	            break;
    	        case "alumno":
    	        	VentanaPrincipal ventana1 = new VentanaPrincipal(api, usuario);
    	        	ventana1.setVisible(true);
    	            break;
    	        case "tutor":
    	        	VentanaPrincipal ventana11 = new VentanaPrincipal(api, usuario);
    	        	ventana11.setVisible(true);
    	            break;
    	        case "profesor":
    	        	VentanaPrincipal ventanaProfesor= new VentanaPrincipal(api, usuario);
    	        	ventanaProfesor.setVisible(true);
    	           
    	            break;
    	        case "administrador":
    	        	VentanaPrincipal ventana111 = new VentanaPrincipal(api, usuario);
    	        	ventana111.setVisible(true);
    	        	break;
    	        	
    	        case "director de carrera":
    	        	VentanaPrincipal ventanaDirector = new VentanaPrincipal(api, usuario);
    	        	ventanaDirector.setVisible(true);
    	            break;
    	        default:
    	            JOptionPane.showMessageDialog(this, "Rol desconocido: " + rol);
    	            return;
    	    }

    	    dispose();
    	}
    }
