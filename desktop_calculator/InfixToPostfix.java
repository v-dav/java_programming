import java.util.*;
import java.util.regex.*;
import javax.swing.*;

public class InfixToPostfix {

    public List<String> splitNumbersOpsAndParentheses(JLabel equationLabel) {
        String expression = equationLabel.getText();
        // Updated pattern to include √ and ^
        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?|[+-×÷()√^]");
        Matcher matcher = pattern.matcher(expression);
        List<String> tokens = new ArrayList<>();
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }

    public boolean infixIsCorrect(List<String> tokens) {
        if (tokens.isEmpty()) {
            return false;
        }

        // Check for balanced parentheses
        if (!areParenthesesBalanced(tokens)) {
            return false;
        }

        // Check for empty parentheses
        if (hasEmptyParentheses(tokens)) {
            return false;
        }

        // Validate token sequence
        return isValidTokenSequence(tokens);
    }

    private boolean hasEmptyParentheses(List<String> tokens) {
        for (int i = 0; i < tokens.size() - 1; i++) {
            if (tokens.get(i).equals("(") && tokens.get(i + 1).equals(")")) {
                return true;
            }
        }
        return false;
    }

    private boolean areParenthesesBalanced(List<String> tokens) {
        int openCount = 0;

        for (String token : tokens) {
            if (token.equals("(")) {
                openCount++;
            } else if (token.equals(")")) {
                openCount--;
                // More closing than opening parentheses
                if (openCount < 0) {
                    return false;
                }
            }
        }
        // All parentheses should be matched
        return openCount == 0;
    }

    public boolean isOperator(String operator) {
        return List.of("+", "-", "×", "÷", "^").contains(operator);
    }

    public boolean isUnaryOperator(String operator) {
        return operator.equals("√");
    }

    public String infixToPostfix(List<String> tokens) {
        // Preprocess tokens to convert unary minus to special token
        List<String> processedTokens = preprocessUnaryMinus(tokens);

        Deque<String> operatorsStack = new ArrayDeque<>();
        List<String> postfixExpression = new ArrayList<>();

        for (String current : processedTokens) {
            if (isNumber(current)) {
                postfixExpression.add(current);
            } else if (current.equals("(")) {
                operatorsStack.push(current);
            } else if (current.equals(")")) {
                // Pop all operators until we find the opening parenthesis
                while (!operatorsStack.isEmpty() && !operatorsStack.peek().equals("(")) {
                    postfixExpression.add(operatorsStack.pop());
                }
                // Pop the opening parenthesis
                if (!operatorsStack.isEmpty()) {
                    operatorsStack.pop();
                }
            } else if (isOperator(current) || isUnaryOperator(current) || current.equals("~")) {
                // Handle operators based on precedence
                while (!operatorsStack.isEmpty() &&
                        !operatorsStack.peek().equals("(") &&
                        currentHasLowerPrecedence(current, operatorsStack.peek())) {
                    postfixExpression.add(operatorsStack.pop());
                }
                operatorsStack.push(current);
            }
        }

        // Pop remaining operators
        while (!operatorsStack.isEmpty()) {
            postfixExpression.add(operatorsStack.pop());
        }

        return String.join(" ", postfixExpression);
    }

    private List<String> preprocessUnaryMinus(List<String> tokens) {
        List<String> processedTokens = new ArrayList<>();

        for (int i = 0; i < tokens.size(); i++) {
            String current = tokens.get(i);
            String previous = (i > 0) ? tokens.get(i - 1) : null;

            // Convert unary minus to special token "~"
            if (current.equals("-") && (i == 0 || (previous != null && previous.equals("(")))) {
                processedTokens.add("~"); // Use ~ for unary minus
            } else {
                processedTokens.add(current);
            }
        }

        return processedTokens;
    }

    private boolean currentHasLowerPrecedence(String current, String peek) {
        // Right associative operators (like ^) need special handling
        if (current.equals("^") && peek.equals("^")) {
            return false; // Right associative
        }
        // Unary operators should not be popped by other operators
        if (peek.equals("~") || peek.equals("√")) {
            return precedence(current) < precedence(peek);
        }
        return precedence(current) <= precedence(peek);
    }

    private int precedence(String operator) {
        return switch (operator) {
            case "+", "-" -> 1;
            case "×", "÷" -> 2;
            case "^" -> 3;
            case "√", "~" -> 4; // Highest precedence for unary operators (including unary minus)
            default -> 0;
        };
    }

