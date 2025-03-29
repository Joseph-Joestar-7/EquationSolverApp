public class EquationFactory {

    public static Equation createEquation(String type, String input) throws EquationParseException {
        switch (type.toLowerCase()) {
            case "linear":
                return EquationParser.parseLinear(input);
            case "quadratic":
                System.out.println(input);
                return EquationParser.parseQuadratic(input);
            case "logarithmic":
                return EquationParser.parseLogarithmic(input);
            case "polynomial":
                return EquationParser.parsePolynomial(input);
            
            default:
                throw new EquationParseException("Unsupported equation type: " + type);
        }
    }

    public static Equation createEquation(String type, String input1, String input2) throws EquationParseException{
        return EquationParser.parseMatrix(input1, input2);
    }

    public static Equation createEquation(String method, String equation, double lower, double upper, double guess, double tolerance, int max_iterations ) throws EquationParseException{
        return null;
    }
}
