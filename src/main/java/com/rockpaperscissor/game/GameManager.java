package com.rockpaperscissor.game;

import com.rockpaperscissor.screens.ScreenManager;

public class GameManager {

    private final ScreenManager screenManager;
    private final MatchManager matchManager;

    public GameManager() {
        this.screenManager = new ScreenManager(this);
        this.matchManager = new MatchManager();
    }

    public MatchManager getMatchManager() {
        if (matchManager.getStateOfMatch() != MatchManager.MatchState.MATCH_ONGOING) {
            System.out.println("There's currently no match going on!");
        }

        return matchManager;
    }

    public void exitGame() {
        screenManager.destroyScreen();
    }

    public void createNewGame() {
        matchManager.resetMatch();
        screenManager.showMainMenu();
    }

    public void startNewMatchAgainstBot() {
        matchManager.resetMatch();
        screenManager.showMatchScreen();
    }

    public void continueMatch(GameItem playerChoice) {
        matchManager.playAnotherRound(playerChoice);
        screenManager.showMatchScreen();
    }
}