    public float evaluatePostfix(String postfix) {
        Deque<Float> stack = new ArrayDeque<>();

        for (String component : postfix.split(" ")) {
            if (isNumeric(component)) {
                stack.push(Float.parseFloat(component));
            } else if (isUnaryOperator(component) || component.equals("~")) {
                // Handle unary operators
                if (stack.isEmpty()) {
                    throw new ArithmeticException("Invalid expression");
                }
                float operand = stack.pop();

                if (component.equals("√")) {
                    if (operand < 0) {
                        throw new ArithmeticException("Square root of negative number");
                    }
                    stack.push((float) Math.sqrt(operand));
                } else if (component.equals("~")) {
                    // Unary minus (negation)
                    stack.push(-operand);
                }
            } else {
                // Handle binary operators
                if (stack.size() < 2) {
                    throw new ArithmeticException("Invalid expression");
                }
                float nb1 = stack.pop();
                float nb2 = stack.pop();

                switch (component) {
                    case "+" -> stack.push(nb1 + nb2);
                    case "-" -> stack.push(nb2 - nb1);
                    case "×" -> stack.push(nb1 * nb2);
                    case "÷" -> {
                        if (nb1 == 0) {
                            throw new ArithmeticException("Division by zero");
                        }
                        stack.push(nb2 / nb1);
                    }
                    case "^" -> stack.push((float) Math.pow(nb2, nb1));
                }
            }
        }

        if (stack.size() != 1) {
            throw new ArithmeticException("Invalid expression");
        }

        return stack.pop();
    }

    private boolean isNumeric(String component) {
        try {
            Float.parseFloat(component);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidTokenSequence(List<String> tokens) {
        boolean previousWasUnaryMinus = false; // Track unary minus

        for (int i = 0; i < tokens.size(); i++) {
            String current = tokens.get(i);
            String previous = (i > 0) ? tokens.get(i - 1) : null;
            String next = (i < tokens.size() - 1) ? tokens.get(i + 1) : null;

            if (isNumber(current)) {
                // Check for consecutive numbers (missing operator)
                if (next != null && isNumber(next)) {
                    return false; // Case: "3 4"
                }

                // Numbers can be followed by binary operators, closing parenthesis, or nothing
                if (next != null && !isOperator(next) && !next.equals(")")) {
                    return false;
                }

                previousWasUnaryMinus = false; // Reset flag

            } else if (isOperator(current)) {
                // Binary operators cannot be at end
                if (i == tokens.size() - 1) {
                    return false;
                }

                // Special case for minus at the start or after opening parenthesis (negation)
                if (current.equals("-") && (i == 0 || (previous != null && previous.equals("(")))) {
                    // Allow negation
                    if (next == null || (!isNumber(next) && !next.equals("(") && !isUnaryOperator(next))) {
                        return false;
                    }
                    previousWasUnaryMinus = true; // Mark this as unary minus
                    continue;
                }

                // Binary operators at start (not minus)
                if (i == 0) {
                    return false;
                }

                // Check for consecutive binary operators, but ignore if previous was unary minus
                if (previous != null && isOperator(previous) && !previousWasUnaryMinus) {
                    return false; // Case: "3 + + 4"
                }

                if (next != null && isOperator(next) && !next.equals("-")) {
                    return false; // Case: "3 + × 4"
                }

                // Binary operators must be preceded by numbers or closing parenthesis (or unary minus)
                if (previous == null || (!isNumber(previous) && !previous.equals(")") && !previousWasUnaryMinus)) {
                    return false; // Case: "( + 3)"
                }

                // Binary operators must be followed by numbers, opening parenthesis, or unary operators
                if (next == null || (!isNumber(next) && !next.equals("(") && !isUnaryOperator(next))) {
                    return false; // Case: "3 + )"
                }

                previousWasUnaryMinus = false; // Reset flag

            } else if (isUnaryOperator(current)) {
                // Unary operators must be followed by opening parenthesis or number
                if (next == null || (!next.equals("(") && !isNumber(next))) {
                    return false;
                }

                // Unary operators can be at start or preceded by operators or opening parenthesis
                if (previous != null && !isOperator(previous) && !previous.equals("(") && i != 0) {
                    return false;
                }

                previousWasUnaryMinus = false; // Reset flag

            } else if (current.equals("(")) {
                // Opening parenthesis can be preceded by operators, unary operators, other opening parenthesis, or nothing
                if (previous != null && !isOperator(previous) && !isUnaryOperator(previous) && !previous.equals("(")) {
                    return false;
                }

                // Opening parenthesis must be followed by numbers, unary operators, minus (for negation), or other opening parenthesis
                if (next == null || (!isNumber(next) && !next.equals("(") && !isUnaryOperator(next) && !next.equals("-"))) {
                    return false; // Case: "( + 3)" - operator immediately after opening
                }

                previousWasUnaryMinus = false; // Reset flag

            } else if (current.equals(")")) {
                // Closing parenthesis must be preceded by numbers or other closing parenthesis
                if (previous == null || (!isNumber(previous) && !previous.equals(")"))) {
                    return false;
                }

                // Closing parenthesis can be followed by operators, other closing parenthesis, or nothing
                if (next != null && !isOperator(next) && !next.equals(")")) {
                    return false;
                }

                previousWasUnaryMinus = false; // Reset flag
            }
        }

        return true;
    }

    private boolean isNumber(String token) {
        return token.matches("\\d+(\\.\\d+)?");
    }
}