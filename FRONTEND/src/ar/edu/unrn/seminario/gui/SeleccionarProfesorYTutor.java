package ar.edu.unrn.seminario.gui;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.PropuestaDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SeleccionarProfesorYTutor extends JDialog {

	private JComboBox<String> comboProfesores;
	private JComboBox<String> comboTutores;
	private List<UsuarioDTO> todosLosUsuario;
	private List<UsuarioDTO> profesores;
	private List<UsuarioDTO> tutores;

	public SeleccionarProfesorYTutor(IApi api, PropuestaDTO propuesta) {
		
		this.todosLosUsuario = api.obtenerUsuarios();
		
		this.todosLosUsuario = api.obtenerUsuarios();

	    // Filtrar los usuarios según el rol
	    this.profesores = new ArrayList<>();
	    this.tutores = new ArrayList<>();

	    for (UsuarioDTO usuario : todosLosUsuario) {
	        if (usuario.getRol() == 3) {
	            tutores.add(usuario);
	        } else if (usuario.getRol() == 5) {
	            profesores.add(usuario);
	        }
	    }
		setTitle("Seleccionar Profesor y Tutor");
		setSize(400, 300);
		setLocationRelativeTo(null);
		setModal(true);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		

		comboProfesores = new JComboBox<>();
		for (UsuarioDTO prof : profesores) {
			comboProfesores.addItem(prof.getPersona().getNombre() + " " + prof.getPersona().getApellido());
		}

		comboTutores = new JComboBox<>();
		for (UsuarioDTO tut : tutores) {
			comboTutores.addItem(tut.getPersona().getNombre() + " " + tut.getPersona().getApellido());
		}

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("Profesor:"), gbc);
		gbc.gridx = 1;
		add(comboProfesores, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		add(new JLabel("Tutor:"), gbc);
		gbc.gridx = 1;
		add(comboTutores, gbc);

		JButton asignarBtn = new JButton("Asignar");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		add(asignarBtn, gbc);

		asignarBtn.addActionListener(e -> {
			int profesorIndex = comboProfesores.getSelectedIndex();
			int tutorIndex = comboTutores.getSelectedIndex();

			if (profesorIndex == -1 || tutorIndex == -1) {
				JOptionPane.showMessageDialog(this, "Seleccioná un profesor y un tutor.");
				return;
			}

			UsuarioDTO profesor = profesores.get(profesorIndex);
			UsuarioDTO tutor = tutores.get(tutorIndex);

			boolean asignado = api.asignarProfesorYTutorAPropuesta(propuesta, profesor, tutor);
			if (asignado) {
				JOptionPane.showMessageDialog(this, "Asignación exitosa.");
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Error al asignar. Intente de nuevo.");
			}
		});
	}
}

