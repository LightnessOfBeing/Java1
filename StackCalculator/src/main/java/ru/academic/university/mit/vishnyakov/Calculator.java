package ru.academic.university.mit.vishnyakov;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * Class that represents our calculator.
 */

public class Calculator {

    /**
     * Stack where we store numbers from expression.
     */

    private Stack<Double> numbers;

    /**
     * Stack where we store operators from expression.
     */

    private Stack<Character> operators;

    /**
     * Returns priority of the operation.
     */

    private static final HashMap<Character, Integer> PRIORITY = new HashMap<>();

    static {
        PRIORITY.put('(', 1);
        PRIORITY.put('+', 2);
        PRIORITY.put('-', 2);
        PRIORITY.put('*', 3);
        PRIORITY.put('/', 3);
    }

    /**
     * Constructor that receives the stack for operations and stack for numbers.
     * @param n - stack for numbers.
     * @param o - stack for operations.
     */

    Calculator(Stack<Double> n, Stack<Character> o) {
        numbers = n;
        operators = o;
    }

    /**
     * Transforms infix notation to RPN.
     * @param expression to transform.
     * @return transformed expression.
     */

    @NotNull
    String parseToRPN(String expression) {
        expression = expression.replace(" ", "");
        StringBuilder output = new StringBuilder();
        int sz = expression.length();
        int i = 0;
        while (i < sz) {
            char token = expression.charAt(i);
            if ('0' <= token && token <= '9') {
                while ('0' <= token && token <= '9' && i < sz) {
                    output.append(token);
                    i++;
                    if (i < sz) {
                        token = expression.charAt(i);
                    }
                }
                i--;
                output.append(' ');
            }
            else if (token == ')') {
                while (operators.top() != '(') {
                    output.append(operators.pop());
                    output.append(' ');
                }
                operators.pop();
            }
            else if (token == '(') {
                operators.push(token);
            }
            else {
                int currentPriority = PRIORITY.getOrDefault(token, 4);

                while (!operators.empty() && PRIORITY.getOrDefault(operators.top(), 4) >= currentPriority) {
                    output.append(operators.pop());
                    output.append(' ');
                }

                operators.push(token);
            }
            i++;
        }

        while (!operators.empty()) {
            output.append(operators.pop());
            output.append(' ');
        }

        return output.toString();
    }

    /**
     * Result of operation.
     * @param operator stands for operation we want to perform.
     * @param x first argument.
     * @param y second argument.
     * @return result of application.
     */

    private double application(char operator, double x, double y) {
        switch (operator) {
            case '+':
                return x + y;
            case '-':
                return x - y;
            case '*':
                return x * y;
            default:
                return x / y;
        }
    }

    /**
     * Evaluates expression.
     * @param input which we want to evaluate.
     * @return result of evaluation.
     */

    public double calculate(@NotNull String input) {
        for (int i = 0; i < input.length(); i++) {
            char token = input.charAt(i);
            if ('0' <= token && token <= '9') {
                numbers.push((double)(token - '0'));
            }
            else {
                double second = numbers.pop();
                double first = numbers.pop();
                numbers.push(application(token, first, second));
            }
        }

        return numbers.top();
    }
}