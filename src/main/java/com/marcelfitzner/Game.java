package com.marcelfitzner;

import java.util.HashMap;
import java.util.Map;

import static com.marcelfitzner.GameState.*;
import static com.marcelfitzner.Screens.showMainScreen;
import static java.lang.System.out;

public class Game {
    private static final char KEY_EXIT_GAME = 'x';
    private static final char KEY_START_GAME = ' ';
    private static final char KEY_SHOW_RULES = 'r';
    private static final char KEY_SHOW_SCORES = 's';

    Map<Character, Runnable> menuActions = new HashMap<>();

    private GameState gameState = MAIN_MENU;

    public Game() {
        setupScreenActions();
    }

    public void play() {
        while (gameState != TERMINATED) {
            showMainScreen(menuActions);
        }

        out.println("Thank you for playing Rock-Scissor-Paper!");
    }

    void setupScreenActions() {
        menuActions.put(KEY_EXIT_GAME, () -> gameState = TERMINATED);
        menuActions.put(KEY_SHOW_RULES, () -> { gameState = SHOW_RULES; out.println("Showing the rules..."); } );
        menuActions.put(KEY_SHOW_SCORES, () -> { gameState = SHOW_SCORES; out.println("Showing scores..."); } );
        menuActions.put(KEY_START_GAME, () -> { gameState = PLAYING; startGameVersusBot(); } );
    }

    void startGameVersusBot() {
        Match humanVsBot = new Match();
        humanVsBot.playMatch();
    }
}
