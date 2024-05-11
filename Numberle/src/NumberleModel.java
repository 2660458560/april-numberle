// NumberleModel.java
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Represents the model of the Numberle game.
 */
public class NumberleModel extends Observable implements INumberleModel {
    private String targetNumber;               // The target number that the player needs to guess
    private StringBuilder currentGuess;        // The current guess made by the player
    private int remainingAttempts;             // The number of remaining attempts for the player
    private boolean gameWon;                   // Indicates whether the game has been won

    private final Set<String> greyLetters = new HashSet<>();    // Set of characters that are not in the target number
    private final Set<String> yellowLetters = new HashSet<>();  // Set of characters that are in the target number but not in the correct position
    private final Set<String> greenLetters = new HashSet<>();   // Set of characters that are in the correct position
    public char symbolMatched = '√';             // Symbol to represent a character that is in the correct position
    public char symbolNotInThePlace = '?';       // Symbol to represent a character that is in the target number but not in the correct position
    public char symbolNotExist = '×';            // Symbol to represent a character that is not in the target number

    /**
     * Initializes the game with default settings.
     */
    @Override
    public void initialize() {
        if (!FLAG_RANDOM_SELECT) {
            targetNumber = "6+4=2*5";   // Default target number if random selection is disabled
        } else {
            Set<String> equations = readFile();
            Random rand = new Random();
            int index = rand.nextInt(equations.size());
            targetNumber = new ArrayList<>(equations).get(index);   // Randomly selects a target number from the set of equations
        }

        currentGuess = new StringBuilder("       ");   // Initializes the current guess with spaces
        remainingAttempts = MAX_ATTEMPTS;   // Sets the remaining attempts to the maximum allowed
        gameWon = false;   // Initializes the game as not won
        setChanged();
        notifyObservers();

    }

    /**
     * Processes the player's input guess.
     *
     * @param input The player's input guess.
     * @return True if the input is processed successfully, otherwise false.
     */
    @Override
    public boolean processInput(String input) {
        if (FLAG_SHOW_ERROR_EQUATION) {
            if (!validEquation(input)) {
                return false;
            }
        }

        char[] targetArray = targetNumber.toCharArray();
        char[] inputArray = input.toCharArray();

        for (int i = 0; i < targetArray.length; i++) {
            if (targetArray[i] == inputArray[i]) {
                currentGuess.setCharAt(i, symbolMatched);   // Sets the symbol for a character in the correct position
                greenLetters.add(String.valueOf(inputArray[i]));   // Adds the character to the set of characters in the correct position
            } else if (targetNumber.contains(String.valueOf(inputArray[i]))) {
                currentGuess.setCharAt(i, symbolNotInThePlace);   // Sets the symbol for a character in the target number but not in the correct position
                yellowLetters.add(String.valueOf(inputArray[i]));   // Adds the character to the set of characters not in the correct position
            } else {
                currentGuess.setCharAt(i, symbolNotExist);   // Sets the symbol for a character not in the target number
                greyLetters.add(String.valueOf(inputArray[i]));   // Adds the character to the set of characters not in the target number
            }
        }
        remainingAttempts--;   // Decreases the remaining attempts

        if (currentGuess.toString().equals("√".repeat(EQUATION_LENGTH))) {
            gameWon = true;   // If the current guess matches the target number, the game is won
        }
        setChanged();
        notifyObservers();

        return true;
    }
    /**
     * Checks if the game is over.
     *
     * @return True if the game is over, otherwise false.
     */
    @Override
    public boolean isGameOver() {
        // Returns true if the remaining attempts are zero or the game has been won
        return remainingAttempts <= 0 || gameWon;
    }

    /**
     * Checks if the game is won.
     *
     * @return True if the game is won, otherwise false.
     */
    @Override
    public boolean isGameWon() {
        // Returns true if the game has been won
        return gameWon;
    }

    /**
     * Gets the target number that the player needs to guess.
     *
     * @return The target number.
     */
    @Override
    public String getTargetNumber() {
        // Returns the target number
        return targetNumber;
    }

    /**
     * Gets the current guess made by the player.
     *
     * @return The current guess.
     */
    @Override
    public StringBuilder getCurrentGuess() {
        // Returns the current guess
        return currentGuess;
    }

    /**
     * Gets the number of remaining attempts for the player.
     *
     * @return The number of remaining attempts.
     */
    @Override
    public int getRemainingAttempts() {
        return remainingAttempts;   // Returns the number of remaining attempts
    }

    /**
     * Starts a new game with default settings.
     */
    @Override
    public void startNewGame() {
        initialize();   // Starts a new game by reinitializing the model
    }

    /**
     * Gets the set of grey letters in the current guess.
     *
     * @return The set of grey letters.
     */
    @Override
    public Set<String> getGreyLetters() {
        return greyLetters;   // Returns the set of characters not in the target number
    }

