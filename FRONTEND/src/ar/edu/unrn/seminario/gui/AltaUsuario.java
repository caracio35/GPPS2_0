package ar.edu.unrn.seminario.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.exception.ConexionFallidaException;

public class AltaUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField usuarioTextField;
	private JTextField contrasenaTextField;
	private JTextField nombreTextField;
	private JTextField emailTextField;
	private JComboBox rolComboBox;

	private List<RolDTO> roles = new ArrayList<>();
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Create the frame.
	 */
	public AltaUsuario(IApi api) {

		// Obtengo los roles
		try {
		    this.roles = api.obtenerRoles();
		} catch (ConexionFallidaException e) {
		    JOptionPane.showMessageDialog(this, e.getMessage(), "Error de conexión", JOptionPane.ERROR_MESSAGE);
		}

		setTitle("Alta Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 336);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel usuarioLabel = new JLabel("Usuario:");
		usuarioLabel.setBounds(43, 16, 76, 16);
		contentPane.add(usuarioLabel);

		JLabel contrasenaLabel = new JLabel("Contrase\u00F1a:");
		contrasenaLabel.setBounds(43, 47, 93, 16);
		contentPane.add(contrasenaLabel);

		usuarioTextField = new JTextField();
		usuarioTextField.setBounds(148, 13, 160, 22);
		contentPane.add(usuarioTextField);
		usuarioTextField.setColumns(10);

		contrasenaTextField = new JTextField();
		contrasenaTextField.setBounds(148, 45, 160, 22);
		contentPane.add(contrasenaTextField);
		contrasenaTextField.setColumns(10);

		JButton aceptarButton = new JButton("Aceptar");
		aceptarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				RolDTO rol = roles.get(rolComboBox.getSelectedIndex());

					/*api.registrarUsuario(usuarioTextField.getText(), contrasenaTextField.getText(),
							nombreTextField.getText(), emailTextField.getText(), rol.getCodigo());
					JOptionPane.showMessageDialog(null, "Usuario registrado con exito!", "Info", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
					dispose();
*/
			}
		});
		aceptarButton.setBounds(220, 264, 97, 25);
		contentPane.add(aceptarButton);

		JButton cancelarButton = new JButton("Cancelar");
		cancelarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		cancelarButton.setBounds(329, 264, 97, 25);
		contentPane.add(cancelarButton);

		JLabel nombreLabel = new JLabel("Nombre:");
		nombreLabel.setBounds(43, 84, 56, 16);
		contentPane.add(nombreLabel);

		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setBounds(43, 156, 56, 16);
		contentPane.add(emailLabel);

		JLabel rolLabel = new JLabel("Rol:");
		rolLabel.setBounds(43, 235, 56, 16);
		contentPane.add(rolLabel);

		nombreTextField = new JTextField();
		nombreTextField.setBounds(148, 82, 160, 22);
		contentPane.add(nombreTextField);
		nombreTextField.setColumns(10);

		emailTextField = new JTextField();
		emailTextField.setBounds(148, 154, 160, 22);
		contentPane.add(emailTextField);
		emailTextField.setColumns(10);

		rolComboBox = new JComboBox();
		rolComboBox.setBounds(148, 232, 160, 22);
		contentPane.add(rolComboBox);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(43, 114, 56, 16);
		contentPane.add(lblApellido);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(148, 117, 160, 22);
		contentPane.add(textField);
		
		JLabel lblDni = new JLabel("Dni:");
		lblDni.setBounds(43, 193, 56, 16);
		contentPane.add(lblDni);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(148, 191, 160, 22);
		contentPane.add(textField_1);

		for (RolDTO rol : this.roles) {
			rolComboBox.addItem(rol.getNombre());
		}

	}
}
