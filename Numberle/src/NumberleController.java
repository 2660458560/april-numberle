// NumberleController.java

/**
 * The controller class for the Numberle game.
 */
public class NumberleController {
    private final INumberleModel model;

    /**
     * Constructs a NumberleController object with the specified model.
     *
     * @param model The model to be associated with the controller.
     */
    public NumberleController(INumberleModel model) {
        this.model = model;
    }

    /**
     * Sets the view for the controller.
     *
     */
    public void setView() {
    }

    /**
     * Processes the player's input guess.
     *
     * @param input The player's input guess.
     * @return True if the input is processed successfully, otherwise false.
     */
    public boolean processInput(String input) {
        return !model.processInput(input);
    }

    /**
     * Checks if the game is over.
     *
     * @return True if the game is over, otherwise false.
     */
    public boolean isGameOver() {
        return model.isGameOver();
    }

    /**
     * Checks if the game is won.
     *
     * @return True if the game is won, otherwise false.
     */
    public boolean isGameWon() {
        return model.isGameWon();
    }

    /**
     * Gets the target number that the player needs to guess.
     *
     * @return The target number.
     */
    public String getTargetEquation() {
        return model.getTargetNumber();
    }

    /**
     * Gets the current guess made by the player.
     *
     * @return The current guess.
     */
    public StringBuilder getCurrentGuess() {
        return model.getCurrentGuess();
    }

    /**
     * Gets the number of remaining attempts for the player.
     *
     * @return The number of remaining attempts.
     */
    public int getRemainingAttempts() {
        return model.getRemainingAttempts();
    }

    /**
     * Starts a new game.
     */
    public void startNewGame() {
        model.startNewGame();
    }
}