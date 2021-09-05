package com.marcelfitzner;

import java.util.HashMap;
import java.util.Map;

import static com.marcelfitzner.GameItem.*;
import static com.marcelfitzner.Screens.showPlayingScreen;
import static com.marcelfitzner.Screens.showWinningScreen;
import static java.lang.System.out;

public class Match {

    private static final char KEY_PLAYER_CHOICE_ROCK = '1';
    private static final char KEY_PLAYER_CHOICE_PAPER = '2';
    private static final char KEY_PLAYER_CHOICE_SCISSOR = '3';

    Map<Character, Runnable> matchActions = new HashMap<>();

    public enum MatchState { MATCH_ONGOING, PLAYER_WON, BOT_WON }

    private final Bot randomBot = new Bot("R4nD0m");
    private int round = 0;
    private int scoresBot = 0;
    private int scoresPlayer = 0;
    private MatchState stateOfMatch = MatchState.MATCH_ONGOING;

    public MatchState getStateOfMatch() {
        return stateOfMatch;
    }

    public int getRound() {
        return round;
    }

    public int getScoresPlayer() {
        return scoresPlayer;
    }

    public int getScoresBot() {
        return scoresBot;
    }

    public Match() {
        out.println("Starting a new match! (Human vs. Bot)");
        setupMatchActions();
    }

    void setupMatchActions() {
        matchActions.put(KEY_PLAYER_CHOICE_ROCK, () -> resolveOutcomeOfPlayerChoices(ROCK, randomBot.getChoice()));
        matchActions.put(KEY_PLAYER_CHOICE_PAPER, () -> resolveOutcomeOfPlayerChoices(PAPER, randomBot.getChoice()));
        matchActions.put(KEY_PLAYER_CHOICE_SCISSOR, () -> resolveOutcomeOfPlayerChoices(SCISSOR, randomBot.getChoice()));
    }

    void playMatch() {
        while (stateOfMatch == Match.MatchState.MATCH_ONGOING) {
            showPlayingScreen(matchActions, round, scoresPlayer, scoresBot);
        }

    }

    void resolveOutcomeOfPlayerChoices(GameItem playerChoice, GameItem botChoice) throws IllegalStateException {
        if (playerChoice == botChoice) {
            out.println("/// It's a tie ('" + botChoice + "' vs. '" + botChoice +  "')! Round will be repeated! ///");
        }
        else if (botChoice.beats() == playerChoice) {
            out.println("/// BOT '" + randomBot.getLabel() + "' has won this round! ///\n" +
                        "/// His '" + botChoice + "' beats your '"+ playerChoice + "' ///");
            scoresBot++;
        } else if (playerChoice.beats() == botChoice) {
            out.println("///   You have won this round!   ///\n" +
                        "/// Your '" + playerChoice + "' beats the bot's '"+ botChoice + "' ///");
            scoresPlayer++;
        } else {
            throw new IllegalStateException("Illegal game state reached! " +
                    "Player's choice: " + playerChoice + " - Bot's choice: " + botChoice +
                    "Blame the software developer to not have thought of this case!");
        }

        if (!hasAnyPlayerWon()) {
            round++;
        }
    }

    private boolean hasAnyPlayerWon() {
        if (scoresBot >= 3) {
            showWinningScreen(round, scoresBot, scoresPlayer, "BOT '" + randomBot.getLabel() + "'", "has");
            return true;
        }
        else if (scoresPlayer >= 3) {
            showWinningScreen(round, scoresBot, scoresPlayer, "You", "have");
            return true;
        }

        return false;
    }
}
