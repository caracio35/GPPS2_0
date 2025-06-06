package ar.edu.unrn.seminario.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.PropuestaDTO;

import java.awt.*;
import java.util.List;

public class AsignarTutor extends JFrame {

	private JTable tabla;
	private DefaultTableModel modelo;
	private List<PropuestaDTO> propuestas;

	public AsignarTutor(IApi api) {
		setTitle("Asignar Tutor a Propuestas");
		setSize(700, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		JLabel lblTitulo = new JLabel("Propuestas aprobadas sin profesor asignado");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblTitulo.setBounds(20, 20, 500, 25);
		getContentPane().add(lblTitulo);

		String[] columnas = { "Título", "Área", "Alumno" };
		modelo = new DefaultTableModel(columnas, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tabla = new JTable(modelo);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBounds(20, 60, 640, 220);
		getContentPane().add(scroll);

		JButton btnAsignar = new JButton("Asignar Tutor");
		btnAsignar.setBounds(560, 300, 120, 30);
		getContentPane().add(btnAsignar);

		// Cargar datos
		/*propuestas = api.obtenerTodasPropuestas();
		for (PropuestaDTO p : propuestas) {
			if (p.isAceptada() && p.getProfesor() == null) {
				modelo.addRow(new Object[] {
					p.getTitulo(),
					p.getAreaInteres(),
					p.getAlumnoNombre() // o nombre completo del alumno
				});
			}
		}*/

		btnAsignar.addActionListener(e -> {
			int fila = tabla.getSelectedRow();
			if (fila == -1) {
				JOptionPane.showMessageDialog(this, "Seleccioná una propuesta.");
				return;
			}
			PropuestaDTO seleccionada = propuestas.get(fila);
			new SeleccionarProfesorView(api, seleccionada).setVisible(true);
		});
	}
}
