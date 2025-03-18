public class EquationFactory {
    public static Equation createEquation(String type, String input) throws EquationParseException {
        switch (type.toLowerCase()) {
            case "linear":
                return EquationParser.parseLinear(input);
            case "quadratic":
                return EquationParser.parseQuadratic(input);
            case "logarithmic":
                return EquationParser.parseLogarithmic(input);
            case "matrix":
                return EquationParser.parseMatrix(input);
            default:
                throw new EquationParseException("Unsupported equation type: " + type);
        }
    }
}
