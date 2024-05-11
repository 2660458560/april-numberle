// NumberleView.java

//Import necessary classes
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Map;
import java.util.Observer;
import java.util.Set;

/**
 * Represents the view of the Numberle game.
 */
public class NumberleView implements Observer {
    private final INumberleModel model; // Represents the Numberle model
    private final NumberleController controller; // Represents the Numberle controller
    private final JFrame frame = new JFrame("Numberle Game: Guess the Equation"); // The main game window frame
    private JLabel attemptsLabel; // Label component to display the number of attempts remaining
    private JPanel inputPanel; // Panel containing the input components
    private JLabel[] letterLabels; // Array of labels to display letters or symbols in the input panel
    private JPanel letterPanel; // Panel containing the letter buttons for the keyboard
    private JButton newGameButton; // Button to start a new game
    private KeyAdapter inputPanelKeyListener; // Key listener for keyboard input events in the input panel

    public String numberImagePath = "./resources/buttons/"; // Path to the directory of image icons
    public Map<Character, ImageIcon> numberIcons; // Map associating numbers with image icons
    public Map<Character, ImageIcon> operatorIcons; // Map associating operators with image icons
    private final int iconSize = 80; // Size (in pixels) of the image icons

    /**
     * Constructs a NumberleView object.
     *
     * @param model      The Numberle model.
     * @param controller The Numberle controller.
     */

    public NumberleView(INumberleModel model, NumberleController controller) {
        this.controller = controller; // Assign the provided controller to the 'controller' variable
        this.model = model; // Assign the provided model to the 'model' variable
        this.controller.startNewGame(); // Start a new game using the controller
        ((NumberleModel) this.model).addObserver(this); // Add this view as an observer to the model

        initializeFrame(); // Initialize the game frame

        this.controller.setView(); // Set the view using the controller
        update((NumberleModel) this.model, null); // Update the view with the initial model state

        loadImages(); // Load the image icons for numbers and operators
    }


    /**
     * Loads the image icons for numbers and operators.
     */
    private void loadImages() {
        // Load image icons for numbers '0' to '9'
        for (char c = '0'; c <= '9'; c++) {
            numberIcons.put(c, new ImageIcon(numberImagePath + c + ".png"));
        }

        // Load image icons for operators '+', '-', '×', '÷', and '='
        operatorIcons.put('+', new ImageIcon(numberImagePath + "+.png"));
        operatorIcons.put('-', new ImageIcon(numberImagePath + "-.png"));
        operatorIcons.put('×', new ImageIcon(numberImagePath + "×.png"));
        operatorIcons.put('÷', new ImageIcon(numberImagePath + "÷.png"));
        operatorIcons.put('=', new ImageIcon(numberImagePath + "=.png"));
    }

    /**
     * Scales the provided icon to the specified icon size.
     *
     * @param icon The icon to be scaled.
     * @return The scaled icon.
     */
    private ImageIcon scaleIcon(ImageIcon icon) {
        if (icon != null) {
            // Get the image from the icon and scale it using the specified icon size
            Image scaledImage = icon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);

            // Create and return a new ImageIcon with the scaled image
            return new ImageIcon(scaledImage);
        }

