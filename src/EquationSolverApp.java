import javax.swing.SwingUtilities;

public class EquationSolverApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EquationGUI().setVisible(true));
    }
}
