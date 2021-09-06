package com.rockpaperscissor;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import static com.rockpaperscissor.game.GameItem.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class GameItemTest {

    @Test
    @Description("Ensures Paper beats rock.")
    void ensurePaperBeatsRockOnly() {
        assertEquals(PAPER.beats(), ROCK);

        assertNotEquals(PAPER.beats(), PAPER);
        assertNotEquals(PAPER.beats(), SCISSOR);
        assertNotEquals(PAPER.beats(), UNDEFINED);
    }

    @Test
    @Description("Ensures Rock beats scissor.")
    void ensureRockBeatsScissorOnly() {
        assertEquals(ROCK.beats(), SCISSOR);

        assertNotEquals(ROCK.beats(), ROCK);
        assertNotEquals(ROCK.beats(), PAPER);
        assertNotEquals(ROCK.beats(), UNDEFINED);
    }

    @Test
    @Description("Ensures Scissor beats paper.")
    void ensureScissorBeatsPaper() {
        assertEquals(SCISSOR.beats(), PAPER);

        assertNotEquals(SCISSOR.beats(), SCISSOR);
        assertNotEquals(SCISSOR.beats(), ROCK);
        assertNotEquals(SCISSOR.beats(), UNDEFINED);
    }

    @Test
    @Description("Ensures Undefined only beats undefined.")
    void ensureUndefinedBeatsUndefinedOnly() {
        assertEquals(UNDEFINED.beats(), UNDEFINED);

        assertNotEquals(UNDEFINED.beats(), ROCK);
        assertNotEquals(UNDEFINED.beats(), PAPER);
        assertNotEquals(UNDEFINED.beats(), SCISSOR);
    }
}