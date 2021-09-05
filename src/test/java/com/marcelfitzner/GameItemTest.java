package com.marcelfitzner;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameItemTest {

    @Test
    @Description("Ensures Paper beats rock.")
    public void ensurePaperBeatsRock() {
        assertTrue(GameItem.PAPER.beats() == GameItem.ROCK);
    }

    @Test
    @Description("Ensures Rock beats scissor.")
    public void ensureRockBeatsScissor() {
        assertTrue(GameItem.ROCK.beats() == GameItem.SCISSOR);
    }

    @Test
    @Description("Ensures Scissor beats paper.")
    public void ensureScissorBeatsPaper() {
        assertTrue(GameItem.SCISSOR.beats() == GameItem.PAPER);
    }
}