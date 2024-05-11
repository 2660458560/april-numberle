package com.mobaijun.service.impl;

import com.mobaijun.service.GameInterface;
import com.mobaijun.util.Observer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Random;
import java.util.Stack;

/**
 * Description: [game impl]
 * Author: [mobaijun]
 * Date: [2024/5/7 14:34]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class GameInterfaceImpl extends Observable implements GameInterface {

    /**
     * Array to store characters entered by the user.
     */
    private final String[] enter = new String[7];

    /**
     * Collection of formulas.
     */
    private final List<String> equations;

    /**
     * Current correct guess.
     */
    private String currentGuess;

    /**
     * Current line number.
     */
    private int lineNum = 0;

    /**
     * List of observers.
     */
    private final List<Observer> observers = new LinkedList<>();

    public GameInterfaceImpl(List<String> equations) {
        this.equations = equations;
        getCurrentGuess();
    }

    /**
     * Add the character entered by the user
     *
     * @Pre none
     * @Post adds the string s to the character array enter and does nothing if the array is full.
     */
    @Override
    public void processInput(String s) {
        for (int i = 0; i < enter.length; i++) {
            if (enter[i] == null) {
                enter[i] = s;
                break;
            }
        }
    }

    /**
     * Determine whether the user can continue to enter characters
     *
     * @return true or false
     * @Pre none.
     * @Post A return value of true means that you can continue to enter characters,
     * while a return value of false means that you cannot continue to enter characters.
     */
    @Override
    public Boolean checkEnter() {
        for (String s : enter) {
            if (s == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * User deletes characters
     *
     * @Pre none.
     * @Post removes the last non-null element of the array enter, or does nothing if the array is empty.
     */
    @Override
    public void back() {
        boolean check = true;
        for (int i = 0; i < enter.length; i++) {
            if (enter[i] == null) {
                if (i == 0) {
                    return;
                }
                enter[i - 1] = null;
                check = false;
            }
        }
        if (check) {
            enter[6] = null;
        }
        assert (enter[enter.length - 1] == null) || (enter[enter.length - 1].isEmpty()) :
                "The last element of the enter array should be null or the empty string.";
    }


    /**
     * Validates the expression entered by the user
     *
     * @return Validation result
     * @Pre: none.
     * @Post: returns an int array representing different validation results depending on the value of the array.
     */
    @Override
    public int[] checkEquation() {
        // Verify that the length of the equation is 7
        boolean checkLength = checkEnter();
        if (checkLength) {
            return new int[]{-4};
        }

        // Verify that the expression has an equal sign
        boolean hasEquals = false;
        for (String str : enter) {
            if (str.equals("=")) {
                hasEquals = true;
                break;
            }
        }
        if (!hasEquals) {
            return new int[]{-1};
        }

        // Verify that an expression has an operator
        boolean hasOperators = false;
        for (String str : enter) {
            if (isOperator(str)) {
                hasOperators = true;
                break;
            }
        }
        if (!hasOperators) {
            return new int[]{-2};
        }

        // Verify that the expression is correct
        int[] ints = new int[7];
        StringBuilder left = new StringBuilder();
        StringBuilder right = new StringBuilder();
        int equalIndex = 0;
        for (int i = 0; i < enter.length; i++) {
            if (enter[i].equals("=")) {
                equalIndex = i;
                break;
            } else {
                left.append(enter[i]);
            }
        }
        for (int i = equalIndex + 1; i < enter.length; i++) {
            right.append(enter[i]);
        }

        double evalLeft = calculateExpressionValue(left.toString());
        double evalRight = calculateExpressionValue(right.toString());

        // Verify that the values to the left and right of the expression are equal
        if (Math.abs(evalLeft - evalRight) < 1e-6) {
            char[] answer = currentGuess.toCharArray();
            char[] guess = (left + "=" + right).toCharArray();
            for (int i = 0; i < answer.length; i++) {
                if (answer[i] == guess[i]) {
                    // Characters are contained and in the correct position
                    ints[i] = 0;
                } else {
                    boolean found = false;
                    for (char c : answer) {
                        if (c == guess[i]) {
                            // Included but not in the right place
                            ints[i] = 1;
                            found = false;
                            break;
                        } else {
                            found = true;
                        }
                    }
                    if (found) {
                        // This character is not included
                        ints[i] = 2;
                    }
                }
            }

            boolean allCorrect = true;
            for (int anInt : ints) {
                if (anInt != 0) {
                    allCorrect = false;
                    break;
                }
            }
            if (allCorrect) {
                return new int[]{-6};
            } else {
                if (6 - (lineNum + 1) == 0) {
                    return new int[]{-5};
                }
            }
        } else {
            return new int[]{-3};
        }

        return ints;
    }

    /**
     * Method to evaluate an arithmetic expression
     *
     * @Pre The input parameter expression is a valid mathematical expression.
     * @Post The return value is the result of the expression calculation.
     */
    private double calculateExpressionValue(String expression) {
        assert expression != null : "Expression must not be null"; // Precondition
        assert expression.matches("[-+*/0-9.]+") : "Expression must be a valid mathematical expression"; // Precondition

        Stack<Double> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (Character.isDigit(ch) || (ch == '-' && (i == 0 || !Character.isDigit(expression.charAt(i - 1))))) {
                StringBuilder num = new StringBuilder();
                if (ch == '-' && (i == 0 || !Character.isDigit(expression.charAt(i - 1)))) {
                    // Handle negative numbers
                    num.append(ch);
                    i++;
                    while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                        num.append(expression.charAt(i));
                        i++;
                    }
                    i--;
                } else {
                    // Handle positive numbers
                    while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                        num.append(expression.charAt(i));
                        i++;
                    }
                    i--;
                }
                operands.push(Double.parseDouble(num.toString()));
            } else if (isOperator(Character.toString(ch))) {
                while (!operators.isEmpty() && hasPrecedence(ch, operators.peek())) {
                    operands.push(performOperation(operators.pop(), operands.pop(), operands.pop()));
                }
                operators.push(ch);
            }
        }
        while (!operators.isEmpty()) {
            operands.push(performOperation(operators.pop(), operands.pop(), operands.pop()));
        }
        double result = operands.pop();
        // Postcondition
        assert Double.isFinite(result) : "Result must be a finite number";
        return result;
    }

    /**
     * Method to check if a string represents an operator
     *
     * @Pre The input parameter str is a string.
     * @Post Returns true if the input string represents an operator; otherwise returns false.
     */
    private boolean isOperator(String str) {
        assert str != null : "Input string must not be null"; // Precondition
        return str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/");
    }


    /**
     * Method to check precedence of operators
     *
     * @Pre The input parameters op1 and op2 are two operators.
     * @Post Returns true if op1 has a higher priority than op2; otherwise returns false.
     */
    private boolean hasPrecedence(char op1, char op2) {
        // Precondition
        assert (op1 == '+' || op1 == '-' || op1 == '*' || op1 == '/') : "op1 must be a valid operator";
        assert (op2 == '+' || op2 == '-' || op2 == '*' || op2 == '/') : "op2 must be a valid operator";

        return (op2 == '*' || op2 == '/') && (op1 == '+' || op1 == '-') ||
                (op2 == '+' || op2 == '-') && (op1 == '+' || op1 == '-');
    }

    /**
     * Method to apply arithmetic operation
     *
     * @Pre operator is a valid operator. operand1 and operand2 are two valid operands.
     * @Post The return value is the result of the corresponding operation
     * performed on the operand according to the given operator.
     */
    private double performOperation(char operator, double operand2, double operand1) {
        // Precondition
        assert (operator == '+' || operator == '-' || operator == '*' || operator == '/') : "Operator must be a valid operator";

        double result = switch (operator) {
            case '+' -> operand1 + operand2;
            case '-' -> operand1 - operand2;
            case '*' -> operand1 * operand2;
            case '/' -> {
                if (operand2 == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                yield operand1 / operand2;
            }
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
        assert Double.isFinite(result) : "Result must be a finite number";
        return result;
    }


    /**
     * @return lineNum
     * @Pre none.
     * @Post returns the value of the lineNum property of the object.
     */
    @Override
    public int getLineNum() {
        return lineNum;
    }

    /**
     * @Pre The lineNum parameter passed in is greater than or equal to 0.
     * @Post Assigns the incoming lineNum parameter to the lineNum property of the object.
     */
    @Override
    public void setLineNum(int lineNum) {
        assert lineNum >= 0 : "The lineNum parameter passed in is greater than or equal to 0";
        this.lineNum = lineNum;
    }

    /**
     * @Pre none.
     * @Post Returns the value of the currentGuess attribute of the object.
     */
    @Override
    public String getCurrentGuess() {
        // Randomly choose a formula from the correct formula as the correct answer
        String result = currentGuess = equations.get(new Random().nextInt(equations.size()));
        assert result != null : "Returned value must not be null.";
        return result;
    }

    /**
     * Empty enter array
     *
     * @Pre none.
     * @Post Set all elements of the array enter to null.
     */
    @Override
    public void clear() {
        Arrays.fill(enter, null);
        assert Arrays.stream(enter).allMatch(Objects::isNull) : "All elements in the enter array should be null.";
    }

    /**
     * Start a new game
     *
     * @Pre none.
     * @Post Clear the array enter, reset the lineNum attribute to 0, and regenerate the currentGuess attribute.
     */
    @Override
    public void startNewGame() {
        clear();
        lineNum = 0;
        getCurrentGuess();
        //System.out.println(currentGuess);
        assert Arrays.stream(enter).allMatch(Objects::isNull) : "All elements in the enter array should be null.";
        assert lineNum == 0 : "The lineNum attribute should be reset to 0.";
        assert !currentGuess.equals(getCurrentGuess()) : "The currentGuess property should be updated";
    }

    /**
     * @Pre none.
     * @Post Returns enter array of the object.
     */
    @Override
    public String[] getEnteredStrings() {
        return enter;
    }

    /**
     * Add observer
     *
     * @Pre The passed observer parameter is not null.
     * @Post Adds the passed in observer object to the observers list.
     */
    @Override
    public void addObserver(Observer observer) {
        assert observer != null : "The passed observer parameter cannot be null";
        observers.add(observer);
        assert observers.contains(observer) : "The observers list should contain the passed-in observer objects.";
    }

    /**
     * Remove observer
     *
     * @Pre The passed observer parameter is not null.
     * @Post Removes the passed-in observer object from the observers list.
     */
    @Override
    public void removeObserver(Observer observer) {
        assert observer != null : "The passed observer parameter cannot be null";
        observers.remove(observer);
        assert !observers.contains(observer) : "The observers list should not contain the passed-in observer objects.";
    }

    /**
     * Update notify
     *
     * @Pre none.
     * @Post Notify all observer objects.
     */
    @Override
    public void notifyObserver() {
        // Notify every observer of the update
        for (com.mobaijun.util.Observer item : observers) {
            item.update();
        }
    }
}
