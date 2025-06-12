package ar.edu.unrn.seminario.gui;

import javax.swing.*;

import ar.edu.unrn.seminario.api.IApi;

import java.awt.*;
import java.io.File;

public class CargarTareaActividad extends JFrame {

	private IApi api;
	private String nombreActividad ;
	private  JFrame ventanaAnterior;
	private File archivoSeleccionado;
	private JTextField txtNombreArchivo;

	public CargarTareaActividad(IApi api, String nombreActividad, JFrame ventanaAnterior) {
	    this.api = api;
	    this.nombreActividad = nombreActividad;
	    this.ventanaAnterior = ventanaAnterior;

	    setTitle("Cargar Tarea / Archivo");
	    setSize(450, 200);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setLocationRelativeTo(null);
	    setLayout(null);

	    JLabel lblInstruccion = new JLabel("Seleccioná un archivo para subir:");
	    lblInstruccion.setBounds(20, 20, 300, 25);
	    add(lblInstruccion);

	    txtNombreArchivo = new JTextField();
	    txtNombreArchivo.setEditable(false);
	    txtNombreArchivo.setBounds(20, 60, 280, 25);
	    add(txtNombreArchivo);

	    JButton btnSeleccionar = new JButton("Seleccionar archivo");
	    btnSeleccionar.setBounds(310, 60, 120, 25);
	    add(btnSeleccionar);

	    JButton btnSubir = new JButton("Subir");
	    btnSubir.setBounds(150, 110, 100, 30);
	    add(btnSubir);

	    btnSeleccionar.addActionListener(e -> {
	        JFileChooser fileChooser = new JFileChooser();
	        int opcion = fileChooser.showOpenDialog(this);
	        if (opcion == JFileChooser.APPROVE_OPTION) {
	            archivoSeleccionado = fileChooser.getSelectedFile();
	            txtNombreArchivo.setText(archivoSeleccionado.getName());
	        }
	    });

	    btnSubir.addActionListener(e -> {
	        if (archivoSeleccionado != null) {
	            try {
	                api.registrarEntrega(nombreActividad, archivoSeleccionado);
	                JOptionPane.showMessageDialog(this, "Archivo cargado correctamente.");
	                ventanaAnterior.dispose(); // Cierra VentanaEntregasAlumno
	                dispose(); // Cierra esta ventana
	            } catch (Exception ex) {
	                JOptionPane.showMessageDialog(this, "Error al subir archivo: " + ex.getMessage());
	            }
	        } else {
	            JOptionPane.showMessageDialog(this, "Primero seleccioná un archivo.");
	        }
	    });
	}
}
