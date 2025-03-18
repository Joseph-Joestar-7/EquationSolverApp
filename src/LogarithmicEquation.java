public class LogarithmicEquation extends Equation {
    private double c;

    public LogarithmicEquation(double c) {
        this.c = c;
    }

    @Override
    public EquationResult solve() {
        if (c >= 0) {
            return new EquationResult("No solution (logarithm of a non-positive number is undefined)");
        }
        double x = Math.pow(10, -c); // Solving log(x) + c = 0 => x = 10^(-c)
        return new EquationResult("x = " + x);
    }
}
