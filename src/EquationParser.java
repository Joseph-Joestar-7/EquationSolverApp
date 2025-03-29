import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class EquationParser {
    public static Equation parseLinear(String input) throws EquationParseException {
        input = input.replaceAll("\\s+", "");
        String[] sides = input.split("=");
        if (sides.length != 2 || !sides[1].equals("0")) {
            throw new EquationParseException("Invalid linear equation format. Expected 'ax+b=0'");
        }
        Pattern pattern = Pattern.compile("([+-]?\\d*\\.?\\d*)x([+-]\\d*\\.?\\d*)?");
        Matcher matcher = pattern.matcher(sides[0]);
        if (matcher.matches()) {
            double a = Double.parseDouble(matcher.group(1));
            double b = Double.parseDouble(matcher.group(2));
            return new LinearEquation(a, b);
        } else {
            throw new EquationParseException("Failed to parse linear equation.");
        }
    }

    public static Equation parseQuadratic(String input) throws EquationParseException {
        input = input.replaceAll("\\s+", "");
        String[] sides = input.split("=");
        if (sides.length != 2 || !sides[1].equals("0")) {
            throw new EquationParseException("Invalid quadratic equation format.");
        }
        Pattern pattern = Pattern.compile("([+-]?\\d*\\.?\\d*)x\\^2([+-]?\\d*\\.?\\d*)x([+-]?\\d*\\.?\\d*)");
        Matcher matcher = pattern.matcher(sides[0]);
        if (matcher.matches()) {
            double a = Double.parseDouble(matcher.group(1));
            double b = Double.parseDouble(matcher.group(2));
            double c = Double.parseDouble(matcher.group(3));
            return new QuadraticEquation(a, b, c);
        } else {
            throw new EquationParseException("Failed to parse quadratic equation.");
        }
    }

    public static Equation parsePolynomial(String input) throws EquationParseException {
        input = input.replaceAll("\\s+", ""); 
        String[] sides = input.split("=");

        if (sides.length != 2 || !sides[1].equals("0")) {
            throw new EquationParseException(
                    "Invalid polynomial equation format. Expected format: 'ax^n + bx^n-1 + ... + c = 0'");
        }

        Pattern pattern = Pattern.compile("([+-]?\\d*\\.?\\d*)x\\^?(\\d*)|([+-]?\\d+\\.?\\d*)");
        Matcher matcher = pattern.matcher(sides[0]);

        int maxDegree = -1;
        List<Double> coeffs = new ArrayList<>();

        while (matcher.find()) {
            double coefficient;
            int degree;

            if (matcher.group(3) != null) {
                coefficient = Double.parseDouble(matcher.group(3));
                degree = 0;
            } else {
                coefficient = matcher.group(1).isEmpty() || matcher.group(1).equals("+") ? 1
                        : matcher.group(1).equals("-") ? -1
                                : Double.parseDouble(matcher.group(1));

                degree = matcher.group(2).isEmpty() ? 1 : Integer.parseInt(matcher.group(2));
            }

            while (coeffs.size() <= degree) {
                coeffs.add(0.0);
            }
            coeffs.set(degree, coeffs.get(degree) + coefficient); 
            maxDegree = Math.max(maxDegree, degree);
        }

        double[] finalCoefficients = new double[maxDegree + 1];
        for (int i = 0; i <= maxDegree; i++) {
            finalCoefficients[i] = coeffs.get(i);
        }
        return new PolynomialEquation(finalCoefficients);
    }

    public static Equation parseLogarithmic(String input) throws EquationParseException {
        input = input.replaceAll("\\s+", "");
        Pattern pattern = Pattern.compile("log\\(x\\)([+-]\\d+)?=0");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            String cStr = matcher.group(1);
            double c = (cStr == null) ? 0 : Double.parseDouble(cStr);
            return new LogarithmicEquation(c);
        } else {
            throw new EquationParseException("Failed to parse logarithmic equation. Expected format: log(x)+c=0");
        }
    }

    public static Equation parseMatrix(String matrixAInput, String matrixBInput) throws EquationParseException {
        double[][] A = parseMatrixString(matrixAInput);
        double[] b = parseVectorString(matrixBInput);
        if (A.length != b.length) {
            throw new EquationParseException(
                    "The number of rows in Matrix A must equal the number of elements in Vector B.");
        }
        return new MatrixEquation(A, b);
    }

    private static double[][] parseMatrixString(String input) throws EquationParseException {
        input = input.trim();
        if (input.startsWith("[") && input.endsWith("]")) {
            input = input.substring(1, input.length() - 1);
        } else {
            throw new EquationParseException("Matrix A format error: missing square brackets.");
        }
        String[] rows = input.split(";");
        double[][] matrix = new double[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i].trim();
            String[] tokens = row.split("\\s+");
            matrix[i] = new double[tokens.length];
            for (int j = 0; j < tokens.length; j++) {
                try {
                    matrix[i][j] = Double.parseDouble(tokens[j]);
                } catch (NumberFormatException e) {
                    throw new EquationParseException("Invalid number in Matrix A: " + tokens[j]);
                }
            }
        }
        int cols = matrix[0].length;
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i].length != cols) {
                throw new EquationParseException("Rows in Matrix A have inconsistent lengths.");
            }
        }
        return matrix;
    }

    private static double[] parseVectorString(String input) throws EquationParseException {
        input = input.trim();
        if (input.startsWith("[") && input.endsWith("]")) {
            input = input.substring(1, input.length() - 1);
        } else {
            throw new EquationParseException("Vector B format error: missing square brackets.");
        }

        input = input.replaceAll(";", " ");

        String[] tokens = input.split("\\s+");
        double[] vector = new double[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            try {
                vector[i] = Double.parseDouble(tokens[i]);
            } catch (NumberFormatException e) {
                throw new EquationParseException("Invalid number in Vector B: " + tokens[i]);
            }
        }
        return vector;
    }
}
