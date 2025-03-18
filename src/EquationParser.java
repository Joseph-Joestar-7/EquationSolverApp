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

    public static Equation parseMatrix(String input) throws EquationParseException {
        throw new EquationParseException("Matrix equations not yet implemented.");
    }
}
