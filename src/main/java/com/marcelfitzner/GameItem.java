package com.marcelfitzner;

public enum GameItem {
    UNDEFINED, ROCK, PAPER, SCISSOR;

    static {
        UNDEFINED.beats = UNDEFINED;
        PAPER.beats = ROCK;
        ROCK.beats = SCISSOR;
        SCISSOR.beats = PAPER;
    }
    private GameItem beats;

    GameItem beats() {
        return beats;
    }
}