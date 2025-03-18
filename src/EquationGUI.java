import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EquationGUI extends JFrame {
    private JTextField inputField;
    private JComboBox<String> equationTypeBox;
    private JButton solveButton;
    private JTextArea resultArea;
    
    // New panel and fields for matrix input
    private JPanel matrixPanel;
    private JTextField matrixAField;
    private JTextField matrixBField;
    
    public EquationGUI() {
        setTitle("Equation Solver");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }
    
    private void initComponents() {
        // Common input field for non-matrix equations
        inputField = new JTextField(20);
        
        equationTypeBox = new JComboBox<>(new String[]{"Linear", "Quadratic", "Logarithmic", "Matrix"});
        solveButton = new JButton("Solve");
        resultArea = new JTextArea(10, 50);
        resultArea.setEditable(false);
        
        // Top panel with label, input, dropdown, and button
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Equation: "));
        topPanel.add(inputField);
        topPanel.add(new JLabel("Type: "));
        topPanel.add(equationTypeBox);
        topPanel.add(solveButton);
        
        // Matrix panel (hidden by default)
        matrixPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        matrixAField = new JTextField(20);
        matrixBField = new JTextField(20);
        matrixPanel.add(new JLabel("Matrix A:"));
        matrixPanel.add(matrixAField);
        matrixPanel.add(new JLabel("Vector B:"));
        matrixPanel.add(matrixBField);
        matrixPanel.setVisible(false);
        
        // Main input panel combining the common and matrix fields
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(topPanel, BorderLayout.NORTH);
        inputPanel.add(matrixPanel, BorderLayout.CENTER);
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // Switch between a single equation field and matrix fields based on selection
        equationTypeBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String type = (String) equationTypeBox.getSelectedItem();
                if ("Matrix".equals(type)) {
                    inputField.setVisible(false);
                    matrixPanel.setVisible(true);
                } else {
                    inputField.setVisible(true);
                    matrixPanel.setVisible(false);
                }
                inputPanel.revalidate();
                inputPanel.repaint();
            }
        });
        
        solveButton.addActionListener(e -> solveEquation());
    }
    
    private void solveEquation() {
        String type = (String) equationTypeBox.getSelectedItem();
        try {
            Equation eq;
            if ("Matrix".equals(type)) {
                // For matrices, use the separate fields
                String matrixAInput = matrixAField.getText().trim();
                String matrixBInput = matrixBField.getText().trim();
                //eq = EquationParser.parseMatrix(matrixAInput, matrixBInput);
            } else {
                // For other types, use the single input field
                String input = inputField.getText().trim();
                eq = EquationFactory.createEquation(type, input);
            }
            //EquationResult result = eq.solve();
            //resultArea.setText(result.toString());
        } catch (EquationParseException ex) {
            resultArea.setText("Error: " + ex.getMessage());
        }
    }
    
    
}
