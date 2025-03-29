import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EquationGUI extends JFrame {
    
    private Equation equa;

    private JComboBox<String> equationTypeBox;
    private JComboBox<String> methodTypeBox;

    // Fields for Log,Linear and Quad input
    private JTextField inputField;
    private JButton solveButton;
    private JTextArea resultArea;

    private JButton plotGraphButton; // new Plot Graph button

    // Fields for matrix input
    private JPanel matrixPanel;
    private JTextField matrixAField;
    private JTextField matrixBField;

    // Fields for transcendental equation solving
    private JPanel methodPanel;
    private JTextField lowerBoundField, upperBoundField, initialGuessField, toleranceField, maxIterationsField;

    public EquationGUI() {
        setTitle("Equation Solver");
        setSize(900, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        inputField = new JTextField(20);

        equationTypeBox = new JComboBox<>(
                new String[] { "Polynomial" ,"Logarithmic", "Matrix", "Transcendental" });
        solveButton = new JButton("Solve");
        plotGraphButton = new JButton("Plot Graph");
        resultArea = new JTextArea(10, 50);
        resultArea.setEditable(false);

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Equation: "));
        topPanel.add(inputField);
        topPanel.add(new JLabel("Type: "));
        topPanel.add(equationTypeBox);
        topPanel.add(solveButton);
        topPanel.add(plotGraphButton);

        matrixPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        matrixAField = new JTextField(20);
        matrixBField = new JTextField(20);
        matrixPanel.add(new JLabel("Matrix A:"));
        matrixPanel.add(matrixAField);
        matrixPanel.add(new JLabel("Vector B:"));
        matrixPanel.add(matrixBField);
        matrixPanel.setVisible(false);

        methodTypeBox = new JComboBox<>(new String[] { "Bisection", "Regula Falsi", "Newton-Raphson" });
        methodPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        lowerBoundField = new JTextField(20);
        upperBoundField = new JTextField(20);
        initialGuessField = new JTextField(20);
        toleranceField = new JTextField(20);
        maxIterationsField = new JTextField(20);

        methodPanel.add(new JLabel("Method:"));
        methodPanel.add(methodTypeBox);
        methodPanel.add(new JLabel("Lower Bound (a):"));
        methodPanel.add(lowerBoundField);
        methodPanel.add(new JLabel("Upper Bound (b):"));
        methodPanel.add(upperBoundField);
        methodPanel.add(new JLabel("Initial Guess (x₀):"));
        methodPanel.add(initialGuessField);
        methodPanel.add(new JLabel("Tolerance (ε):"));
        methodPanel.add(toleranceField);
        methodPanel.add(new JLabel("Max Iterations:"));
        methodPanel.add(maxIterationsField);
        methodPanel.setVisible(false);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(topPanel, BorderLayout.NORTH);
        inputPanel.add(matrixPanel, BorderLayout.CENTER);
        inputPanel.add(methodPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(resultArea);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        equationTypeBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String type = (String) equationTypeBox.getSelectedItem();
                inputField.setVisible(!"Matrix".equals(type) && !"Transcendental".equals(type));
                matrixPanel.setVisible("Matrix".equals(type));
                methodPanel.setVisible("Transcendental".equals(type));
                inputPanel.revalidate();
                inputPanel.repaint();
            }
        });

        methodTypeBox.addItemListener(e -> updateMethodFields());

        solveButton.addActionListener(e -> solveEquation());

        plotGraphButton.addActionListener(e -> {
            String type = (String) equationTypeBox.getSelectedItem();
            if ("Matrix".equalsIgnoreCase(type)) {
                JOptionPane.showMessageDialog(EquationGUI.this,
                        "Graphing is not supported for this type",
                        "Not Supported",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

                GraphWindow graphWindow = new GraphWindow(equa);
                graphWindow.setVisible(true);
            
        });

    }
    
    private void updateMethodFields() {
        String method = (String) methodTypeBox.getSelectedItem();
        boolean isNewton = "Newton-Raphson".equals(method);
        lowerBoundField.setEnabled(!isNewton);
        upperBoundField.setEnabled(!isNewton);
        initialGuessField.setEnabled(isNewton);
    }

    private void solveEquation() {
        String type = (String) equationTypeBox.getSelectedItem();
        try {
            if ("Matrix".equals(type)) {
                String matrixAInput = matrixAField.getText().trim();
                String matrixBInput = matrixBField.getText().trim();
                equa= EquationFactory.createEquation(type, matrixAInput, matrixBInput);
            } else if ("Transcendental".equals(type)) {
                try {
                    String equation = inputField.getText().trim();
                    String method = (String) methodTypeBox.getSelectedItem();
                    double lower = Double.parseDouble(lowerBoundField.getText());
                    double upper = Double.parseDouble(upperBoundField.getText());
                    double guess = Double.parseDouble(initialGuessField.getText());
                    double tolerance = Double.parseDouble(toleranceField.getText());
                    int maxIterations = Integer.parseInt(maxIterationsField.getText());
                    equa = EquationFactory.createEquation(method, equation, lower, upper, guess, tolerance,maxIterations);
                } catch (NumberFormatException e) {
                    resultArea.setText("Error: Please enter valid numeric values.");
                    return;
                }
            } else {
                String input = inputField.getText().trim();
                equa = EquationFactory.createEquation(type, input);
            }
            EquationResult result = equa.solve();
            resultArea.setText(result.toString());
        
        } catch (EquationParseException ex) {
            resultArea.setText("Error: " + ex.getMessage());
        }
    }

}
