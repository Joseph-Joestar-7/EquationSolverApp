public class QuadraticEquation extends Equation {
    private double a, b, c;

    public QuadraticEquation(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double evaluate(double x) {
        return a * x * x + b * x + c;
    }

    @Override
    public EquationResult solve() {
        double discriminant = b * b - 4 * a * c;
        if (discriminant > 0) {
            double x1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double x2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            return new EquationResult("x1 = " + x1 + ", x2 = " + x2);
        } else if (discriminant == 0) {
            double x = -b / (2 * a);
            return new EquationResult("x = " + x);
        } else {
            double realPart = -b / (2 * a);
        double imaginaryPart = Math.sqrt(-discriminant) / (2 * a);
        String root1 = realPart + " + " + imaginaryPart + "i";
        String root2 = realPart + " - " + imaginaryPart + "i";
        return new EquationResult("x1 = " + root1 + ", x2 = " + root2);
        }
    }
}
