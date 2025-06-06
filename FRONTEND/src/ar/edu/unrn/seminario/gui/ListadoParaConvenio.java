
package ar.edu.unrn.seminario.gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.PropuestaDTO;

import java.awt.*;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListadoParaConvenio extends JFrame {

	private JTable tablaConvenios;
	private DefaultTableModel modeloTabla;
	private List<PropuestaDTO> propuestas; 
	private JLabel lblTitulo;
	private JButton btnSeleccionar;
	private JButton btnCancelar;

	public ListadoParaConvenio(IApi api) { 
		setTitle("Listado de Convenios");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(700, 450);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		lblTitulo = new JLabel("Convenios disponibles");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblTitulo.setBounds(20, 20, 300, 25);
		getContentPane().add(lblTitulo);

		// Tabla
		String[] columnas = { "Título", "Área de Interés" };
		modeloTabla = new DefaultTableModel(columnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tablaConvenios = new JTable(modeloTabla);
		tablaConvenios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollTabla = new JScrollPane(tablaConvenios);
		scrollTabla.setBounds(20, 60, 640, 291);
		getContentPane().add(scrollTabla);

		// Botón seleccionar
		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnSeleccionar.setBounds(373, 361, 131, 30);
		getContentPane().add(btnSeleccionar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnCancelar.setBounds(530, 361, 131, 30);
		getContentPane().add(btnCancelar);

		// Simular carga (temporal mientras comentaste la llamada real)
		modeloTabla.addRow(new Object[] { "Sistema de turnos médicos", "Salud" });
		modeloTabla.addRow(new Object[] { "ChatBot educativo", "Inteligencia Artificial" });

		// Acción del botón
		btnSeleccionar.addActionListener(e -> seleccionarConvenio());
	}

	private void seleccionarConvenio() {
		int fila = tablaConvenios.getSelectedRow();
		if (fila == -1) {
			JOptionPane.showMessageDialog(this, "Seleccioná un convenio.");
			return;
		}

		String titulo = modeloTabla.getValueAt(fila, 0).toString();
		JOptionPane.showMessageDialog(this, "Convenio seleccionado: " + titulo);

		
		dispose();
	}
}

