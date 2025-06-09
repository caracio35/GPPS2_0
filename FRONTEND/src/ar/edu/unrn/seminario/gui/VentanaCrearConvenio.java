package ar.edu.unrn.seminario.gui;

import javax.swing.*;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.ActividadDTO;
import ar.edu.unrn.seminario.dto.PropuestaDTO;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VentanaCrearConvenio extends JFrame {

	  private static final long serialVersionUID = 1L;
	    private JTextField txtEntidad, txtAlumno, txtDniAlumno;
	    private JTextField txtTutor, txtEntidadTutor;
	    private JTextField txtProfesor, txtDniProfesor;
	    private JTextField txtFechaInicio, txtFechaFin;
	    private IApi api;
	    private PropuestaDTO propuesta;

	    public VentanaCrearConvenio(IApi api, PropuestaDTO propuesta) {
	        this.api = api;
	        this.propuesta = propuesta;

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

	        // ✅ Cargar automáticamente los datos desde la propuesta
	        cargarDatosDesdePropuesta();
	        
	        txtFechaInicio.addFocusListener(new FocusAdapter() {
	            @Override
	            public void focusLost(FocusEvent e) {
	                calcularFechaFinDesdeActividades();
	            }
	        });
	        
	        btnCrear.addActionListener(e -> {
	            try {
	                // Validar fechas
	                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	                LocalDate fechaInicio = LocalDate.parse(txtFechaInicio.getText(), formatter);
	                LocalDate fechaFin = LocalDate.parse(txtFechaFin.getText(), formatter);

	                // Mostrar diálogo para elegir destino del archivo
	                JFileChooser chooser = new JFileChooser();
	                chooser.setSelectedFile(new File("convenio_" + propuesta.getTitulo().replaceAll("\\s+", "_") + ".docx"));
	                int opcion = chooser.showSaveDialog(this);
	                if (opcion != JFileChooser.APPROVE_OPTION) return;

	                File archivoDestino = chooser.getSelectedFile();

	                // Llamar al método del backend para generar y guardar el convenio
	                api.generarYGuardarConvenio(propuesta, fechaInicio, fechaFin, archivoDestino);

	                JOptionPane.showMessageDialog(this, "Convenio generado correctamente.");
	                dispose(); // Cierra la ventana

	            } catch (Exception ex) {
	                JOptionPane.showMessageDialog(this, "Error al generar el convenio: " + ex.getMessage());
	                ex.printStackTrace();
	            }
	        });
	    }
	    private void calcularFechaFinDesdeActividades() {
	    	 try {
	    	        // Formato esperado
	    	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    	        String fechaInicioTexto = txtFechaInicio.getText();
	    	        LocalDate fechaInicio = LocalDate.parse(fechaInicioTexto, formatter);

	    	        // Validar que no sea menor a hoy
	    	        if (fechaInicio.isBefore(LocalDate.now())) {
	    	            JOptionPane.showMessageDialog(this, "La fecha de inicio no puede ser anterior a hoy.");
	    	            txtFechaFin.setText("");
	    	            return;
	    	        }

	    	        // Sumar horas de actividades
	    	        int totalHoras = propuesta.getActividades()
	    	                                  .stream()
	    	                                  .mapToInt(ActividadDTO::getHoras)
	    	                                  .sum();

	    	        int horasPorDia = 4;
	    	        int diasNecesarios = (int) Math.ceil((double) totalHoras / horasPorDia);

	    	        // Avanzar solo días hábiles
	    	        LocalDate fechaFin = fechaInicio;
	    	        int diasContados = 0;
	    	        while (diasContados < diasNecesarios) {
	    	            fechaFin = fechaFin.plusDays(1);
	    	            DayOfWeek diaSemana = fechaFin.getDayOfWeek();
	    	            if (diaSemana != DayOfWeek.SATURDAY && diaSemana != DayOfWeek.SUNDAY) {
	    	                diasContados++;
	    	            }
	    	        }

	    	        txtFechaFin.setText(fechaFin.format(formatter));

	    	    } catch (Exception e) {
	    	        JOptionPane.showMessageDialog(this, "Error al calcular la fecha de fin. Usá formato dd/MM/yyyy.");
	    	        e.printStackTrace();
	    	    }
	    	}

	    private void cargarDatosDesdePropuesta() {
	        if (propuesta != null) {
	            // Entidad
	            if (propuesta.getCreador() != null) {
	                txtEntidad.setText(propuesta.getCreador().getUsername());
	            }

	            // Alumno
	            if (propuesta.getAlumno() != null && propuesta.getAlumno().getPersona() != null) {
	                txtAlumno.setText(propuesta.getAlumno().getPersona().getNombre() + " " + propuesta.getAlumno().getPersona().getApellido());
	                txtDniAlumno.setText(propuesta.getAlumno().getPersona().getDni());
	            }

	            // Tutor
	            if (propuesta.getTutor() != null && propuesta.getTutor().getPersona() != null) {
	                txtTutor.setText(propuesta.getTutor().getPersona().getNombre() + " " + propuesta.getTutor().getPersona().getApellido());
	                txtEntidadTutor.setText(propuesta.getTutor().getPersona().getDni());
	            }

	            // Profesor
	            if (propuesta.getProfesor() != null && propuesta.getProfesor().getPersona() != null) {
	                txtProfesor.setText(propuesta.getProfesor().getPersona().getNombre() + " " + propuesta.getProfesor().getPersona().getApellido());
	                txtDniProfesor.setText(propuesta.getProfesor().getPersona().getDni());
	            }
	        }
	    }
	}

