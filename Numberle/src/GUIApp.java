// GUIApp.java

//Import necessary classes
import javax.swing.*;

/**
 * A GUI application that creates and displays the Numberle game GUI.
 */
public class GUIApp {

    /**
     * The entry point of the GUIApp application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                GUIApp::createAndShowGUI
        );
    }

    /**
     * Creates and shows the Numberle game GUI.
     */
    public static void createAndShowGUI() {
        // Create a new instance of the NumberleModel.
        INumberleModel model = new NumberleModel();
        // Create a new instance of the NumberleController, passing the model.
        NumberleController controller = new NumberleController(model);
        // Create a new instance of the NumberleView, passing the model and controller.
        new NumberleView(model, controller);
    }
}
