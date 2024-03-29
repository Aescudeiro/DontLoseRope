package org.academiadecodigo.thunderstructs.charlie.Utilities;

public class Messages {

    private static final String MENU_LINE = "\n_________________________";
    public static final String GAME_FULL = "The game room you're trying to enter is full! Pick another one";
    public static final String WINNER_ANNOUNCEMENT = "\nCongratulations! The winning team is: ";
    public static final String QUIT = Color.ANSI_BLUE + "Thanks " + Color.ANSI_RED + "for " + Color.ANSI_GREEN +
            "playing! " + Color.ANSI_RESET + "[Press enter to go back to terminal]";
    public static final String MAIN_MENU = Color.ANSI_GREEN + "Main menu" + MENU_LINE + Color.ANSI_RESET;
    public static final String WELCOME = Color.ANSI_GREEN + "\nWELCOME PLAYER! " +
            "Maximise your window for a better game experience \nPlease insert your name: " + Color.ANSI_RESET + "\n";
    public static final String JOIN_GAME = Color.ANSI_GREEN + "Choose a game type" + MENU_LINE + Color.ANSI_RESET;
    public static final String CREATE_MENU = Color.ANSI_GREEN + "Create Game Menu" + MENU_LINE + Color.ANSI_RESET;
    public static final String SELECT_TEAM = Color.ANSI_GREEN + "Select Team" + MENU_LINE + Color.ANSI_RESET;
    public static final String SET_TEAM_COLOR = Color.ANSI_GREEN + "Choose Team Color" + MENU_LINE + Color.ANSI_RESET;
    public static final String SET_GAME_TYPE = Color.ANSI_GREEN + "Choose Game Type" + MENU_LINE + Color.ANSI_RESET;
    public static final String SET_DIFFICULTY = Color.ANSI_GREEN + "Choose Game Difficulty" + MENU_LINE + Color.ANSI_RESET;
    public static final String GAME_NAME = Color.ANSI_GREEN + "Please insert the game name: " + Color.ANSI_RESET + "\n";
    public static final String SET_MAX_PLAYERS = Color.ANSI_GREEN + "Set max player number: " + Color.ANSI_RESET + "\n";

    public static final String[] gameInstructions = {
            "GAME INSTRUCTIONS",
            "_________________",
            "",
            "Don't lose rope, meaning you can't lose rope.",
            "You lose rope whenever a player from the opposing team answers a challenge correctly.",
            "There are 2 types of challenges:",
            "",
            "   Words challenge: You'll have to type the provided word. There are 4 difficulty levels:",
            "",
            "       1. Words with 3 letters;",
            "       2. Words with 6 letters;",
            "       3. Words with 10 letters;",
            "       4. Words in latin;",
            "",
            "   Algebra challenge: you'll have to type the result of the equation provided. 4 difficulty levels:",
            "",
            "       1. Only additions;",
            "       2. Additions and subtractions;",
            "       3. Addictions, subtractions and multiplication.",
            "       4. Addictions, subtractions, multiplications and division",
            "",
            "Don't Lose Rope has a few pre-created game rooms that you can join, or you can create your own game room!",
            "Rope game starts when the room is full. Be ready for the countdown!",
            "__________________",
            "",
            "This game was created by:",
            "Afonso Escudeiros, João Furnas, Karolis Vaitkevicius and Rafael Azevedo."
    };

    public static final String[] createGameInstructions = {
            "HOW TO CREATE A GAME",
            "_____________________",
            "",
            "Provide a name for your game;",
            "Choose the game type: Algebra or Words;",
            "Choose difficulty level from 1 to 4;",
            "_____________________",
            "",
            "    Words challenge: You'll have to type the provided word. There are 4 difficulty levels:",
            "",
            "       1. Words with 3 letters;",
            "       2. Words with 6 letters;",
            "       3. Words with 10 letters;",
            "       4. Words in latin;",
            "",
            "    Algebra challenge: you'll have to type the result of the equation provided. 4 difficulty levels:",
            "",
            "       1. Only additions;",
            "       2. Additions and subtractions;",
            "       3. Addictions, subtractions and multiplication.",
            "       4. Addictions, subtractions, multiplications and division",
            "_____________________",
            "",
            "Choose a team from the available team's list.",
            "Go back to Main Menu and join the game you just created",
            "",
            "HAVE FUN!"
    };
}