package ar.edu.unrn.seminario.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.PropuestaDTO;
import ar.edu.unrn.seminario.exception.ExcepcionPersistencia;

import java.awt.*;
import java.util.List;


	public class AsignarTutor extends JFrame {

		private JTable tabla;
		private DefaultTableModel modelo;
		private List<PropuestaDTO> propuestas;

		public AsignarTutor(IApi api) {
			setTitle("Asignar Profesor y Tutor a Propuestas");
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

			JButton btnAsignar = new JButton("Asignar Profesor y Tutor");
			btnAsignar.setBounds(480, 300, 200, 30);
			getContentPane().add(btnAsignar);

			try {
			    propuestas = api.buscarPropuestasParaAsignarTutores();
			} catch (ExcepcionPersistencia e) {
			    JOptionPane.showMessageDialog(this, e.getMessage(), "Error al obtener propuestas", JOptionPane.ERROR_MESSAGE);
			}
			for (PropuestaDTO p : propuestas) {
				if (p.getAceptados() == 1 && p.getProfesor() == null && p.getAlumno() != null) {
					modelo.addRow(new Object[] {
						p.getTitulo(),
						p.getAreaDeInteres(),
						p.getAlumno().getPersona().getNombre() + " " + p.getAlumno().getPersona().getApellido()
					});
				}
			}

			btnAsignar.addActionListener(e -> {
				int fila = tabla.getSelectedRow();
				if (fila == -1) {
					JOptionPane.showMessageDialog(this, "Seleccioná una propuesta.");
					return;
				}
				PropuestaDTO seleccionada = propuestas.get(fila);
				new SeleccionarProfesorYTutor(api, seleccionada).setVisible(true);
				dispose();
			});
		}
	}