package com.rockpaperscissor.screens;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.rockpaperscissor.game.GameManager;

import java.io.IOException;

import static java.lang.System.out;

public class ScreenManager {
    final DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
    final Window window = new BasicWindow("Rock, Paper, Scissor");

    GameManager gameManager;
    ScreenHelper screenHelper;
    WindowBasedTextGUI textGUI = null;
    Screen screen = null;

    public ScreenManager(GameManager gameManager) {
        this.gameManager = gameManager;
        this.screenHelper = new ScreenHelper();

        try {
            screen = terminalFactory.createScreen();
            screen.startScreen();
            // We create the default MultiWindowTextGUI constructor so only a single thread is used
            // as this game does not require complex game logic / multi-threading:
            textGUI = new MultiWindowTextGUI(screen);
        } catch (IOException e) {
            e.printStackTrace();
            out.println("Could not create Lantern screen!");
        }
    }

    public void destroyScreen() {
        if (screen != null) {
            try {
                screen.stopScreen();
                window.close();
            }
            catch(IOException e) {
                e.printStackTrace();
                out.println("Could not stop Lantern screen!");
            }
        }
    }

    public void showMainMenu() {
        screenHelper.showMenuScreen(window, textGUI, gameManager);
    }

    public void showMatchScreen() {
        screenHelper.showMatchScreen(window, textGUI, gameManager);
    }
}
