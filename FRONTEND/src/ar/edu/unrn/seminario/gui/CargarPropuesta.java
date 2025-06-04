package ar.edu.unrn.seminario.gui;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CargarPropuesta extends JDialog {

    public CargarPropuesta(JFrame parent) {
        super(parent, "Cargar Propuesta (Demo)", true);

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

        JLabel header = new JLabel("Carga de Propuesta");
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setForeground(new Color(33, 150, 243));
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.add(header, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        formPanel.add(crearCampo("Título de propuesta:", ""));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(crearCampo("Área de interés:", ""));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(crearAreaTexto("Objetivo del proyecto:", "", 3));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(crearAreaTexto("Breve descripción del proyecto:", "", 4));

        JLabel actividadesLabel = new JLabel("Actividades y horas:");
        actividadesLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        actividadesLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(actividadesLabel);

        String[] columnNames = { "Actividad", "Horas" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        tableModel.addRow(new Object[] { "", 0 });
        tableModel.addRow(new Object[] { "", 0 });

        JTable actividadesTable = new JTable(tableModel);
        actividadesTable.setRowHeight(25);
        JScrollPane tableScroll = new JScrollPane(actividadesTable);
        tableScroll.setPreferredSize(new Dimension(400, 120));
        formPanel.add(tableScroll);

        JLabel totalHorasLabel = new JLabel("Total de horas: 0");
        totalHorasLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalHorasLabel.setForeground(new Color(33, 150, 243));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(totalHorasLabel);

        JScrollPane formScrollPane = new JScrollPane(formPanel);
        formScrollPane.setBorder(null);
        formScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(formScrollPane, BorderLayout.CENTER);

        JButton cerrarBtn = new JButton("Cerrar");
        cerrarBtn.setBackground(new Color(244, 67, 54));
        cerrarBtn.setForeground(Color.WHITE);
        cerrarBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cerrarBtn.setFocusPainted(false);
        cerrarBtn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        cerrarBtn.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(cerrarBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(panel);
        setSize(750, 900);
        setLocationRelativeTo(parent);
        getRootPane().setDefaultButton(cerrarBtn);
    }

    private JPanel crearCampo(String label, String valor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JTextField field = new JTextField(valor);
        field.setEditable(true);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(jLabel);
        panel.add(field);
        return panel;
    }

    private JPanel crearAreaTexto(String label, String valor, int rows) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JTextArea area = new JTextArea(valor, rows, 20);
        area.setEditable(true);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(400, rows * 25));
        panel.add(jLabel);
        panel.add(scroll);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CargarPropuesta dialog = new CargarPropuesta(null);
            dialog.setVisible(true);
        });
    }
}

