package academy.javapro;

class ExpressionParser {
    private final String input;
    private int position;

    public ExpressionParser(String input) {
        this.input = input;
        this.position = 0;
    }

  
    public double parseExpression() {
        double result = parseTerm(); 
        while (position < input.length() && input.charAt(position) == '+') { 
            position++; 
            result += parseTerm(); 
        }
        return result;
    }

    
    private double parseTerm() {
        double result = parseFactor(); 
        while (position < input.length() && input.charAt(position) == '*') { 
            position++; 
            result *= parseFactor(); 
        }
        return result;
    }

   
    private double parseFactor() {
        if (position < input.length() && input.charAt(position) == '(') {
            position++; 
            double result = parseExpression(); 
            if (position < input.length() && input.charAt(position) == ')') { 
                position++; 
            } else {
                throw new IllegalArgumentException("Mismatched parentheses at position " + position);
            }
            return result;
        } else {
            return parseNumber(); 
        }
    }

   
    private double parseNumber() {
        StringBuilder numberStr = new StringBuilder();
        while (position < input.length() && (Character.isDigit(input.charAt(position)) || input.charAt(position) == '.')) {
            numberStr.append(input.charAt(position));
            position++;
        }
        if (numberStr.length() == 0) {
            throw new IllegalArgumentException("Invalid number at position " + position);
        }
        return Double.parseDouble(numberStr.toString()); 
    }

    public static void main(String[] args) {
        // Test cases
        String[] testCases = {
                "2 + 3 * (4 + 5)",    
                "2 + 3 * 4",          
                "(2 + 3) * 4",       
                "2 * (3 + 4) * (5 + 6)",
                "1.5 + 2.5 * 3"      
        };

       
        for (String expression : testCases) {
            System.out.println("\nTest Case: " + expression);
            try {
                ExpressionParser parser = new ExpressionParser(expression.replaceAll("\\s+", "")); // Remove spaces
                double result = parser.parseExpression();
                System.out.println("Result: " + result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
