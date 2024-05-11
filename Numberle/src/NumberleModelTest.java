import org.testng.annotations.Test;   // Importing the necessary classes for JUnit testing
import java.util.Set;   // Importing the Set interface
import static org.junit.jupiter.api.Assertions.*;   // Importing static assertion methods

class NumberleModelTest {
    @Test
    void processInput_ValidInput_Test() {
        NumberleModel model = new NumberleModel();   // Creating an instance of the NumberleModel class
        model.initialize();   // Initializing the model

        // Testing valid input
        String targetNumber = model.getTargetNumber();   // Getting the target number from the model
        System.out.println("Target Equations: "+targetNumber);
        assertTrue(model.processInput(targetNumber), "Valid input should return true");   // Asserting that processing valid input returns true
        assertTrue(model.isGameWon(), "Game should be won after correct input");   // Asserting that the game is won after correct input
    }

    @Test
    void processInput_InvalidInput_Test() {
        NumberleModel model = new NumberleModel();   // Creating an instance of the NumberleModel class
        model.initialize();   // Initializing the model

        // Testing invalid input
        String targetNumber = model.getTargetNumber();   // Getting the target number from the model
        String invalidInput = targetNumber.replace('+', '-');   // Creating an invalid input by replacing '+' with '-'
        assertFalse(model.processInput(invalidInput), "Invalid input should return false");   // Asserting that processing invalid input returns false
        assertFalse(model.isGameWon(), "Game should not be won after invalid input");   // Asserting that the game is not won after invalid input
    }

    @Test
    void isGameOver_GameNotOver_Test() {
        NumberleModel model = new NumberleModel();   // Creating an instance of the NumberleModel class
        model.initialize();   // Initializing the model

        // Testing game not over
        assertFalse(model.isGameOver(), "Game should not be over at the start");   // Asserting that the game is not over at the start
    }

    @Test
    void getGreyLetters_Test() {
        NumberleModel model = new NumberleModel();   // Creating an instance of the NumberleModel class
        model.initialize();   // Initializing the model

        // Testing grey letters
        Set<String> greyLetters = model.getGreyLetters();   // Getting the set of grey letters from the model
        System.out.println("greyLetters: "+greyLetters);
        assertTrue(greyLetters.isEmpty(), "Grey letters should be empty at initialization");   // Asserting that the set of grey letters is empty at initialization
    }

    @Test
    void getYellowLetters_Test() {
        NumberleModel model = new NumberleModel();   // Creating an instance of the NumberleModel class
        model.initialize();   // Initializing the model

        // Testing yellow letters
        Set<String> yellowLetters = model.getYellowLetters();   // Getting the set of yellow letters from the model
        System.out.println("yellowLetters: "+yellowLetters);
        assertTrue(yellowLetters.isEmpty(), "Yellow letters should be empty at initialization");   // Asserting that the set of yellow letters is empty at initialization
    }

    @Test
    void getGreenLetters_Test() {
        NumberleModel model = new NumberleModel();   // Creating an instance of the NumberleModel class
        model.initialize();   // Initializing the model

        // Testing green letters
        Set<String> greenLetters = model.getGreenLetters();   // Getting the set of green letters from the model
        System.out.println("greenLetters: "+greenLetters);
        assertTrue(greenLetters.isEmpty(), "Green letters should be empty at initialization");   // Asserting that the set of green letters is empty at initialization
    }
}