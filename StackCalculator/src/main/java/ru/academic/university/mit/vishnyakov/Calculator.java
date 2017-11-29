package ru.academic.university.mit.vishnyakov;

import org.jetbrains.annotations.NotNull;

/**
 * Class that represents our calculator.
 */

public class Calculator {

    /**
     * Stack that helps us parse the expression.
     */

    private Stack<Double> numbers;
    private Stack<Character> operators;

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
            else if (operators.empty() || operators.top() == '(') {
                operators.push(token);
            }
            else if (token == '(') {
                operators.push(token);
            }
            else if (token == ')') {
                while (operators.top() != '(') {
                    output.append(operators.pop());
                    output.append(' ');
                }
                operators.pop();
            }
            else {
                int currentPriority = priority(token);

                while (!operators.empty() && priority(operators.top()) >= currentPriority) {
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
     * Returns priority of the operand.
     * @param operator which priority we want to receive
     * @return priority of operator.
     */

    private int priority(char operator) {
        switch (operator) {
            case '(':
                return 0;
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 3;
        }
    }

    /**
     * Evalates the expression encoded in RPN.
     * @param expression to calculate.
     * @return result of evaluation.
     */

    double calculate(@NotNull String expression) {
        double firstOperand, secondOperand;
        String[] s = expression.split("\\s");
        for (String token : expression.split("\\s")) {

            switch (token) {
                case "+":

                    secondOperand = numbers.pop();
                    firstOperand = numbers.pop();

                    numbers.push(firstOperand + secondOperand);
                    break;
                case "-":

                    secondOperand = numbers.pop();
                    firstOperand = numbers.pop();

                    numbers.push(firstOperand - secondOperand);
                    break;
                case "*":

                    secondOperand = numbers.pop();
                    firstOperand = numbers.pop();

                    numbers.push(firstOperand * secondOperand);
                    break;
                case "/":

                    secondOperand = numbers.pop();
                    firstOperand = numbers.pop();

                    if (secondOperand == 0.0) {
                        throw new ArithmeticException("Cannot divide by zero!");
                    }

                    numbers.push(firstOperand / secondOperand);
                    break;
                case "^":

                    secondOperand = numbers.pop();
                    firstOperand = numbers.pop();

                    numbers.push(Math.pow(firstOperand, secondOperand));
                    break;
                default:
                    numbers.push(Double.parseDouble(token));
                    break;
            }

        }
        return numbers.top();
    }
}