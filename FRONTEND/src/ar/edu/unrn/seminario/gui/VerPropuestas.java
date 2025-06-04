package ar.edu.unrn.seminario.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VerPropuestas extends JDialog {

    public VerPropuestas(JFrame parent) {
        super(parent, "Ver Propuestas (Demo)", true);

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

        JLabel header = new JLabel("Ver Propuestas");
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
        tableModel.addRow(new Object[] {"Ejemplo 1", "Tecnología", "Desarrollo de App"});
        tableModel.addRow(new Object[] {"Ejemplo 2", "Salud", "Campaña de prevención"});

        JTable propuestasTable = new JTable(tableModel);
        propuestasTable.setRowHeight(25);
        propuestasTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(propuestasTable);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JButton verDetalleBtn = new JButton("Ver Detalle");
        verDetalleBtn.setBackground(new Color(33, 150, 243));
        verDetalleBtn.setForeground(Color.WHITE);
        verDetalleBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        verDetalleBtn.setFocusPainted(false);
        verDetalleBtn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        verDetalleBtn.setEnabled(false);

        verDetalleBtn.addActionListener(e -> mostrarDetalle(propuestasTable.getSelectedRow()));

        propuestasTable.getSelectionModel().addListSelectionListener(e -> {
            verDetalleBtn.setEnabled(propuestasTable.getSelectedRow() != -1);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(verDetalleBtn);

        JButton cerrarBtn = new JButton("Cerrar");
        cerrarBtn.setBackground(new Color(244, 67, 54));
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
        setSize(650, 700);
        setLocationRelativeTo(parent);
        getRootPane().setDefaultButton(cerrarBtn);
    }

    private void mostrarDetalle(int selectedRow) {
        if (selectedRow == -1) return;

        String titulo = "Ejemplo 1";
        String area = "Tecnología";
        String descripcion = "Desarrollo de App";

        if (selectedRow == 1) {
            titulo = "Ejemplo 2";
            area = "Salud";
            descripcion = "Campaña de prevención";
        }

        JDialog detalle = new JDialog(this, "Detalle de Propuesta", true);
        detalle.setLayout(new BorderLayout(10, 10));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        panel.add(crearCampo("Título:", titulo));
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearCampo("Área de interés:", area));
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearAreaTexto("Descripción:", descripcion));

        // Tabla de actividades
        panel.add(Box.createVerticalStrut(15));
        JLabel actividadesLabel = new JLabel("Actividades y horas:");
        actividadesLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(actividadesLabel);

        String[] columnNames = { "Actividad", "Horas" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addRow(new Object[] {"Relevamiento", 10});
        tableModel.addRow(new Object[] {"Desarrollo", 20});
        tableModel.addRow(new Object[] {"Testing", 15});

        JTable actividadesTable = new JTable(tableModel);
        actividadesTable.setRowHeight(25);
        JScrollPane actividadesScroll = new JScrollPane(actividadesTable);
        actividadesScroll.setPreferredSize(new Dimension(400, 100));
        panel.add(actividadesScroll);

        // Total de horas
        JLabel totalHorasLabel = new JLabel("Total de horas: 45");
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

        detalle.add(panel, BorderLayout.CENTER);
        detalle.add(btnPanel, BorderLayout.SOUTH);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VerPropuestas dialog = new VerPropuestas(null);
            dialog.setVisible(true);
        });
    }
}

