import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EquationGUI extends JFrame {
    private JTextField inputField;
    private JComboBox<String> equationTypeBox;
    private JButton solveButton;
    private JTextArea resultArea;
    
    public EquationGUI() {
        setTitle("Equation Solver");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }
    
    private void initComponents() {
        inputField = new JTextField(20);
        equationTypeBox = new JComboBox<>(new String[]{"Linear", "Quadratic", "Logarithmic", "Matrix"});
        solveButton = new JButton("Solve");
        resultArea = new JTextArea(10, 50);
        resultArea.setEditable(false);
        
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Equation: "));
        topPanel.add(inputField);
        topPanel.add(new JLabel("Type: "));
        topPanel.add(equationTypeBox);
        topPanel.add(solveButton);
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        solveButton.addActionListener(e -> solveEquation());
    }
    
    private void solveEquation() {
        String input = inputField.getText().trim();
        String type = (String) equationTypeBox.getSelectedItem();
        try {
            Equation eq = EquationFactory.createEquation(type, input);
            EquationResult result = eq.solve();
            resultArea.setText(result.toString());
        } catch (EquationParseException ex) {
            resultArea.setText("Error: " + ex.getMessage());
        }
    }
}
