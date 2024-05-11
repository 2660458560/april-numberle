package com.mobaijun.controller;

import com.mobaijun.service.impl.GameInterfaceImpl;
import com.mobaijun.util.GameView;
import com.mobaijun.util.TextUtil;
import javax.swing.*;
import lombok.Getter;

/**
 * Description: [conroller]
 * Author: [mobaijun]
 * Date: [2024/5/7 15:23]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class GameController {

    private GameInterfaceImpl model;

    /**
     * -- GETTER --
     * Retrieves the current view instance.
     */
    @Getter
    private GameView view;

    public GameController(GameInterfaceImpl model) {
        this.model = model;
    }

    /**
     * Initializes the game by creating a new view and making it visible.
     * Associates the view with the controller and the controller with the view.
     */
    public void startGame() {
        GameView page = new GameView(this, model);
        page.setVisible(true);
        page.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.view = page;
        page.setControl(this);
    }

    /**
     * Synchronizes data between the model and the view by processing input for each cell.
     */
    public void update() {
        for (int i = 0; i < model.getEnteredStrings().length; i++) {
            if (model.getEnteredStrings()[i] != null) {
                getView().processInput(model.getEnteredStrings()[i], i + (model.getLineNum() * 7));
            } else {
                getView().processInput("", i + (model.getLineNum() * 7));
            }
        }
    }

    /**
     * Handles game over events.
     *
     * @param i 0 for restarting the game, 1 for exiting the application.
     */
    public void gameOver(int i) {
        if (i == 0) {
            model.startNewGame();
            getView().removeKeyListener(getView().getKeyListeners()[0]);
            this.model = new GameInterfaceImpl(TextUtil.readTextFromFile());
            getView().setControl(this);
            getView().dispose();
            GameView page = new GameView(this, model);
            setView(page);
            page.initializeFrame(page);
            page.removeKeyListener(page.getKeyListeners()[0]);
            page.setVisible(true);
        } else {
            System.exit(0);
        }
    }

    /**
     * Sets the provided view as the current view and adds it as an observer to the model.
     *
     * @param view The GameView instance to set.
     */
    public void setView(GameView view) {
        this.view = view;
        model.addObserver(view);
    }
}
