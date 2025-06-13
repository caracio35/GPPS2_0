package ar.edu.unrn.seminario.gui;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.ActividadDTO;
import ar.edu.unrn.seminario.dto.PropuestaDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.exception.DuplicadaException;
import ar.edu.unrn.seminario.exception.ExcepcionPersistencia;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CargarPropuesta extends JDialog {

	private IApi api;
	private JPanel contentPanel;
	private UsuarioDTO usuario;

	public CargarPropuesta() {
		initialize();
	}

	public CargarPropuesta(JFrame parent, IApi api, UsuarioDTO usuario) {
		super(parent, "Cargar Propuesta", true);
		this.api = api;
		this.usuario = usuario;
		initialize();
	}

	private void initialize() {
		setTitle("Cargar Propuesta");
		setSize(705, 756);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		contentPanel = new JPanel();
		contentPanel.setLayout(null);
		contentPanel.setBounds(0, 0, 734, 722);
		getContentPane().add(contentPanel);

		JLabel header = new JLabel("Carga de Propuesta");
		header.setFont(new Font("Segoe UI", Font.BOLD, 24));
		header.setForeground(new Color(33, 150, 243));
		header.setBounds(50, 28, 300, 30);
		contentPanel.add(header);

		JLabel lblTitulo = new JLabel("Título de propuesta:");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblTitulo.setBounds(50, 80, 200, 20);
		contentPanel.add(lblTitulo);

		JTextField txtTitulo = new JTextField();
		txtTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtTitulo.setBounds(50, 110, 600, 30);
		contentPanel.add(txtTitulo);

		JLabel lblArea = new JLabel("Área de interés:");
		lblArea.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblArea.setBounds(50, 150, 200, 20);
		contentPanel.add(lblArea);

		JTextField txtArea = new JTextField();
		txtArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtArea.setBounds(50, 175, 600, 30);
		contentPanel.add(txtArea);

		JLabel lblObjetivo = new JLabel("Objetivo del proyecto:");
		lblObjetivo.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblObjetivo.setBounds(50, 220, 200, 20);
		contentPanel.add(lblObjetivo);

		JTextArea txtObjetivo = new JTextArea();
		txtObjetivo.setLineWrap(true);
		txtObjetivo.setWrapStyleWord(true);
		JScrollPane scrollObjetivo = new JScrollPane(txtObjetivo);
		scrollObjetivo.setBounds(50, 245, 600, 60);
		contentPanel.add(scrollObjetivo);

		JLabel lblDescripcion = new JLabel("Breve descripción del proyecto:");
		lblDescripcion.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblDescripcion.setBounds(50, 320, 300, 20);
		contentPanel.add(lblDescripcion);

		JTextArea txtDescripcion = new JTextArea();
		txtDescripcion.setLineWrap(true);
		txtDescripcion.setWrapStyleWord(true);
		JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
		scrollDescripcion.setBounds(50, 345, 600, 80);
		contentPanel.add(scrollDescripcion);

		JLabel lblActividades = new JLabel("Actividades y horas:");
		lblActividades.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblActividades.setBounds(50, 440, 200, 20);
		contentPanel.add(lblActividades);

		String[] columnas = { "Actividad", "Horas" };
		DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
		modeloTabla.addRow(new Object[] { "", 0 });
		modeloTabla.addRow(new Object[] { "", 0 });

		JTable tabla = new JTable(modeloTabla);
		tabla.setRowHeight(25);
		JScrollPane scrollTabla = new JScrollPane(tabla);
		scrollTabla.setBounds(50, 465, 600, 128);
		contentPanel.add(scrollTabla);

		JButton btnAgregarActividad = new JButton("Agregar Actividad");
		btnAgregarActividad.setBounds(50, 600, 160, 25);
		btnAgregarActividad.addActionListener(e -> modeloTabla.addRow(new Object[] { "", 0 }));
		contentPanel.add(btnAgregarActividad);

		JLabel lblTotal = new JLabel("Total de horas: 0");
		lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblTotal.setForeground(new Color(33, 150, 243));
		lblTotal.setBounds(230, 600, 200, 25);
		contentPanel.add(lblTotal);

		modeloTabla.addTableModelListener(e -> {
		    int totalHoras = 0;
		    for (int i = 0; i < modeloTabla.getRowCount(); i++) {
		        Object valor = modeloTabla.getValueAt(i, 1);
		        if (valor instanceof Number) {
		            totalHoras += ((Number) valor).intValue();
		        } else {
		            try {
		                totalHoras += Integer.parseInt(valor.toString());
		            } catch (Exception ex) {
		                // Ignorar
		            }
		        }
		    }
		    lblTotal.setText("Total de horas: " + totalHoras);
		});

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCerrar.setBackground(new Color(244, 67, 54));
		btnCerrar.setForeground(new Color(0, 0, 0));
		btnCerrar.setBounds(550, 682, 100, 30);
		btnCerrar.addActionListener(e -> dispose());
		contentPanel.add(btnCerrar);

		JButton btnNewCrearPropuesta = new JButton("Cargar");
		btnNewCrearPropuesta.setBackground(new Color(128, 255, 0));
		btnNewCrearPropuesta.setForeground(new Color(0, 0, 0));
		btnNewCrearPropuesta.setBounds(422, 682, 100, 30);
		contentPanel.add(btnNewCrearPropuesta);

		btnNewCrearPropuesta.addActionListener(e -> {
			String titulo = txtTitulo.getText().trim();
			String area = txtArea.getText().trim();
			String objetivo = txtObjetivo.getText().trim();
			String descripcion = txtDescripcion.getText().trim();

			List<ActividadDTO> actividades = new ArrayList<>();
			for (int i = 0; i < modeloTabla.getRowCount(); i++) {
				String nombre = modeloTabla.getValueAt(i, 0).toString();
				int horas;
				try {
					horas = Integer.parseInt(modeloTabla.getValueAt(i, 1).toString());
				} catch (NumberFormatException ex) {
					horas = 0;
				}
				if (!nombre.isBlank() && horas > 0) {
					actividades.add(new ActividadDTO(nombre, horas));
				}
			}

			int totalHoras = actividades.stream().mapToInt(ActividadDTO::getHoras).sum();
			if (totalHoras < 150 || totalHoras > 200) {
				JOptionPane.showMessageDialog(this,
					"El total de horas debe ser entre 150 y 200.",
					"Error de validación",
					JOptionPane.ERROR_MESSAGE);
				return;
			}

			PropuestaDTO propuesta = new PropuestaDTO(
				titulo,
				descripcion,
				area,
				objetivo,
				"",
				-1,
				usuario,
				actividades
			);

			try {
			    api.crearPropuesta(propuesta);
			    JOptionPane.showMessageDialog(this, "Propuesta creada correctamente.");
			    dispose();
			} catch (ConexionFallidaException e1) {
			    JOptionPane.showMessageDialog(this, e1.getMessage(), "Error de conexión", JOptionPane.ERROR_MESSAGE);
			} catch (DuplicadaException e1) {
			    JOptionPane.showMessageDialog(this, e1.getMessage(), "Propuesta duplicada", JOptionPane.WARNING_MESSAGE);
			} catch (ExcepcionPersistencia e1) {
			    JOptionPane.showMessageDialog(this, e1.getMessage(), "Error de base de datos", JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}


