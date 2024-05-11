// CLIApp.java

//Import necessary classes
import java.util.Scanner;

/**
 * Represents a command-line interface (CLI) application for playing the Numberle game.
 * The user interacts with the game through the command line.
 * This class provides the main entry point for running the game.
 */
public class CLIApp {
    private final INumberleModel model; // The game model used for managing the game state and processing user input.
    private final Scanner scanner; // The Scanner object used for reading user input from the command line.

    /**
     * Constructs a CLIApp object with a new instance of NumberleModel and a Scanner for user input.
     */
    public CLIApp() {
        model = new NumberleModel(); // Create a new instance of NumberleModel as the game model.
        scanner = new Scanner(System.in); // Create a new Scanner object to read user input from the command line.
    }

    /**
     * Starts the Numberle game.
     * This method displays welcome messages, handles user input, and controls the game flow.
     */
    public void startGame() {
        // Display welcome messages and instructions to the user
        System.out.println("""
                Welcome to Numberle Game!
                ----------------------------------------
                What you need to do is to find
                a hidden mathematical equation
                through at most 6 guesses.
                After each guess,
                some feedback will be provided to
                help you gradually narrow down the scope.
                Let's start!
                ----------------------------------------""");

        model.startNewGame(); // Initialize a new game using the game model.

        while (!model.isGameOver()) {
            // Prompt the user for their guess and process it
            System.out.println("You have " + model.getRemainingAttempts()+"/6 attempts remained");
            System.out.print("Enter your guess: ");
            String guess = scanner.nextLine();

            boolean valid = model.processInput(guess); // Process the user's guess using the game model.

            if (valid) {
                // Provide feedback to the user based on their guess
                System.out.println("----------------------------------------\nFor your guess this time, there is a hint: \n\n                    " + model.getCurrentGuess().toString()+"\n\n√ means correct digit or operator at the right place;"+
                        "\n? means digit or operator exists but not here;"+
                        "\n× means it does not appear in this equation."
                );
            } else {
                System.out.println("Invalid equation.");
            }

            System.out.println("----------------------------------------"); // Print a separator line
        }

        if (model.isGameWon()) {
            System.out.println("Congratulations! You won the game!"); // Print victory message
        } else {
            System.out.println("Game over! You ran out of attempts. The target equation was: " + model.getTargetNumber());
        }

        // Ask the user if they want to play again
        System.out.println("Do you want to play again? (yes/no)");
        String playAgain = scanner.nextLine();

        if (playAgain.equalsIgnoreCase("yes")) {
            startGame(); // Restart the game if the user wants to play again
        } else {
            System.out.println("Thank you for playing Numberle Game!");
        }
    }

    /**
     * The main entry point of the application.
     * Creates an instance of CLIApp and starts the game.
     *
     * @param args The command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        CLIApp game = new CLIApp(); // Create a new instance of CLIApp.
        game.startGame(); // Start the game by calling the startGame() method.
    }
}
