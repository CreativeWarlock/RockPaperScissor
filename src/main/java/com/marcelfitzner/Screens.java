package com.marcelfitzner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;

import static java.lang.System.out;

class Screens {

    public static void showMainScreen(Map<Character, Runnable> keyboardCommands) {
        out.println("            WELCOME TO ROCK-PAPER-SCISSOR!\n\n" +
                    "            - Press <Space> to start a game against the computer.\n\n" +
                    "            - Press <x> to quit the game\n" +
                    "--------------------------------------------------------------------------------------------\n" +
                    "(If you run this program in an IDE console you need to complete your selection with <RETURN>)\n"+
                    "Your selection: ");

        processUserInput(keyboardCommands);
    }

    public static void showPlayingScreen(Map<Character, Runnable> keyboardCommands, int round, int scoresOfPlayer, int scoresOfBot) {
        out.println("\n            ### ROUND " + round + " ###\n\n" +
                " SCORES:          PLAYER: " + scoresOfPlayer + " - BOT: " + scoresOfBot + " \n\n" +
                " CHOOSE ONE OF THE FOLLOWING:\n" +
                "               1 - ROCK\n" +
                "               2 - PAPER\n" +
                "               3 - SCISSOR\n\n" +
                "After you selected 1, 2 or 3 the computer will chose one item, as well.\n" +
                "The computer will not know your selection and comes up with his own random choice.\n" +
                "--------------------------------------------------------------------------------------------\n" +
                "(If you run this program in an IDE console you need to complete your selection with <RETURN>)\n" +
                "Your selection: ");
        out.println();

        processUserInput(keyboardCommands);
    }

    //Map<Character, Runnable> keyboardCommands,
    public static void showWinningScreen(int round, int scoresOfPlayer, int scoresOfBot, String playerLabel, String verb) {
        out.println("            ### " + playerLabel + " " + verb + " WON!\n\n" +
                " Number of Rounds: " + round + " ###\n\n" +
                " SCORES:\n" +
                " PLAYER: " + scoresOfPlayer + " - BOT: " + scoresOfBot + " \n\n" +
                // TODO: add choice to go back to main menu.
                "--------------------------------------------------------------------------------------------\n" +
                "(If you run this program in an IDE console you need to complete your selection with <RETURN>)\n" +
                "Your selection: ");
        out.println();

        //processUserInput(keyboardCommands);
    }

    private static void processUserInput(Map<Character, Runnable> keyboardCommands) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[] charInput = new char[1];
        int numberOfCharsRead = 0;

        do {
            try {
                numberOfCharsRead = br.read(charInput);

                if (!keyboardCommands.containsKey(charInput[0])) {
                    if (numberOfCharsRead > 0) {
                        out.println("\nYou pressed " + Arrays.toString(charInput));
                    }
                    out.print("Please select one of the following commands: " + keyboardCommands.keySet() + "\n");
                }
            } catch (IOException e) {
                out.print("Please select one of the following commands: " + keyboardCommands.keySet() + "\n");
            }
        } while (keyboardCommands.get(charInput[0]) == null);

        keyboardCommands.get(charInput[0]).run();
    }
}
