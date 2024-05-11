package com.mobaijun;

import com.mobaijun.controller.GameController;
import com.mobaijun.service.impl.GameInterfaceImpl;
import com.mobaijun.util.TextUtil;
import javax.swing.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: [gui application]
 * Author: [mobaijun]
 * Date: [2024/5/7 14:15]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
public class GuiApplication {

    public static void main(String[] args) {
        setLookAndFeel();
        GameInterfaceImpl model = new GameInterfaceImpl(TextUtil.readTextFromFile());
        GameController controller = new GameController(model);
        controller.startGame();
    }

    /**
     * Sets the look and feel for the application.
     */
    private static void setLookAndFeel() {
        try {
            javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            log.error("Failed to set look and feel", e);
        }
    }
}
