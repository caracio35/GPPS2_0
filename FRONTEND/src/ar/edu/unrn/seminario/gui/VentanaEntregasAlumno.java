package ar.edu.unrn.seminario.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.*;

public class VentanaEntregasAlumno extends JFrame {

    private final IApi api;
    private final UsuarioDTO alumno;
    private final PropuestaDTO propuesta;
    private JTable tablaActividades;
    private JButton btnEntregarSeleccionada;

    public VentanaEntregasAlumno(IApi api, UsuarioDTO alumno) {
        this.api = api;
        this.alumno = alumno;
        this.propuesta = api.obtenerPropuestaDeAlumno(alumno.getUsername());

        setTitle("Entregas de Actividades");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        if (propuesta == null) {
            JOptionPane.showMessageDialog(
                this,
                "Todavía no estás inscripto a ninguna propuesta.\n" +
                "Por favor, inscribite antes de entregar tareas.",
                "Sin propuesta asignada",
                JOptionPane.WARNING_MESSAGE
            );
            dispose();
            return;
        }

        JLabel lblTitulo = new JLabel("Actividades de la propuesta: " + propuesta.getTitulo());
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.NORTH);

        // Modelo de tabla
        String[] columnas = {"Actividad", "Horas", "Entregado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        tablaActividades = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaActividades);
        add(scrollPane, BorderLayout.CENTER);

        // Llenar tabla
        for (ActividadDTO act : propuesta.getActividades()) {
            modelo.addRow(new Object[]{
                act.getNombre(),
                act.getHoras(),
                "No" // Si querés consultar si ya entregó, podés actualizar esto más adelante
            });
        }

        // Botón inferior
        btnEntregarSeleccionada = new JButton("Entregar tarea seleccionada");
        btnEntregarSeleccionada.setEnabled(false);
        add(btnEntregarSeleccionada, BorderLayout.SOUTH);

        // Habilitar el botón al seleccionar una fila
        tablaActividades.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                btnEntregarSeleccionada.setEnabled(tablaActividades.getSelectedRow() != -1);
            }
        });

        // Acción del botón
        btnEntregarSeleccionada.addActionListener(e -> {
            int fila = tablaActividades.getSelectedRow();
            if (fila != -1) {
                String nombreActividad = (String) tablaActividades.getValueAt(fila, 0);
                new CargarTareaActividad(api, nombreActividad, this).setVisible(true);
            }
        });

        setVisible(true);
    }
}