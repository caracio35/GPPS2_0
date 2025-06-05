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

		RolDTO rolDto = api.obtenerRolPorCodigo(usuario.getRol());
		String nombreRol = rolDto.getNombre().toLowerCase();

		// Crear barra de menú
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// PRIMERO: agregar botones según el rol (aparecen a la izquierda)
		if (nombreRol.equals("institución")) {
			menuBar.add(crearBotonMenu("Crear Propuesta"));
			menuBar.add(crearBotonMenu("Ver Estado de Propuestas"));
		} else if (nombreRol.equals("alumno")) {
			menuBar.add(crearBotonMenu("Ver Propuestas"));
			menuBar.add(crearBotonMenu("Subir Informe Final"));
		} else if (nombreRol.equals("tutor")) {
			menuBar.add(crearBotonMenu("Revisar Propuestas"));
			menuBar.add(crearBotonMenu("Evaluar Alumno"));
		} else if (nombreRol.equals("profesor")) {
			menuBar.add(crearBotonMenu("Asignar Propuesta"));
		} else if (nombreRol.equals("directorcarrera") || nombreRol.equals("director de carrera")) {
			menuBar.add(crearBotonMenu("Validar Propuestas"));
			menuBar.add(crearBotonMenu("Ver Informes Finales"));
		}

		// LUEGO: agregar menús comunes (aparecen después de los botones)
		if (!nombreRol.equals("institución")) {
			JMenu usuarioMenu = new JMenu("Usuarios");

			JMenuItem altaUsuario = new JMenuItem("Alta/Modificación");
			JMenuItem listado = new JMenuItem("Listado");
			listado.addActionListener(e -> {
				ListadoUsuario listadoUsuario = new ListadoUsuario(api);
				listadoUsuario.setVisible(true);
			});

			usuarioMenu.add(altaUsuario);
			usuarioMenu.add(listado);
			menuBar.add(usuarioMenu);

			JMenu configuracionMenu = new JMenu("Configuración");
			JMenuItem salir = new JMenuItem("Salir");
			salir.addActionListener(e -> System.exit(0));
			configuracionMenu.add(salir);
			menuBar.add(configuracionMenu);
		}

		// Contenido principal
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);

		// Mostrar mensaje de bienvenida en el centro
		String nombre = "(usuario)";
		if (usuario.getPersona() != null) {
			nombre = usuario.getPersona().getNombre();
		}

		JLabel bienvenida = new JLabel("Bienvenido/a " + nombre, SwingConstants.CENTER);
		bienvenida.setFont(new Font("SansSerif", Font.BOLD, 20));
		contentPane.add(bienvenida, BorderLayout.CENTER);
	}

	private Component crearBotonMenu(String texto) {
		JButton boton = new JButton(texto);
		boton.setFocusPainted(false);
		boton.setBorderPainted(false);
		boton.setContentAreaFilled(false);
		boton.setOpaque(false);
		boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return boton;
	}
}
