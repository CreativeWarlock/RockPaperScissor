package com.rockpaperscissor.game;

import java.util.EnumSet;

import static java.lang.System.out;

public class MatchManager {

    public enum MatchState { NOT_STARTED, MATCH_ONGOING, PLAYER_WON, BOT_WON }

    private MatchState stateOfMatch = MatchState.NOT_STARTED;

    public MatchState getStateOfMatch() {
        return stateOfMatch;
    }

    private final Bot randomBot = new Bot("R4nD0m");
    private int round = 0;
    private int scoresOfBot = 0;
    private int scoresOfPlayer = 0;

    private String lastMatchMessage = "";

    public String getMessageOfLastRound() {
        return lastMatchMessage;
    }

    public int getRound() {
        return round;
    }

    public int getScoresOfPlayer() {
        return scoresOfPlayer;
    }

    public int getScoresOfBot() {
        return scoresOfBot;
    }

    public void resetMatch() {
        round = 0;
        scoresOfBot = 0;
        scoresOfPlayer = 0;
        stateOfMatch = MatchState.NOT_STARTED;
    }

    public void playAnotherRound(GameItem playerChoice) {
        out.println("Player picked " + playerChoice);
        if (EnumSet.of(MatchState.NOT_STARTED, MatchState.MATCH_ONGOING).contains(stateOfMatch)) {
            stateOfMatch = MatchState.MATCH_ONGOING;

            GameItem botChoice = randomBot.getChoice();
            out.println("Bot picked " + botChoice);
            resolveOutcomeOfPlayerChoices(playerChoice, botChoice);
        }
    }

    void resolveOutcomeOfPlayerChoices(GameItem playerChoice, GameItem botChoice) throws IllegalStateException {
        if (playerChoice == botChoice) {
            lastMatchMessage = "It's a tie ('" + botChoice + "' vs. '" + botChoice + "')!\nRound will be repeated!";
        }
        else if (botChoice.beats() == playerChoice) {
            lastMatchMessage = "The BOT has won this round!\n" +
                        "His '" + botChoice + "' beats your '"+ playerChoice + "'";
            scoresOfBot++;
        } else if (playerChoice.beats() == botChoice) {
            lastMatchMessage = "You have won this round!   \n" +
                        "Your '" + playerChoice + "' beats the bot's '"+ botChoice + "'";
            scoresOfPlayer++;
        } else {
            throw new IllegalStateException("Illegal game state reached! " +
                    "Player's choice: " + playerChoice + " - Bot's choice: " + botChoice +
                    "Blame the software developer to not have thought of this case!");
        }

        out.println(lastMatchMessage);

        if (!hasAnyPlayerWon()) {
            round++;
        }
    }

    private boolean hasAnyPlayerWon() {
        if (scoresOfBot >= 3) {
            lastMatchMessage = "### The Bot has won the match! ###";
            stateOfMatch = MatchState.BOT_WON;
            return true;
        }
        else if (scoresOfPlayer >= 3) {
            lastMatchMessage = "### You have won the match! ###";
            stateOfMatch = MatchState.PLAYER_WON;
            return true;
        }

        return false;
    }
}
