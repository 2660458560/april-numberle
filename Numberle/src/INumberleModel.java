// INumberleModel.java

//Import necessary classes
import java.util.Set;

/**
 *The INumberleModel interface defines the basic behavior of the Numberle game model.
 */
public interface INumberleModel {
    int MAX_ATTEMPTS = 6; // Maximum number of attempts in the game
    int EQUATION_LENGTH = 7; // Length of the number puzzle
    String GUESS_EQUATIONS_FILE = "equations.txt"; // File name to store guessed equations
    boolean FLAG_SHOW_ERROR_EQUATION = true; // Whether to show incorrect equations
    boolean FLAG_RANDOM_SELECT = true; // Whether to select equations randomly

    /**
     * Initializes the game model.
     */
    void initialize();

    /**
     * Processes user input.
     *
     * @param input the user input string
     * @return true if the input is valid, false otherwise
     */
    boolean processInput(String input);

    /**
     * Checks if the game is over.
     *
     * @return true if the game is over, false otherwise
     */
    boolean isGameOver();

    /**
     * Checks if the game is won.
     *
     * @return true if the game is won, false otherwise
     */
    boolean isGameWon();

    /**
     * Gets the target number.
     *
     * @return the string representation of the target number
     */
    String getTargetNumber();

    /**
     * Gets the current guess equation.
     *
     * @return the StringBuilder object representing the current guess equation
     */
    StringBuilder getCurrentGuess();

    /**
     * Gets the remaining attempts.
     *
     * @return the number of remaining attempts
     */
    int getRemainingAttempts();

    /**
     * Starts a new game.
     */
    void startNewGame();

    /**
     * Gets the set of grey letters.
     *
     * @return the set of grey letters
     */
    Set<String> getGreyLetters();

    /**
     * Gets the set of yellow letters.
     *
     * @return the set of yellow letters
     */
    Set<String> getYellowLetters();

    /**
     * Gets the set of green letters.
     *
     * @return the set of green letters
     */
    Set<String> getGreenLetters();
}