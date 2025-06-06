package ar.edu.unrn.seminario.gui;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.PropuestaDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;

public class SeleccionarProfesorView extends JFrame {

	public SeleccionarProfesorView(IApi api, PropuestaDTO propuesta) {
		setTitle("Asignar Profesor a: " + propuesta.getTitulo());
		setSize(400, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(null);

		JLabel lbl = new JLabel("Seleccionar Profesor:");
		lbl.setBounds(20, 30, 200, 20);
		add(lbl);

		// Lista de profesores disponibles
		//List<UsuarioDTO> profesores = api.obtenerUsuariosPorRol("profesor");
		JComboBox<UsuarioDTO> combo = new JComboBox<>();
		//for (UsuarioDTO u : profesores) {
		//	combo.addItem(u); // toString() debe mostrar nombre + apellido
		//}
		combo.setBounds(160, 30, 200, 25);
		add(combo);

		JButton btnAsignar = new JButton("Asignar");
		btnAsignar.setBounds(250, 100, 100, 30);
		add(btnAsignar);

		btnAsignar.addActionListener(e -> {
			UsuarioDTO profe = (UsuarioDTO) combo.getSelectedItem();
			//api.asignarProfesorAPropuesta(propuesta.getId(), profe.getUsername());
			//JOptionPane.showMessageDialog(this, "Profesor asignado correctamente.");
			dispose();
		});
	}
}