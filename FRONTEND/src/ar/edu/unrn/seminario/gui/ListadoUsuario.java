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
                    //aca iria la ventana nueva 
                }
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
    }
