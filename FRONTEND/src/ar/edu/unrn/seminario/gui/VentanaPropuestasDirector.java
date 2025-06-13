package ar.edu.unrn.seminario.gui;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.*;
import ar.edu.unrn.seminario.exception.ExcepcionPersistencia;

public class VentanaPropuestasDirector extends JDialog {
    private List<PropuestaDTO> propuestas;
    private IApi api;
    private UsuarioDTO director;

    public VentanaPropuestasDirector(JFrame parent, IApi api, UsuarioDTO director) {
        super(parent, "Propuestas Pendientes", true);
        this.api = api;
        this.director = director;

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(240, 248, 255);
                Color color2 = new Color(248, 248, 255);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel header = new JLabel("Propuestas Pendientes de Aprobación");
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setForeground(new Color(33, 150, 243));
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.add(header, BorderLayout.NORTH);

        String[] columnNames = { "Título", "Área de interés", "Descripción" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        propuestas = new ArrayList<>();
        List<PropuestaDTO> todas = new ArrayList<>();
        try {
            todas = api.findTodasConDetalles();
        } catch (ExcepcionPersistencia e) {
            JOptionPane.showMessageDialog(this,
                "❌ Error al cargar las propuestas desde la base de datos:\n" + e.getMessage(),
                "Error de carga",
                JOptionPane.ERROR_MESSAGE);
        }

        for (PropuestaDTO p : todas) {
            if (p.getAceptados() == -1) {
                tableModel.addRow(new Object[]{p.getTitulo(), p.getAreaDeInteres(), p.getDescripcion()});
                propuestas.add(p);
            }
        }

        JTable propuestasTable = new JTable(tableModel);
        propuestasTable.setRowHeight(25);
        propuestasTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(propuestasTable);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JButton verDetalleBtn = new JButton("Ver Detalle");
        JButton aprobarBtn = new JButton("Aprobar");
        JButton rechazarBtn = new JButton("Rechazar");

        verDetalleBtn.setEnabled(false);
        aprobarBtn.setEnabled(false);
        rechazarBtn.setEnabled(false);

        verDetalleBtn.addActionListener(e -> mostrarDetalle(propuestasTable.getSelectedRow()));

        aprobarBtn.addActionListener(e -> {
            int selected = propuestasTable.getSelectedRow();
            if (selected != -1) {
                PropuestaDTO p = propuestas.get(selected);
                p.setAceptado(1);
                try {
                    api.actualizarEstadoPropuesta(p);
                    JOptionPane.showMessageDialog(this, "✅ Propuesta aprobada.");
                    dispose();
                } catch (ExcepcionPersistencia e1) {
                    JOptionPane.showMessageDialog(this,
                        "❌ Error al aprobar la propuesta:\n" + e1.getMessage(),
                        "Error de base de datos",
                        JOptionPane.ERROR_MESSAGE);
                    
                }
            }});

        rechazarBtn.addActionListener(e -> {
            int selected = propuestasTable.getSelectedRow();
            if (selected != -1) {
                PropuestaDTO p = propuestas.get(selected);
                p.setAceptado(0);
                try {
                    api.actualizarEstadoPropuesta(p);
                    JOptionPane.showMessageDialog(this, "❌ Propuesta rechazada.");
                    dispose();
                } catch (ExcepcionPersistencia e1) {
                    JOptionPane.showMessageDialog(this,
                        "❌ Error al rechazar la propuesta:\n" + e1.getMessage(),
                        "Error en la base de datos",
                        JOptionPane.ERROR_MESSAGE);
                    
                }}
        });

        propuestasTable.getSelectionModel().addListSelectionListener(e -> {
            boolean habilitado = propuestasTable.getSelectedRow() != -1;
            verDetalleBtn.setEnabled(habilitado);
            aprobarBtn.setEnabled(habilitado);
            rechazarBtn.setEnabled(habilitado);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(verDetalleBtn);
        buttonPanel.add(aprobarBtn);
        buttonPanel.add(rechazarBtn);

        JButton cerrarBtn = new JButton("Cerrar");
        cerrarBtn.setBackground(Color.WHITE);
        cerrarBtn.setForeground(Color.WHITE);
        cerrarBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cerrarBtn.setFocusPainted(false);
        cerrarBtn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        cerrarBtn.addActionListener(e -> dispose());
        buttonPanel.add(cerrarBtn);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(contentPanel, BorderLayout.CENTER);

        setContentPane(panel);
        setSize(700, 700);
        setLocationRelativeTo(parent);
    }

    private void mostrarDetalle(int selectedRow) {
        if (selectedRow == -1 || selectedRow >= propuestas.size()) return;

        PropuestaDTO propuesta = propuestas.get(selectedRow);

        JDialog detalle = new JDialog(this, "Detalle de Propuesta", true);
        detalle.getContentPane().setLayout(new BorderLayout(10, 10));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        panel.add(crearCampo("Título:", propuesta.getTitulo()));
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearCampo("Área de interés:", propuesta.getAreaDeInteres()));
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearAreaTexto("Descripción:", propuesta.getDescripcion()));

        panel.add(Box.createVerticalStrut(15));
        JLabel actividadesLabel = new JLabel("Actividades y horas:");
        actividadesLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(actividadesLabel);

        String[] columnNames = { "Actividad", "Horas" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        int totalHoras = 0;
        for (ActividadDTO a : propuesta.getActividades()) {
            tableModel.addRow(new Object[]{a.getNombre(), a.getHoras()});
            totalHoras += a.getHoras();
        }
        JTable actividadesTable = new JTable(tableModel);
        actividadesTable.setRowHeight(25);
        JScrollPane actividadesScroll = new JScrollPane(actividadesTable);
        actividadesScroll.setPreferredSize(new Dimension(400, 100));
        panel.add(actividadesScroll);

        JLabel totalHorasLabel = new JLabel("Total de horas: " + totalHoras);
        totalHorasLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalHorasLabel.setForeground(new Color(33, 150, 243));
        panel.add(Box.createVerticalStrut(10));
        panel.add(totalHorasLabel);

        JButton cerrar = new JButton("Cerrar");
        cerrar.setBackground(new Color(244, 67, 54));
        cerrar.setForeground(Color.WHITE);
        cerrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cerrar.addActionListener(e -> detalle.dispose());

        JPanel btnPanel = new JPanel();
        btnPanel.add(cerrar);

        detalle.getContentPane().add(panel, BorderLayout.CENTER);
        detalle.getContentPane().add(btnPanel, BorderLayout.SOUTH);
        detalle.setSize(500, 550);
        detalle.setLocationRelativeTo(this);
        detalle.setVisible(true);
    }

    private JPanel crearCampo(String label, String valor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JTextField field = new JTextField(valor);
        field.setEditable(false);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(jLabel);
        panel.add(field);
        return panel;
    }

    private JPanel crearAreaTexto(String label, String valor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JTextArea area = new JTextArea(valor);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(400, 80));
        panel.add(jLabel);
        panel.add(scroll);
        return panel;
    }
}

