package com.rockpaperscissor;

import com.rockpaperscissor.game.GameManager;

/**
 * This is a simple implementation of the game 'Rock, Paper, Scissor'
 *
 * - A human player plays against the computer
 * - A match takes several rounds in which each both the player & the bot
 * will choose either rock, paper or scissor.
 *
 * The following rules define which item beats the other one, and thus granting a score point for the player:
 *
 * - paper beats rock
 * - rock beats scissor
 * - scissor beats paper
 *
 * If both players select the same item, this round is a tie and will be repeated until one of the player lands a score.
 *
 * The games with one of the player reaches a score of 3 points.
 */

public class Main {
    public static void main(String[] args) {
        GameManager rockScissorPaperGame = new GameManager();
        rockScissorPaperGame.createNewGame();
    }
}
