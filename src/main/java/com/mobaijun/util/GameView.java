package com.mobaijun.util;

import com.mobaijun.controller.GameController;
import com.mobaijun.service.impl.GameInterfaceImpl;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import lombok.Setter;


/**
 * Description: [game view]
 * Author: [mobaijun]
 * Date: [2024/5/7 15:25]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class GameView extends JFrame implements Observer {

    /**
     * This class represents the graphical user interface (GUI) for the game.
     * It contains components such as input boxes and virtual buttons.
     * -- SETTER --
     * Sets the GameController object associated with this view.
     */
    @Setter
    private GameController control;

    /**
     * The model of the game interface implementation.
     */
    private final GameInterfaceImpl model;

    /**
     * Input boxes for user input.
     */
    private List<JTextField> jTextFields;

    /**
     * Virtual buttons for user interaction.
     */
    private List<JButton> jButtons;

    /**
     * Constructs a new GameView with the provided controller and model.
     *
     * @param control the GameController object associated with this view
     * @param model   the GameInterfaceImpl object associated with this view
     */
    public GameView(GameController control, GameInterfaceImpl model) {
        this.control = control;
        this.model = model;
        initializeFrame(this);
    }

    /**
     * Initialisation Interface
     *
     * @param jFrame
     * @Pre: jFrame should be a valid JFrame instance.
     * @Post: Initializes the frame layout, adds components like text fields,
     * buttons, and sets up event listeners for keyboard and button clicks.
     */
    public void initializeFrame(JFrame jFrame) {
        JPanel jPanel = new JPanel();//middle panel

        jTextFields = new ArrayList<>();//Input Box Collection
        jButtons = new ArrayList<>();

        //Add Input Box
        for (int i = 0; i < 42; i++) {
            JTextField jTextField = new JTextField();
            jTextField.setPreferredSize(new Dimension(100, 100));
            jTextField.setHorizontalAlignment(JTextField.CENTER);
            jTextField.setFont(new Font("12", 23, 33));
            jTextField.setFocusable(false);
            jTextField.setEditable(false);
            jTextFields.add(jTextField);
            jPanel.add(jTextField);
        }


        //Setting up the virtual keyboard
        JButton jButton = new JButton("0");
        jButton.setPreferredSize(new Dimension(80, 30));
        jButton.setFont(new Font("Arial", Font.BOLD, 16));
        JButton jButton1 = new JButton("1");
        jButton1.setPreferredSize(new Dimension(80, 30));
        jButton1.setFont(new Font("Arial", Font.BOLD, 16));
        JButton jButton2 = new JButton("2");
        jButton2.setPreferredSize(new Dimension(80, 30));
        jButton2.setFont(new Font("Arial", Font.BOLD, 16));
        JButton jButton3 = new JButton("3");
        jButton3.setPreferredSize(new Dimension(80, 30));
        jButton3.setFont(new Font("Arial", Font.BOLD, 16));
        JButton jButton4 = new JButton("4");
        jButton4.setPreferredSize(new Dimension(80, 30));
        jButton4.setFont(new Font("Arial", Font.BOLD, 16));
        JButton jButton5 = new JButton("5");
        jButton5.setPreferredSize(new Dimension(80, 30));
        jButton5.setFont(new Font("Arial", Font.BOLD, 16));
        JButton jButton6 = new JButton("6");
        jButton6.setPreferredSize(new Dimension(80, 30));
        jButton6.setFont(new Font("Arial", Font.BOLD, 16));
        JButton jButton7 = new JButton("7");
        jButton7.setPreferredSize(new Dimension(80, 30));
        jButton7.setFont(new Font("Arial", Font.BOLD, 16));
        JButton jButton8 = new JButton("8");
        jButton8.setPreferredSize(new Dimension(80, 30));
        jButton8.setFont(new Font("Arial", Font.BOLD, 16));
        JButton jButton9 = new JButton("9");
        jButton9.setPreferredSize(new Dimension(80, 30));
        jButton9.setFont(new Font("Arial", Font.BOLD, 16));
        JButton jButton10 = new JButton("+");
        jButton10.setPreferredSize(new Dimension(80, 30));
        jButton10.setFont(new Font("Arial", Font.BOLD, 22));
        JButton jButton11 = new JButton("-");
        jButton11.setPreferredSize(new Dimension(80, 30));
        jButton11.setFont(new Font("Arial", Font.BOLD, 22));
        JButton jButton12 = new JButton("*");
        jButton12.setPreferredSize(new Dimension(80, 30));
        jButton12.setFont(new Font("Arial", Font.BOLD, 22));
        JButton jButton13 = new JButton("/");
        jButton13.setPreferredSize(new Dimension(80, 30));
        jButton13.setFont(new Font("Arial", Font.BOLD, 22));
        JButton jButton14 = new JButton("=");
        jButton14.setPreferredSize(new Dimension(80, 30));
        jButton14.setFont(new Font("Arial", Font.BOLD, 22));
        JButton jButton15 = new JButton("Back");
        jButton15.setPreferredSize(new Dimension(80, 30));
        jButton15.setFont(new Font("Arial", Font.BOLD, 16));
        JButton jButton16 = new JButton("Enter");
        jButton16.setPreferredSize(new Dimension(160, 30));
        jButton16.setFont(new Font("Arial", Font.BOLD, 16));

        // Add buttons to restart and end the game
        JButton restartButton = new JButton("Restart");
        restartButton.setPreferredSize(new Dimension(120, 40));
        restartButton.setFont(new Font("Arial", Font.BOLD, 16));
        JButton exitButton = new JButton("End");
        exitButton.setPreferredSize(new Dimension(120, 40));
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));


        jButtons.add(jButton);
        jButtons.add(jButton1);
        jButtons.add(jButton2);
        jButtons.add(jButton3);
        jButtons.add(jButton4);
        jButtons.add(jButton5);
        jButtons.add(jButton6);
        jButtons.add(jButton7);
        jButtons.add(jButton8);
        jButtons.add(jButton9);
        jButtons.add(jButton10);
        jButtons.add(jButton11);
        jButtons.add(jButton12);
        jButtons.add(jButton13);
        jButtons.add(jButton14);
        jButtons.add(jButton15);
        jButtons.add(jButton16);
        jButtons.add(restartButton);
        jButtons.add(exitButton);


        // Create a panel to hold the restart and end game buttons
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
        controlPanel.setPreferredSize(new Dimension(880, 60));
        controlPanel.add(restartButton);
        controlPanel.add(exitButton);

        //Adding a virtual keyboard to a page
        JPanel jPanel1 = new JPanel(new GridLayout(3, 6, 10, 10)); // 4 行 4 列，间隔为 10 像素
        // Adding buttons to the virtual keyboard panel
        jPanel1.add(jButton);
        jPanel1.add(jButton1);
        jPanel1.add(jButton2);
        jPanel1.add(jButton3);
        jPanel1.add(jButton4);
        jPanel1.add(jButton5);
        jPanel1.add(jButton6);
        jPanel1.add(jButton7);
        jPanel1.add(jButton8);
        jPanel1.add(jButton9);
        jPanel1.add(jButton10);
        jPanel1.add(jButton11);
        jPanel1.add(jButton12);
        jPanel1.add(jButton13);
        jPanel1.add(jButton14);
        jPanel1.add(jButton15);
        jPanel1.add(jButton16);


        // Adding the Control Panel and Virtual Keyboard Panel to the Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(jPanel1, BorderLayout.CENTER);

        jPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 20));//Setting the input box layout and spacing
        jPanel.setPreferredSize(new Dimension(880, 220));//Setting the Input Box Panel Size
        mainPanel.setPreferredSize(new Dimension(880, 200));//Setting the virtual keyboard size

        jFrame.setFocusable(true);
        jFrame.add(BorderLayout.CENTER, jPanel);//Placing panels into windows
        jFrame.add(BorderLayout.SOUTH, mainPanel);
        jFrame.setBounds(400, 20, 880, 970);//Setting the page size and position


        //Adding click events to the virtual keyboard
        for (JButton button : jButtons) {
            button.addActionListener(e -> {
                JButton jButton17 = (JButton) e.getSource();
                String buttonText = jButton17.getText();
                switch (buttonText) {
                    case "Back":
                        handleBackButtonClick();
                        break;
                    case "Enter":
                        handleEnterButtonClick();
                        break;
                    case "Restart":
                        handleRestartButtonClick();
                        break;
                    case "End":
                        handleEndButtonClick();
                        break;
                    default:
                        model.processInput(buttonText);
                        model.notifyObserver();
                        update();
                        break;
                }
                setFocusable(true);
            });
        }
        //Add keyboard event listener to the frame
        jFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        handleEnterButtonClick();
                        break;
                    case KeyEvent.VK_BACK_SPACE:
                        handleBackButtonClick();
                        break;
                    case KeyEvent.VK_1:
                    case KeyEvent.VK_2:
                    case KeyEvent.VK_3:
                    case KeyEvent.VK_4:
                    case KeyEvent.VK_5:
                    case KeyEvent.VK_6:
                    case KeyEvent.VK_7:
                    case KeyEvent.VK_8:
                    case KeyEvent.VK_9:
                    case KeyEvent.VK_0:
                        model.processInput(String.valueOf(e.getKeyChar()));
                        break;
                    case KeyEvent.VK_PLUS:
                        model.processInput("+");
                        break;
                    case KeyEvent.VK_MINUS:
                        model.processInput("-");
                        break;
                    case KeyEvent.VK_MULTIPLY:
                        model.processInput("*");
                        break;
                    case KeyEvent.VK_DIVIDE:
                        model.processInput("/");
                        break;
                    case KeyEvent.VK_EQUALS:
                        model.processInput("=");
                        break;
                }
                model.notifyObserver();
            }
        });

    }

    /**
     * handle processing when "Back" button is clicked
     *
     * @Pre: none.
     * @Post: Processes the back button click event, updates the model, and refreshes the view.
     */
    private void handleBackButtonClick() {
        model.back();
        model.notifyObserver();
        update();
    }

    /**
     * handle processing when "Enter" button is clicked
     *
     * @Pre: none.
     * @Post: Processes the enter button click event, validates the equation,
     * updates the view accordingly, and displays appropriate dialog messages.
     */
    public void handleEnterButtonClick() {
        int[] verify = model.checkEquation();
        if (verify[0] == -1) {//no equal sign
            displayDialog(0, "");
        } else if (verify[0] == -2) {//no operator
            displayDialog(1, "");
        } else if (verify[0] == -3) {//left and right of the expression are not equal
            displayDialog(2, "");
        } else if (verify[0] == -4) {//too short
            displayDialog(6, "");
        } else if (verify[0] == -5) {//lose
            displayDialog(4, "");
        } else if (verify[0] == -6) {//win
            setValidationColor(-1, model.getLineNum() * 7, "ok");
            displayDialog(5, "");
        } else {//try again
            if (verify.length == 7) {
                // set color
                for (int i = 0; i < verify.length; i++) {
                    setValidationColor(verify[i], i + (model.getLineNum() * 7), model.getEnteredStrings()[i]);
                }
            }
            model.clear();
            model.setLineNum(model.getLineNum() + 1);
            update();
            displayDialog(3, "You can try " + (6 - model.getLineNum()) + " times");
        }
    }

    /**
     * handle processing when "Restart" button is clicked
     *
     * @Pre: none.
     * @Post: Handles the restart button click event, triggers the game restart through the control, and updates the view.
     */
    public void handleRestartButtonClick() {
        control.gameOver(0);
        model.notifyObserver();
        update();
    }

    /**
     * handle processing when "End" button is clicked
     *
     * @Pre: none.
     * @Post: Handles the end button click event, exits the game through the control, and updates the view.
     */
    public void handleEndButtonClick() {
        control.gameOver(1);
        model.notifyObserver();
    }

    /**
     * @Pre: s should be a valid string, index should be a valid index within the range of text fields.
     * @Post: Sets the provided string s to the text field at the specified index.
     */
    public void processInput(String s, int index) {
        jTextFields.get(index).setText(s);
    }


    /**
     * Setting the colour of characters and buttons after validation
     *
     * @Pre: i should be an integer representing the validation status,
     * index should be a valid index within the range of text fields, s should be a valid string.
     * @Post: Sets the background color of text fields and buttons based on the validation status and provided string.
     */
    public void setValidationColor(int i, int index, String s) {
        if (i == 2) {
            jTextFields.get(index).setBackground(Color.gray);
            for (JButton jButton : jButtons) {
                if (jButton.getText().equals(s)) {
                    jButton.setBackground(Color.gray);
                    break;
                }
            }
        } else if (i == 1) {
            jTextFields.get(index).setBackground(Color.orange);
            for (JButton jButton : jButtons) {
                if (jButton.getText().equals(s)) {
                    jButton.setBackground(Color.orange);
                    break;
                }
            }
        } else if (i == 0) {
            jTextFields.get(index).setBackground(Color.GREEN);
            for (JButton jButton : jButtons) {
                if (jButton.getText().equals(s)) {
                    jButton.setBackground(Color.GREEN);
                    break;
                }
            }
            // The equation is correct
        } else if (i == -1) {
            for (int j = index; j < index + 7; j++) {
                jTextFields.get(j).setBackground(Color.GREEN);
            }
            this.repaint();
        }
    }


    /**
     * Displays appropriate dialog messages based on the provided code and message.
     *
     * @Pre 'code' must be an integer representing the type of dialog message.
     * @Post Displays a dialog message according to the provided code and message.
     */
    public void displayDialog(int code, String message) {
        if (code == 0) {
            //Create a JOptionPane dialogue box for displaying error messages
            JOptionPane optionPane = new JOptionPane("Missing equals sign", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
            // optionPane.setOptions(new String[]{"OK"}); // Set the label of the OK button to "OK"
            // Create a JDialog dialogue that wraps the JOptionPane dialogue with the specified title.
            JDialog dialog = optionPane.createDialog("Equation Error");
            // Display the dialogue box so that it is visible
            dialog.setVisible(true);
        } else if (code == 1) {
            JOptionPane optionPane = new JOptionPane("Missing operators", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
            JDialog dialog = optionPane.createDialog("Equation Error");
            dialog.setVisible(true);
        } else if (code == 2) {
            JOptionPane optionPane = new JOptionPane("The values on the left and right sides of the equation are not equal", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
            JDialog dialog = optionPane.createDialog("Equation Error");
            dialog.setVisible(true);
        } else if (code == 3) {
            //Create a JOptionPane dialogue box for displaying general messages
            JOptionPane optionPane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
            JDialog dialog = optionPane.createDialog("Try again");
            dialog.setVisible(true);
        } else if (code == 4) {
            JOptionPane optionPane = new JOptionPane("You Lose", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
            JDialog dialog = optionPane.createDialog("Game Over");
            dialog.setVisible(true);
        } else if (code == 5) {
            JOptionPane optionPane = new JOptionPane("You Win", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
            JDialog dialog = optionPane.createDialog("Game Over");
            dialog.setVisible(true);
        } else if (code == 6) {
            JOptionPane optionPane = new JOptionPane("Too short", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
            JDialog dialog = optionPane.createDialog("Equation Error");
            dialog.setVisible(true);
        }
    }

    @Override
    public void update() {
        control.update();
    }
}
