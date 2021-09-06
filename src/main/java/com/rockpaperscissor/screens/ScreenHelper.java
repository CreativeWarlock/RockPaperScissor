package com.rockpaperscissor.screens;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.rockpaperscissor.game.GameItem;
import com.rockpaperscissor.game.GameManager;
import com.rockpaperscissor.game.MatchManager;

import java.util.*;

import static com.rockpaperscissor.game.GameItem.*;

public class ScreenHelper {

    public static class PlayerChoice implements Runnable {
        GameManager gameManager;
        GameItem choice;

        public PlayerChoice(GameManager gameManager, GameItem choice) {
            this.gameManager = gameManager;
            this.choice = choice;
        }

        public void run() {
            gameManager.continueMatch(choice);
        }
    }

    private static final LayoutData CENTERED = GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER);
    private static final LayoutData FILLED_3_COLS = GridLayout.createHorizontallyFilledLayoutData(3);
    private static final LayoutData PSEUDO_CENTERED = GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER, true, false, 3, 1);

    public static final String RULES_OF_GAME = " * This is a simple implementation of the game\n" +
            "               'Rock, Paper, Scissor'\n" +
            " \n" +
            " - In this game you play against the computer\n" +
            " - A match takes several rounds\n" +
            " - In each round both you and the bot choose either rock, paper or scissor.\n" +
            " \n" +
            " The following rules define which item beats the other one,\n" +
            " and thus grants a score point for the winner of a round:\n" +
            " \n" +
            " - paper beats rock\n" +
            " - rock beats scissor\n" +
            " - scissor beats paper\n" +
            " \n" +
            " If both players select the same item, this round is a tie and\n" +
            " will be repeated until one of the player lands a score.\n" +
            " \n" +
            " The player who reaches a score of 3 points wins the game!\n Good luck, human!";

    public void showMenuScreen(Window window, WindowBasedTextGUI textGUI, GameManager gameManager) {
        Panel contentPanel = new Panel(new GridLayout(1));

        GridLayout gridLayout = (GridLayout)contentPanel.getLayoutManager();
        gridLayout.setHorizontalSpacing(5);
        gridLayout.setLeftMarginSize(10);
        gridLayout.setRightMarginSize(10);
        gridLayout.setBottomMarginSize(2);
        gridLayout.setTopMarginSize(2);

        Label title = new Label("WELCOME TO ROCK-PAPER-SCISSOR!");
        title.setLayoutData(PSEUDO_CENTERED);
        contentPanel.addComponent(title);

        contentPanel.addComponent(createEmptyRow(1));

        contentPanel.addComponent(new Button("New Match", gameManager::startNewMatchAgainstBot).setLayoutData(CENTERED));

        contentPanel.addComponent(createEmptyRow(1));

        Button rulesBtn = new Button("Rules",
                () -> MessageDialog.showMessageDialog(textGUI, "Rules", RULES_OF_GAME,
                        MessageDialogButton.OK));
        rulesBtn.setLayoutData(CENTERED);
        contentPanel.addComponent(rulesBtn);

        contentPanel.addComponent(createEmptyRow(1));

        contentPanel.addComponent(new Button("Exit", gameManager::exitGame).setLayoutData(CENTERED));

        window.setComponent(contentPanel);
        textGUI.addWindowAndWait(window);
    }

    /** We keep it distinctly simple and not employ an MVC pattern.
     * Instead the view (V) is mixed up with control (C) code. However, the model remains in the gameManager (M <-> VC)
     *
     * @param window - the window of the console in which the gui is enwrapped
     * @param textGUI - the graphical user interface
     * @param gameManager - the game manager with its internal model & data of the game
     */
    public void showMatchScreen(Window window, WindowBasedTextGUI textGUI, GameManager gameManager) {
        Panel contentPanel = new Panel(new GridLayout(3));

        GridLayout gridLayout = (GridLayout)contentPanel.getLayoutManager();
        gridLayout.setHorizontalSpacing(5);
        gridLayout.setLeftMarginSize(5);
        gridLayout.setRightMarginSize(5);
        gridLayout.setBottomMarginSize(1);
        gridLayout.setTopMarginSize(1);

        String titleMessage = "-== NEW MATCH: PLAYER VERSUS BOT ==-";
        if (gameManager.getMatchManager().getStateOfMatch() == MatchManager.MatchState.MATCH_ONGOING) {
            titleMessage = "          -== ONGOING MATCH ==-";
        }

        Label title = new Label(titleMessage);
        title.setLayoutData(FILLED_3_COLS);
        contentPanel.addComponent(title);

        contentPanel.addComponent(createSeparator(3));
        contentPanel.addComponent(createEmptyRow(3));

        Label round = new Label("ROUND: #" + gameManager.getMatchManager().getRound());
        round.setLayoutData(FILLED_3_COLS);
        contentPanel.addComponent(round);

        contentPanel.addComponent(createEmptyRow(3));

        Label scores = new Label("SCORES: PLAYER <" + gameManager.getMatchManager().getScoresOfPlayer() + "> - BOT <" + gameManager.getMatchManager().getScoresOfBot() + ">");
        scores.setLayoutData(FILLED_3_COLS);
        contentPanel.addComponent(scores);

        contentPanel.addComponent(createEmptyRow(3));
        contentPanel.addComponent(createSeparator(3));
        contentPanel.addComponent(createEmptyRow(3));

        String messageOfLastRound = gameManager.getMatchManager().getMessageOfLastRound();
        if (!messageOfLastRound.isEmpty()) {

            Label lastMessage = new Label(messageOfLastRound);
            lastMessage.setLayoutData(FILLED_3_COLS);
            contentPanel.addComponent(lastMessage);

            contentPanel.addComponent(createEmptyRow(3));
            contentPanel.addComponent(createSeparator(3));
            contentPanel.addComponent(createEmptyRow(3));
        }

        if (EnumSet.of(MatchManager.MatchState.NOT_STARTED, MatchManager.MatchState.MATCH_ONGOING)
                .contains(gameManager.getMatchManager().getStateOfMatch())) {
            Label choice = new Label("Your choice:");
            choice.setLayoutData(CENTERED);
            contentPanel.addComponent(choice);

            contentPanel.addComponent(createEmptyRow(3));

            Button choseRockBtn = new Button("Rock", new PlayerChoice(gameManager, ROCK));
            contentPanel.addComponent(choseRockBtn);

            Button chosePaperBtn = new Button("Paper", new PlayerChoice(gameManager, PAPER));
            contentPanel.addComponent(chosePaperBtn);

            Button choseScissorBtn = new Button("Scissor", new PlayerChoice(gameManager, SCISSOR));
            contentPanel.addComponent(choseScissorBtn);

            contentPanel.addComponent(createEmptyRow(3));
            contentPanel.addComponent(createSeparator(3));
            contentPanel.addComponent(createEmptyRow(3));
        }

        Button leaveMatchBtn = new Button("Leave Match", gameManager::createNewGame).setLayoutData(CENTERED);
        contentPanel.addComponent(leaveMatchBtn);
        contentPanel.addComponent(createEmptyRow(3));

        window.setComponent(contentPanel);
        textGUI.addWindowAndWait(window);
    }

    private Component createSeparator(int columnSpan) {
        return new Separator(Direction.HORIZONTAL).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(columnSpan));
    }

    private Component createEmptyRow(int columnSpan) {
        return new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(columnSpan));
    }

    // Some GUI elements from the Lantern demo. Useful for reference
    public static void showDemoScreen(Window window, WindowBasedTextGUI textGUI) {
        Panel contentPanel = new Panel(new GridLayout(2));

        GridLayout gridLayout = (GridLayout)contentPanel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);

        Label title = new Label("This is a label that spans two columns");
        title.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.BEGINNING,
                GridLayout.Alignment.BEGINNING,
                true,
                false,
                2,
                1));
        contentPanel.addComponent(title);

        contentPanel.addComponent(new Label("Text Box (aligned)"));
        contentPanel.addComponent(
                new TextBox()
                        .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER)));

        contentPanel.addComponent(new Label("Read-only Combo Box (forced size)"));
        List<String> timezonesAsStrings = new ArrayList<>(Arrays.asList(TimeZone.getAvailableIDs()));
        ComboBox<String> readOnlyComboBox = new ComboBox<>(timezonesAsStrings);
        readOnlyComboBox.setReadOnly(true);
        readOnlyComboBox.setPreferredSize(new TerminalSize(20, 1));
        contentPanel.addComponent(readOnlyComboBox);

        contentPanel.addComponent(new Label("Editable Combo Box (filled)").setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER)));
        contentPanel.addComponent(
                new ComboBox<>("Item #1", "Item #2", "Item #3", "Item #4")
                        .setReadOnly(false)
                        .setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1)));

        contentPanel.addComponent(new Label("Button (centered)"));
        contentPanel.addComponent(new Button("Button", ()
                -> MessageDialog.showMessageDialog(textGUI, "MessageBox", "This is a message box", MessageDialogButton.OK)).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER)));

        contentPanel.addComponent(
                new EmptySpace()
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(2)));
        contentPanel.addComponent(
                new Separator(Direction.HORIZONTAL)
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(2)));
        contentPanel.addComponent(
                new Button("Close", window::close).setLayoutData(
                        GridLayout.createHorizontallyEndAlignedLayoutData(2)));

        window.setComponent(contentPanel);
        textGUI.addWindowAndWait(window);
    }
}
