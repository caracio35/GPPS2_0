package ar.edu.unrn.seminario.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.PersonaDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.exception.DuplicadaException;
import ar.edu.unrn.seminario.exception.ExcepcionPersistencia;

public class AltaUsuario extends JFrame {

    private JPanel contentPane;
    private JTextField usuarioTextField;
    private JTextField contrasenaTextField;
    private JTextField nombreTextField;
    private JTextField emailTextField;
    private JComboBox rolComboBox;

    private List<RolDTO> roles = new ArrayList<>();
    private JTextField textFieldApellido;
    private JTextField textFieldDni;

    public AltaUsuario(IApi api) {
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

        JLabel contrasenaLabel = new JLabel("Contraseña:");
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

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setBounds(43, 84, 56, 16);
        contentPane.add(nombreLabel);

        nombreTextField = new JTextField();
        nombreTextField.setBounds(148, 82, 160, 22);
        contentPane.add(nombreTextField);
        nombreTextField.setColumns(10);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(43, 114, 56, 16);
        contentPane.add(lblApellido);

        textFieldApellido = new JTextField();
        textFieldApellido.setColumns(10);
        textFieldApellido.setBounds(148, 117, 160, 22);
        contentPane.add(textFieldApellido);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(43, 156, 56, 16);
        contentPane.add(emailLabel);

        emailTextField = new JTextField();
        emailTextField.setBounds(148, 154, 160, 22);
        contentPane.add(emailTextField);
        emailTextField.setColumns(10);

        JLabel lblDni = new JLabel("Dni:");
        lblDni.setBounds(43, 193, 56, 16);
        contentPane.add(lblDni);

        textFieldDni = new JTextField();
        textFieldDni.setColumns(10);
        textFieldDni.setBounds(148, 191, 160, 22);
        contentPane.add(textFieldDni);

        JLabel rolLabel = new JLabel("Rol:");
        rolLabel.setBounds(43, 235, 56, 16);
        contentPane.add(rolLabel);

        rolComboBox = new JComboBox();
        rolComboBox.setBounds(148, 232, 160, 22);
        contentPane.add(rolComboBox);

        // 🔻 Cargar roles después de inicializar rolComboBox
        try {
            this.roles = api.obtenerRoles();
            List<RolDTO> rolesFiltrados = filtrarRoles();
            for (RolDTO rol : rolesFiltrados) {
                rolComboBox.addItem(rol.getNombre());
            }
            this.roles = rolesFiltrados;
        } catch (ConexionFallidaException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de conexión", JOptionPane.ERROR_MESSAGE);
        }

        JButton aceptarButton = new JButton("Aceptar");
        aceptarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    RolDTO rol = roles.get(rolComboBox.getSelectedIndex());

                    PersonaDTO persona = new PersonaDTO(
                        nombreTextField.getText(),
                        textFieldApellido.getText(),
                        textFieldDni.getText()
                    );

                    UsuarioDTO nuevoUsuario = new UsuarioDTO(
                        usuarioTextField.getText(),
                        contrasenaTextField.getText(),
                        persona,
                        emailTextField.getText(),
                        rol.getCodigo(),
                        true,
                        null
                    );

                    api.registrarUsuario(nuevoUsuario);
                    JOptionPane.showMessageDialog(null, "Usuario registrado con éxito!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } catch (ConexionFallidaException | DuplicadaException | ExcepcionPersistencia e) {
                    JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        aceptarButton.setBounds(220, 264, 97, 25);
        contentPane.add(aceptarButton);

        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        cancelarButton.setBounds(329, 264, 97, 25);
        contentPane.add(cancelarButton);
    }

    private List<RolDTO> filtrarRoles() {
        return roles.stream()
                .filter(rol -> {
                    String nombre = rol.getNombre().toLowerCase();
                    return nombre.equals("profesor") || nombre.equals("tutor");
                })
                .toList();
    }
}