    /**
     * Gets the set of gold letters in the current guess.
     *
     * @return The set of gold letters.
     */
    @Override
    public Set<String> getYellowLetters() {
        return yellowLetters;   // Returns the set of characters in the target number but not in the correct position
    }

    /**
     * Gets the set of green letters in the current guess.
     *
     * @return The set of green letters.
     */
    @Override
    public Set<String> getGreenLetters() {
        return greenLetters;   // Returns the set of characters in the correct position
    }

    /**
     * Reads equations from a file and returns them as a set of strings.
     *
     * @return A set of equations read from a file.
     */
    private Set<String> readFile() {
        Set<String> result = new HashSet<>(); // Create a new HashSet to store the read equations.
        try {
            Scanner sc = new Scanner(new File(GUESS_EQUATIONS_FILE));// Create a new Scanner to read the file.
            // Iterate over each line in the file.
            while (sc.hasNextLine()) {
                String line = sc.nextLine().strip(); // Read the next line and remove leading/trailing whitespace.
                if (!line.isEmpty()) { // Check if the line is not empty.
                    result.add(line); // Add the non-empty line to the set of equations.
                }
            }
        } catch (FileNotFoundException e) { // Handle the case where the file is not found.
            e.printStackTrace(); // Print the error stack trace.
        }
        return result; // Return the set of non-empty equations.
    }

    /**
     * Checks if the provided equation is valid.
     *
     * @param equation The equation to validate.
     * @return True if the equation is valid, otherwise false.
     */
    private boolean validEquation(String equation) {
        //If any of these conditions are not met, the method returns false.
        // The equation must be exactly 7 characters long and contain the equals sign "=".
        if (equation.length() != 7 || !equation.contains("=")) {
            return false;
        }

        // The equation should not have "=" as the first or last character.
        if (equation.charAt(0) == '=' || equation.charAt(6) == '=') {
            return false;
        }

        String[] parts = equation.split("="); // Split the equation into two parts: left expression and right expression.
        String leftExpression = parts[0]; // Extract the left expression.
        String rightExpression = parts[1]; // Extract the right expression.

        System.out.println("----------------------------------------\n"+"Left Expression: " + leftExpression); // Print the left expression for debugging.
        System.out.println("Right Expression: " + rightExpression); // Print the right expression for debugging.

        // Evaluate the left and right expressions and check if their values are equal.
        return evaluateExpression(leftExpression) == evaluateExpression(rightExpression);
    }

    /**
     * Evaluates the value of a mathematical expression.
     *
     * @param expression The mathematical expression to evaluate.
     * @return The evaluated value of the expression.
     */
    private int evaluateExpression(String expression) {
        int result = 0; // Stores the final result of the expression.
        int num = 0; // Stores the currently parsed number.
        int prevNum = 0; // Stores the previous number in the expression.
        char operator = '+'; // Stores the current operator.
        char prevOperator = '+'; // Stores the previous operator.

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i); // Get the current character in the expression.

            if (Character.isDigit(c)) { // Check if the character is a digit.
                num = num * 10 + (c - '0'); // Accumulate the digit to form a number.
            } else if (c == '+' || c == '-') { // Check if the character is an addition or subtraction operator.
                // Apply the previous operator to the previous number.
                if (prevOperator == '*') {
                    prevNum *= num;
                } else if (prevOperator == '/') {
                    if (num == 0) {
                        throw new ArithmeticException("Division by zero"); // Division by zero is not allowed.
                    }
                    prevNum /= num;
                } else {
                    prevNum = num;
                }

                // Apply the current operator to the accumulated result.
                if (operator == '+') {
                    result += prevNum;
                } else {
                    result -= prevNum;
                }

                // Update the operators and reset the number.
                operator = c;
                prevOperator = '+';
                num = 0;
            } else if (c == '*' || c == '/') { // Check if the character is a multiplication or division operator.
                // Apply the previous multiplication or division if any.
                if (prevOperator == '*') {
                    prevNum *= num;
                } else if (prevOperator == '/') {
                    if (num == 0) {
                        throw new ArithmeticException("Division by zero"); // Division by zero is not allowed.
                    }
                    prevNum /= num;
                } else {
                    prevNum = num;
                }

                // Update the previous operator to the current one and reset the number.
                prevOperator = c;
                num = 0;
            }
        }

        // Apply the last number with the last operator
        if (prevOperator == '*') {
            prevNum *= num;
        } else if (prevOperator == '/') {
            if (num == 0) {
                // Division by zero is not allowed.
                throw new ArithmeticException("Division by zero");
            }
            prevNum /= num;
        } else {
            prevNum = num;
        }

        // Apply the final result with the last operator
        if (operator == '+') {
            result += prevNum;
        } else {
            result -= prevNum;
        }

        return result; // Return the final result of the expression.
    }

}
