import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class CompilerGUI extends JFrame {
    private JTextArea entradaTextArea;
    private JTextArea salidaTextArea;
    private JTextArea reportesTextArea;

    public CompilerGUI() {
        setTitle("LPM DP");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        // Crear la barra de menú
        JMenuBar menuBar = new JMenuBar();

        // Menú "Archivos"
        JMenu archivosMenu = new JMenu("Archivos");
        JMenuItem nuevoItem = new JMenuItem("Nuevo");
        JMenuItem abrirItem = new JMenuItem("Abrir");
        JMenuItem guardarItem = new JMenuItem("Guardar Archivo");
        archivosMenu.add(nuevoItem);
        archivosMenu.add(abrirItem);
        archivosMenu.add(guardarItem);

        // Menú "Reportes" con 3 opciones
        JMenu reportesMenu = new JMenu("Reportes");
        JMenuItem verReporteTextoItem = new JMenuItem("Ver Reporte en Texto");
        JMenuItem generarHTMLTokensItem = new JMenuItem("Generar HTML Tokens");
        JMenuItem generarHTMLErroresItem = new JMenuItem("Generar HTML Errores");
        reportesMenu.add(verReporteTextoItem);
        reportesMenu.add(generarHTMLTokensItem);
        reportesMenu.add(generarHTMLErroresItem);

        // Menú "Ejecutar"
        JMenu ejecutarMenu = new JMenu("Ejecutar");
        JMenuItem ejecutarItem = new JMenuItem("Ejecutar");
        ejecutarMenu.add(ejecutarItem);

        menuBar.add(archivosMenu);
        menuBar.add(reportesMenu);
        menuBar.add(ejecutarMenu);
        setJMenuBar(menuBar);

        // Título principal
        JLabel titleLabel = new JLabel("LPM DP", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));

        // Crear el JTabbedPane con 3 pestañas: Entrada, Salida y Reportes
        JTabbedPane tabbedPane = new JTabbedPane();
        entradaTextArea = new JTextArea();
        salidaTextArea = new JTextArea();
        reportesTextArea = new JTextArea();
        JScrollPane entradaScroll = new JScrollPane(entradaTextArea);
        JScrollPane salidaScroll = new JScrollPane(salidaTextArea);
        JScrollPane reportesScroll = new JScrollPane(reportesTextArea);

        tabbedPane.addTab("Entrada", entradaScroll);
        tabbedPane.addTab("Salida", salidaScroll);
        tabbedPane.addTab("Reportes", reportesScroll);

        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);

        // Acciones del menú "Archivos"
        nuevoItem.addActionListener(e -> entradaTextArea.setText(""));
        abrirItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(CompilerGUI.this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
                    entradaTextArea.setText(content);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(CompilerGUI.this, "Error al abrir el archivo: " + ex.getMessage());
                }
            }
        });
        guardarItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showSaveDialog(CompilerGUI.this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(entradaTextArea.getText());
                    JOptionPane.showMessageDialog(CompilerGUI.this, "Archivo guardado correctamente.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(CompilerGUI.this, "Error al guardar el archivo: " + ex.getMessage());
                }
            }
        });

        // Acciones del menú "Reportes"
        verReporteTextoItem.addActionListener(e -> {
            String reporteTexto = errors.ErrorHandler.getInstance().generateReportAsString("all");
            reportesTextArea.setText(reporteTexto);
            tabbedPane.setSelectedIndex(2);
        });
        generarHTMLTokensItem.addActionListener(e -> {
            tokens.TokenHandler.getInstance().generateReport();
            JOptionPane.showMessageDialog(CompilerGUI.this, "Reporte HTML Tokens generado en la carpeta 'reports'.");
        });
        generarHTMLErroresItem.addActionListener(e -> {
            errors.ErrorHandler.getInstance().generateReport("all");
            JOptionPane.showMessageDialog(CompilerGUI.this, "Reporte HTML Errores generado en la carpeta 'reports'.");
        });

        // Acción del menú "Ejecutar"
        ejecutarItem.addActionListener(e -> {
            String inputText = entradaTextArea.getText();
            if (inputText == null || inputText.trim().isEmpty()) {
                JOptionPane.showMessageDialog(CompilerGUI.this, "La entrada está vacía.");
                return;
            }
            String usar = inputText;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            PrintStream oldOut = System.out;
            System.setOut(ps);

            Main.executeParser(usar);

            reportesTextArea.setText(errors.ErrorHandler.getInstance().generateReportAsString("all"));

            System.out.flush();
            System.setOut(oldOut);
            String output = baos.toString();
            salidaTextArea.setText(output);
            tabbedPane.setSelectedIndex(1);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CompilerGUI gui = new CompilerGUI();
            gui.setVisible(true);
        });
    }
}
