package com.marcelfitzner;

import java.util.concurrent.ThreadLocalRandom;

import static com.marcelfitzner.GameItem.*;

public class Bot {

    String label;

    public String getLabel() {
        return label;
    }

    public Bot(String label) {
        this.label = label;
    }

    public GameItem getChoice() {
        // just random. No specific bot intelligence.
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
