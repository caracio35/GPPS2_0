package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;

public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;

	public VentanaPrincipal(IApi api, UsuarioDTO usuario) {
		setTitle("Ventana Principal - " + usuario.getUsername());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 300);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		RolDTO rolDto = api.obtenerRolPorCodigo(usuario.getRol());
		String nombreRol = rolDto.getNombre().toLowerCase();

		inicializarMenuPorRol(menuBar, nombreRol, api, usuario);
		
		mostrarMensajeBienvenida(usuario);
	}

	private void inicializarMenuPorRol(JMenuBar menuBar, String rol, IApi api, UsuarioDTO usuario) {
		
		if (rol.equals("institución")) {
			menuBar.add(crearBotonMenu("Crear Propuesta", () -> {
				CargarPropuesta crear = new CargarPropuesta(this, api, usuario);
				crear.setVisible(true);
			}));
			menuBar.add(crearBotonMenu("Ver Estado de Propuestas", () -> {
				//ver propuestas suvidas por alumnos
			}));
		} else if (rol.equals("alumno")) {
			menuBar.add(crearBotonMenu("Ver Propuestas", () -> {
				VerPropuestas ventanaVerPropuesta = new VerPropuestas(this);
				ventanaVerPropuesta.setVisible(true);
			}));
			menuBar.add(crearBotonMenu("", () ->
					JOptionPane.showMessageDialog(this, "Aquí iría la lógica para subir informe final.")
			));
		} else if (rol.equals("tutor")) {
			menuBar.add(crearBotonMenu("", () -> {}));
			menuBar.add(crearBotonMenu("Evaluar Alumno", () -> {}));
		} else if (rol.equals("profesor")) {
			menuBar.add(crearBotonMenu("evaluar ", () -> {}));
			
		} else if (rol.equals("director de carrera")) {
			menuBar.add(crearBotonMenu("Asignar Tutor/Profesor", () -> {
				new AsignarTutor(api).setVisible(true);
			}));
			menuBar.add(crearBotonMenu("Crear Usuario", () -> {
				AltaUsuario ventanaAltaUsuario = new AltaUsuario(api);
				ventanaAltaUsuario.setVisible(true);
				
			}));
			menuBar.add(crearBotonMenu("Crear Convenio", () -> {
				ListadoParaConvenio ventanaListado = new ListadoParaConvenio(api);
				ventanaListado.setVisible(true);
			}));
			
		}
	}

	

	private void mostrarMensajeBienvenida(UsuarioDTO usuario) {
		String nombre = "(usuario)";
		if (usuario.getPersona() != null) {
			nombre = usuario.getPersona().getNombre();
		}
		JLabel bienvenida = new JLabel("Bienvenido/a " + nombre, SwingConstants.CENTER);
		bienvenida.setFont(new Font("SansSerif", Font.BOLD, 20));
		contentPane.add(bienvenida, BorderLayout.CENTER);
	}

	private Component crearBotonMenu(String texto, Runnable accion) {
		JButton boton = new JButton(texto);
		boton.setFocusPainted(false);
		boton.setBorderPainted(false);
		boton.setContentAreaFilled(false);
		boton.setOpaque(false);
		boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		boton.addActionListener(e -> accion.run());
		return boton;
	}
}
