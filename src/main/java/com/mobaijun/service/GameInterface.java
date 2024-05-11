package com.mobaijun.service;

import com.mobaijun.util.Observer;

/**
 * Description: [base interface]
 * Author: [mobaijun]
 * Date: [2024/5/7 14:19]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public interface GameInterface {

    /**
     * Processes the input string.
     *
     * @param input the input string to be processed
     */
    void processInput(String input);

    /**
     * Checks if the Enter key is pressed.
     *
     * @return true if the Enter key is pressed, otherwise false
     */
    Boolean checkEnter();

    /**
     * Performs a back operation.
     */
    void back();

    /**
     * Checks the equation and returns the result.
     *
     * @return an array containing the results of equation checking
     */
    int[] checkEquation();

    /**
     * Gets the current line number.
     *
     * @return the current line number
     */
    int getLineNum();

    /**
     * Sets the line number.
     *
     * @param lineNum the line number to be set
     */
    void setLineNum(int lineNum);

    /**
     * Gets the current guess.
     *
     * @return the current guess
     */
    String getCurrentGuess();

    /**
     * Clears the game state.
     */
    void clear();

    /**
     * Starts a new game.
     */
    void startNewGame();

    /**
     * Gets an array of entered strings.
     *
     * @return an array of entered strings
     */
    String[] getEnteredStrings();

    /**
     * Adds an observer to the game.
     *
     * @param observer the observer to be added
     */
    void addObserver(Observer observer);

    /**
     * Removes an observer from the game.
     *
     * @param observer the observer to be removed
     */
    void removeObserver(Observer observer);

    /**
     * Notifies the observer about changes in the game state.
     */
    void notifyObserver();
}
