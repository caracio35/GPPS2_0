package ar.edu.unrn.seminario.gui;

import javax.swing.*;

import ar.edu.unrn.seminario.api.IApi;

import java.awt.*;

public class VentanaCrearConvenio extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtEntidad, txtAlumno, txtDniAlumno;
	private JTextField txtTutor, txtEntidadTutor;
	private JTextField txtProfesor, txtDniProfesor;
	private JTextField txtFechaInicio, txtFechaFin;
	private IApi api;
	public VentanaCrearConvenio(IApi api) {
		setTitle("Crear Convenio");
		setSize(532, 537);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		JLabel lblTitulo = new JLabel("Datos del Convenio");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTitulo.setBounds(20, 20, 300, 30);
		getContentPane().add(lblTitulo);

		JLabel lblEntidad = new JLabel("Nombre de la Entidad:");
		lblEntidad.setBounds(20, 70, 200, 20);
		getContentPane().add(lblEntidad);

		txtEntidad = new JTextField();
		txtEntidad.setBounds(250, 70, 244, 25);
		txtEntidad.setEditable(false);
		getContentPane().add(txtEntidad);

		JLabel lblAlumno = new JLabel("Nombre y Apellido del Alumno:");
		lblAlumno.setBounds(20, 110, 250, 20);
		getContentPane().add(lblAlumno);

		txtAlumno = new JTextField();
		txtAlumno.setBounds(250, 110, 244, 25);
		txtAlumno.setEditable(false);
		getContentPane().add(txtAlumno);

		JLabel lblDniAlumno = new JLabel("DNI del Alumno:");
		lblDniAlumno.setBounds(20, 150, 200, 20);
		getContentPane().add(lblDniAlumno);

		txtDniAlumno = new JTextField();
		txtDniAlumno.setBounds(250, 150, 244, 25);
		txtDniAlumno.setEditable(false);
		getContentPane().add(txtDniAlumno);

		JLabel lblTutor = new JLabel("Nombre y Apellido del Tutor:");
		lblTutor.setBounds(20, 190, 250, 20);
		getContentPane().add(lblTutor);

		txtTutor = new JTextField();
		txtTutor.setBounds(250, 190, 244, 25);
		txtTutor.setEditable(false);
		getContentPane().add(txtTutor);

		JLabel lblEntidadTutor = new JLabel("DNI del Tutor");
		lblEntidadTutor.setBounds(20, 230, 200, 20);
		getContentPane().add(lblEntidadTutor);

		txtEntidadTutor = new JTextField();
		txtEntidadTutor.setBounds(250, 230, 244, 25);
		txtEntidadTutor.setEditable(false);
		getContentPane().add(txtEntidadTutor);

		JLabel lblProfesor = new JLabel("Nombre y Apellido del Profesor:");
		lblProfesor.setBounds(20, 270, 250, 20);
		getContentPane().add(lblProfesor);

		txtProfesor = new JTextField();
		txtProfesor.setBounds(250, 270, 244, 25);
		txtProfesor.setEditable(false);
		getContentPane().add(txtProfesor);

		JLabel lblDniProfesor = new JLabel("DNI del Profesor:");
		lblDniProfesor.setBounds(20, 310, 200, 20);
		getContentPane().add(lblDniProfesor);

		txtDniProfesor = new JTextField();
		txtDniProfesor.setBounds(250, 310, 244, 25);
		txtDniProfesor.setEditable(false);
		getContentPane().add(txtDniProfesor);

		JLabel lblFechaInicio = new JLabel("Fecha de Inicio:");
		lblFechaInicio.setBounds(20, 350, 200, 20);
		getContentPane().add(lblFechaInicio);

		txtFechaInicio = new JTextField("dd/mm/aaaa");
		txtFechaInicio.setBounds(250, 350, 150, 25);
		getContentPane().add(txtFechaInicio);

		JLabel lblFechaFin = new JLabel("Fecha de Finalización:");
		lblFechaFin.setBounds(20, 390, 200, 20);
		getContentPane().add(lblFechaFin);

		txtFechaFin = new JTextField("dd/mm/aaaa");
		txtFechaFin.setBounds(250, 390, 150, 25);
		getContentPane().add(txtFechaFin);

		JButton btnCrear = new JButton("Crear Convenio");
		btnCrear.setBounds(192, 460, 150, 30);
		getContentPane().add(btnCrear);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(364, 460, 130, 30);
		btnCancelar.addActionListener(e -> dispose());
		getContentPane().add(btnCancelar);
	}
}