        // Return null if the provided icon is null
        return null;
    }

    /**
     * Initializes the frame for the game.
     */
    public void initializeFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the default close operation for the frame
        frame.setSize(960, 720); // Set the size of the frame (720p 4:3)
        frame.setLayout(new BorderLayout()); // Set the layout manager for the frame

        JLayeredPane layeredPane = new JLayeredPane(); // Create a layered pane
        layeredPane.setPreferredSize(frame.getSize()); // Set the preferred size of the layered pane to match the frame size

        setAttemptsLabel(); // Set up the attempts label
        setInputPanel(); // Set up the input panel
        setKeyboard(); // Set up the keyboard
        initializeNewGameButton(); // Initialize the new game button
        setInputPanelKeyListener(); // Set up the key listener for the input panel

        frame.setVisible(true); // Set the frame visibility to true
    }


    /**
     * Sets the attempts label on the frame.
     */
    private void setAttemptsLabel() {
        JPanel attemptsPanel = new JPanel(); // Create a new panel for the attempts label
        Font labelFont = new Font("Axure handwriting", Font.PLAIN, 18); // Create a custom font for the label

        // Create a new JLabel with HTML content for the welcome message and remaining attempts
        // This method can achieve line wrapping of text
        attemptsLabel = new JLabel("<html>Welcome to<br> Numberle! "
                + "<br><br> Attempts" + "<br> remaining: <br>"
                + controller.getRemainingAttempts() + " / 6 </html>");

        attemptsLabel.setFont(labelFont); // Set the font for the attempts label
        attemptsPanel.add(attemptsLabel); // Add the attempts label to the attempts panel
        frame.add(attemptsPanel, BorderLayout.EAST); // Add the attempts panel to the frame's east (right) side
    }


    /**
     * Sets the input panel on the frame.
     */
    private void setInputPanel() {
        inputPanel = new JPanel(); // Create a new panel for the input area
        inputPanel.setLayout(new GridLayout(6, 7)); // Set the layout manager for the input panel as a 6x7 grid

        Font labelFont = new Font("Axure handwriting", Font.BOLD, 40); // Create a custom font for the letter labels

        letterLabels = new JLabel[42]; // Create an array of JLabels to store the letter labels

        for (int i = 0; i < 42; i++) {
            JLabel label = new JLabel("", JLabel.CENTER); // Create a new JLabel with empty text, centered alignment
            label.setOpaque(true); // Set the label as opaque
            label.setBackground(Color.WHITE); // Set the background color of the label as white
            label.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Set a gray border for the label
            label.setFont(labelFont); // Set the font for the label
            label.setHorizontalAlignment(JLabel.CENTER); // Set the horizontal alignment of the label as center
            label.setVerticalAlignment(JLabel.CENTER); // Set the vertical alignment of the label as center

            inputPanel.add(label); // Add the label to the input panel
            letterLabels[i] = label; // Store the label in the letterLabels array
        }

        frame.add(inputPanel, BorderLayout.CENTER); // Add the input panel to the frame's center
    }

    /**
     * Sets the keyboard panel on the frame.
     */
    private void setKeyboard() {
        letterPanel = new JPanel(); // Create a new panel for the keyboard
        letterPanel.setLayout(new GridLayout(2, 1)); // Set the layout manager for the letter panel as a 2x1 grid

        JPanel row1Panel = new JPanel(); // Create a new panel for the first row of letters
        row1Panel.setLayout(new GridLayout(1, 10)); // Set the layout manager for row1Panel as a 1x10 grid

        Character[] row1Letters = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' }; // Define an array of characters for the first row of letters

        for (Character letter : row1Letters) {
            ImageIcon icon = scaleIcon(loadImageIcon(numberImagePath + letter + ".png")); // Load the image icon for the current letter and scale it
            if (icon != null) {
                CreateNewButtonWithIcon(row1Panel, letter, icon);
            }
        }

        letterPanel.add(row1Panel); // Add row1Panel to the letterPanel

        JPanel row2Panel = new JPanel(); // Create a new panel for the second row of letters
        row2Panel.setLayout(new GridLayout(1, 10)); // Set the layout manager for row2Panel as a 1x10 grid

        JButton backspaceButton = new JButton(scaleIcon(loadImageIcon(numberImagePath + "◀——.png"))); // Create a backspace button with the backspace icon
        if (backspaceButton.getIcon() != null) {
            backspaceButton.setPreferredSize(new Dimension(iconSize * 5, iconSize)); // Set the preferred size for the backspace button
            backspaceButton.setEnabled(true); // Enable the backspace button
            backspaceButton.addActionListener(e -> {
                String currentText = getInputText();
                if (!currentText.isEmpty()) {
                    updateInputPanel(currentText.substring(0, currentText.length() - 1)); // Remove the last character from the input panel
                }
            });
            backspaceButton.setContentAreaFilled(false); // Make the backspace button background transparent
            backspaceButton.setOpaque(false); // Ensure transparency of the backspace button
            row2Panel.add(backspaceButton); // Add the backspace button to row2Panel
        }

        Character[] row2Letters = { '+', '-', '×', '÷', '=' }; // Define an array of characters for the second row of letters

        for (Character letter : row2Letters) {
            ImageIcon icon = scaleIcon(loadImageIcon(numberImagePath + letter + ".png")); // Load the image icon for the current letter and scale it
            if (icon != null && (letter == '+' || letter == '-' || letter == '=')) {
                CreateNewButtonWithIcon(row2Panel, letter, icon);
            } else if (icon != null && (letter == '×')) {
                JButton button = new JButton(icon); // Create a new button with the icon
                button.setPreferredSize(new Dimension(iconSize, iconSize)); // Set the preferred size for the button
                button.setEnabled(true); // Enable the button
                button.addActionListener(e -> updateInputPanel(getInputText() + '*')); // Add an action listener to the button to update the input panel with '*'
                button.setContentAreaFilled(false); // Make the button background transparent
                button.setOpaque(false); // Ensure transparency of the button
                row2Panel.add(button); // Add the button to row2Panel
            } else if (icon != null) {
                JButton button = new JButton(icon); // Create a new button with the icon
                button.setPreferredSize(new Dimension(iconSize, iconSize)); // Set the preferred size for the button
                button.setEnabled(true); // Enable the button
                button.addActionListener(e -> updateInputPanel(getInputText() + '/')); // Add an action listener to the button to update the input panel with '/'
                button.setContentAreaFilled(false); // Make the button background transparent
                button.setOpaque(false); // Ensure transparency of the button
                row2Panel.add(button); // Add the button to row2Panel
            }
        }

        JButton enterButton = new JButton(scaleIcon(loadImageIcon(numberImagePath + "Enter.png"))); // Create an enter button with the enter icon
        if (enterButton.getIcon() != null) {
            enterButton.setPreferredSize(new Dimension(iconSize * 5, iconSize)); // Set the preferred size for the enter button
            enterButton.setEnabled(true); // Enable the enter button
            enterButton.addActionListener(e -> {
                if (controller.processInput(getInputText())) {
                    JOptionPane.showMessageDialog(null, "Invalid input!"); // Display an error message if the input is invalid
                }
            });
            enterButton.setContentAreaFilled(false); // Make the enter button background transparent
            enterButton.setOpaque(false); // Ensure transparency of the enter button
            row2Panel.add(enterButton); // Add the enter button to row2Panel
        }

        letterPanel.add(row2Panel); // Add row2Panel to the letterPanel

        frame.add(letterPanel, BorderLayout.SOUTH); // Add the letterPanel to the frame's south
    }

    private void CreateNewButtonWithIcon(JPanel row1Panel, Character letter, ImageIcon icon) {
        JButton button = new JButton(icon); // Create a new button with the icon
        button.setPreferredSize(new Dimension(iconSize, iconSize)); // Set the preferred size for the button
        button.setEnabled(true); // Enable the button
        button.addActionListener(e -> updateInputPanel(getInputText() + letter)); // Add an action listener to the button to update the input panel
        button.setContentAreaFilled(false); // Make the button background transparent
        button.setOpaque(false); // Ensure transparency of the button
        row1Panel.add(button); // Add the button to row1Panel
    }

    /**
     * Loads an image icon from the specified path.
     *
     * @param path The path to the image file.
     * @return The loaded image icon, or null if there was an error.
     */
    private ImageIcon loadImageIcon(String path) {
        try {
            ImageIcon icon = new ImageIcon(path); // Create a new image icon using the specified path
            if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) { // Check if the image loading was successful
                throw new IOException("Failed to load image at " + path); // Throw an exception if the image loading failed
            }
            return icon; // Return the loaded image icon
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage()); // Print an error message if there was an exception
            return null; // Return null to indicate that there was an error loading the image
        }
    }

    /**
     * Gets the current text entered by the player.
     *
     * @return The current text entered by the player.
     */
    private String getInputText() {
        int currentGuess = INumberleModel.MAX_ATTEMPTS - controller.getRemainingAttempts(); // Calculate the current guess number
        int startRow = currentGuess % 6; // Calculate the starting row index for the current guess
        int startIndex = startRow * 7; // Calculate the starting index in the letterLabels array for the current guess
        int endIndex = startIndex + 7; // Calculate the ending index in the letterLabels array for the current guess

        StringBuilder sb = new StringBuilder(); // Create a StringBuilder to store the input text
        for (int i = startIndex; i < endIndex; i++) {
            String text = letterLabels[i].getText(); // Get the text from the letter label at index i
            sb.append(text); // Append the text to the StringBuilder
        }
        return sb.toString(); // Convert the StringBuilder to a string and return the input text
    }

    /**
     * Updates the state of the input panel based on the current guess.
     */
    private void updateInputPanelState() {
        int lastGuess = INumberleModel.MAX_ATTEMPTS - controller.getRemainingAttempts() - 1; // Calculate the index of the last guess
        if (lastGuess >= 0) {
            int startRow = lastGuess % 6; // Calculate the starting row index for the last guess
            int startIndex = startRow * 7; // Calculate the starting index in the letterLabels array for the last guess
            int endIndex = startIndex + 7; // Calculate the ending index in the letterLabels array for the last guess

            String currentGuess = controller.getCurrentGuess().toString(); // Get the current guess as a string
            for (int i = startIndex; i < endIndex; i++) {
                switch (currentGuess.charAt(i % 7)) { // Retrieve the character at the corresponding index in the current guess
                    case '√' -> letterLabels[i].setBackground(Color.GREEN); // Set the background color of the letter label to green if the character is '√'
                    case '?' -> letterLabels[i].setBackground(Color.YELLOW); // Set the background color of the letter label to yellow if the character is '?'
                    case '×' -> letterLabels[i].setBackground(Color.LIGHT_GRAY); // Set the background color of the letter label to light gray if the character is '×'
                    default -> letterLabels[i].setBackground(Color.WHITE); // Set the background color of the letter label to white for any other character
                }
            }
        }
    }


    /**
     * Updates the input panel with the specified text.
     *
     * @param text The text to update the input panel with.
     */
    private void updateInputPanel(String text) {
        int currentGuess = INumberleModel.MAX_ATTEMPTS - controller.getRemainingAttempts(); // Calculate the current guess number
        int startRow = currentGuess % 6; // Calculate the starting row index for the current guess
        int startIndex = startRow * 7; // Calculate the starting index in the letterLabels array for the current guess
        int endIndex = startIndex + 7; // Calculate the ending index in the letterLabels array for the current guess

        for (int i = startIndex; i < endIndex; i++) {
            if (i - startIndex < text.length()) { // Check if there are remaining characters in the provided text
                letterLabels[i].setText(String.valueOf(text.charAt(i - startIndex))); // Set the text of the letter label to the corresponding character from the text
            } else {
                letterLabels[i].setText(""); // Clear the text of the letter label if there are no more characters in the text
            }
        }
    }


    /**
     * Initializes the "Start New Game" button.
     */
    private void initializeNewGameButton() {
        Font labelFont = new Font("Axure handwriting", Font.PLAIN, 18);

        newGameButton = new JButton("Start New Game"); // Create a new JButton with the label "Start New Game"
        newGameButton.setEnabled(false); // Disable the button initially
        newGameButton.setFont(labelFont); // Set the font of the button

        // Add an ActionListener to the button that performs actions when it is clicked
        newGameButton.addActionListener(e -> {
            controller.startNewGame(); // Call the startNewGame() method in the controller
            startNewGame(); // Call the startNewGame() method in the current class
            updateNewGameButton(); // Call the updateNewGameButton() method in the current class
        });

        JPanel buttonPanel = new JPanel(); // Create a new JPanel to hold the button
        buttonPanel.add(newGameButton); // Add the newGameButton to the buttonPanel
        frame.add(buttonPanel, BorderLayout.NORTH); // Add the buttonPanel to the frame's layout in the NORTH region
    }


    /**
     * Starts a new game by resetting the frame.
     */
    public void startNewGame() {
        frame.getContentPane().removeAll(); // Remove all components from the frame's content pane

        setAttemptsLabel(); // Set up the attempts label
        setInputPanel(); // Set up the input panel
        setKeyboard(); // Set up the keyboard
        initializeNewGameButton(); // Initialize the "Start New Game" button

        setInputPanelKeyListener(); // Set up the key listener for the input panel

        frame.revalidate(); // Revalidate the frame to reflect the changes
        frame.repaint(); // Repaint the frame to update the display
    }


    /**
     * Checks if the game is over and displays appropriate messages.
     */
    private void checkGameOver() {
        if (controller.isGameOver()) { // Check if the game is over
            setComponentsEnabled(letterPanel, false); // Disable the components in the letter panel
            inputPanel.removeKeyListener(inputPanelKeyListener); // Remove the key listener from the input panel

            if (controller.isGameWon()) { // Check if the game is won
                JOptionPane.showMessageDialog(null, "Congratulations! You won!\n(*^▽^*)\n, you can still start a new game"); // Display a congratulatory message
            } else {
                JOptionPane.showMessageDialog(null, "Sorry, you lost.\nThe target equation was " +
                        controller.getTargetEquation()+"\n Don't be upset, you can still start a new game\n(*￣︶￣)"); // Display a message indicating the game was lost
            }
        }
    }


    /**
     * Sets the enabled/disabled state of a component and its child components recursively.
     *
     * @param component The component to set the enabled state for.
     * @param enabled   The desired enabled state (true for enabled, false for disabled).
     */
    private void setComponentsEnabled(Component component, boolean enabled) {
        component.setEnabled(enabled); // Set the enabled state of the current component

        if (component instanceof Container) { // Check if the current component is a container (such as a JPanel)
            Component[] components = ((Container) component).getComponents(); // Get the child components of the container
            for (Component childComponent : components) { // Iterate over the child components
                setComponentsEnabled(childComponent, enabled); // Recursively call setComponentsEnabled() for each child component
            }
        }
    }


    /**
     * Updates the state of the "Start New Game" button based on the number of remaining attempts.
     */
    private void updateNewGameButton() {
        // Enable the "Start New Game" button if there is at least one remaining attempt
        newGameButton.setEnabled(INumberleModel.MAX_ATTEMPTS - controller.getRemainingAttempts() >= 1);
    }

    /**
     * Sets up the key listener for the input panel.
     */
    private void setInputPanelKeyListener() {
        inputPanelKeyListener = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!isValidInput(c)) { // Check if the typed character is a valid input
                    e.consume(); // Consume the event to prevent the character from being entered
                } else if (c == KeyEvent.VK_ENTER) { // Check if the Enter key is pressed
                    if (controller.processInput(getInputText())) { // Process the input and check if it is invalid
                        JOptionPane.showMessageDialog(null, "Invalid input!"); // Display a message for invalid input
                    }
                } else if (c == KeyEvent.VK_BACK_SPACE) { // Check if the Backspace key is pressed
                    String currentText = getInputText(); // Get the current text in the input panel
                    if (!currentText.isEmpty()) { // Check if the current text is not empty
                        updateInputPanel(currentText.substring(0, currentText.length() - 1)); // Update the input panel by removing the last character
                    }
                } else {
                    updateInputPanel(getInputText() + c); // Update the input panel by appending the typed character
                }
            }

            private boolean isValidInput(char c) {
                return (c >= '0' && c <= '9') || (c == '+' || c == '-' || c == '*' || c == '/' || c == '=')
                        || c == KeyEvent.VK_BACK_SPACE
                        || c == KeyEvent.VK_ENTER;
            }
        };

        inputPanel.addKeyListener(inputPanelKeyListener); // Add the key listener to the input panel
        inputPanel.setFocusable(true); // Set the input panel as focusable
        inputPanel.requestFocusInWindow(); // Request focus for the input panel
    }


    /**
     * Updates the attempts label with the remaining attempts.
     */
    private void updateAttemptsLabel() {
        // Create a font for the label
        Font labelFont = new Font("Axure handwriting", Font.PLAIN, 18);
        // Set the text of the attempts label with HTML formatting
        attemptsLabel.setText("<html>Welcome to<br> Numberle! <br><br>Attempts" + "<br> remaining: <br>" +
                controller.getRemainingAttempts() + " / 6 </html>");
        // Set the font of the attempts label
        attemptsLabel.setFont(labelFont);
    }

    /**
     * Updates the keyboard buttons with different background colors based on their letter status.
     */
    private void updateKeyboard() {
        Set<String> greyLetters = model.getGreyLetters(); // Get the set of grey letters
        Set<String> yellowLetters = model.getYellowLetters(); // Get the set of yellow letters
        Set<String> greenLetters = model.getGreenLetters(); // Get the set of green letters

        for (Component component : letterPanel.getComponents()) { // Iterate over the components in the letter panel
            if (component instanceof JPanel rowPanel) { // Check if the component is a row panel
                for (Component buttonComponent : rowPanel.getComponents()) { // Iterate over the components in the row panel
                    if (buttonComponent instanceof JButton button) { // Check if the component is a button
                        String letter = button.getText().toLowerCase(); // Get the text of the button (letter)
                        Color backgroundColor = null; // Initialize the background color variable

                        // Set the background color based on the letter status
                        if (greenLetters.contains(letter)) {
                            // Set the background color to green if the letter is in the greenLetters set.
                            backgroundColor = Color.GREEN;
                        } else if (yellowLetters.contains(letter)) {
                            // Set the background color to yellow if the letter is in the yellowLetters set.
                            backgroundColor = Color.YELLOW;
                        } else if (greyLetters.contains(letter)) {
                            // Set the background color to gray if the letter is in the greyLetters set.
                            backgroundColor = Color.GRAY;
                        }

                        button.setBackground(backgroundColor); // Set the background color of the button
                    }
                }
            }
        }
    }
    @Override
    /*
      Update method called by an Observable object.

      @param o   The Observable object that triggered the update.
     * @param arg An optional argument passed by the Observable object.
     */
    public void update(java.util.Observable o, Object arg) {
        updateAttemptsLabel(); // Update the attempts label
        updateInputPanelState(); // Update the state of the input panel
        updateKeyboard(); // Update the keyboard
        updateNewGameButton(); // Update the "Start New Game" button
        checkGameOver(); // Check if the game is over
    }
}