public class LinearEquation extends Equation {
    private double a, b;

    public LinearEquation(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public EquationResult solve() {
        if (a == 0) return new EquationResult("No solution or infinite solutions.");
        return new EquationResult("x = " + (-b / a));
    }
}
