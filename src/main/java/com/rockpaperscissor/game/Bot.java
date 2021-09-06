package com.rockpaperscissor.game;

import java.util.concurrent.ThreadLocalRandom;

import static com.rockpaperscissor.game.GameItem.*;

public class Bot {

    String label;

    public String getLabel() {
        return label;
    }

    public Bot(String label) {
        this.label = label;
    }

    public GameItem getChoice() {
        // just random choice. No specific bot intelligence.
        int botChoice = ThreadLocalRandom
                .current().nextInt(1, 4);

        return switch (botChoice) {
            case 1 -> ROCK;
            case 2 -> PAPER;
            case 3 -> SCISSOR;
            default -> UNDEFINED;
        };
    }
}
