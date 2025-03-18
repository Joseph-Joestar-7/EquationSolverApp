import java.util.Arrays;

public class MatrixEquation extends Equation {
    private double[][] coefficients;
    private double[] constants;

    public MatrixEquation(double[][] coefficients, double[] constants) {
        this.coefficients = coefficients;
        this.constants = constants;
    }

    @Override
    public EquationResult solve() {
        int n = constants.length;
        double[] solution = gaussianElimination(coefficients, constants);

        if (solution == null) {
            return new EquationResult("No unique solution");
        }
        return new EquationResult("Solution: " + Arrays.toString(solution));
    }

    private double[] gaussianElimination(double[][] A, double[] b) {
        int n = A.length;
        for (int i = 0; i < n; i++) {
            int max = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(A[k][i]) > Math.abs(A[max][i])) {
                    max = k;
                }
            }

            double[] temp = A[i];
            A[i] = A[max];
            A[max] = temp;

            double t = b[i];
            b[i] = b[max];
            b[max] = t;

            if (Math.abs(A[i][i]) <= 1e-10) {
                return null;
            }

            for (int k = i + 1; k < n; k++) {
                double factor = A[k][i] / A[i][i];
                b[k] -= factor * b[i];
                for (int j = i; j < n; j++) {
                    A[k][j] -= factor * A[i][j];
                }
            }
        }

        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / A[i][i];
        }
        return x;
    }
}
