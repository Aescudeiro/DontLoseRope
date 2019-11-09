package org.academiadecodigo.thunderstructs.charlie.Utilities;

public class Messages {

    private static final String MENULINE = "\n_________________________";
    public static final String GAME_FULL = "The game room you're trying to enter is full! Pick another one";
    public static final String WINNER_ANNOUNCEMENT = "\nCongratulations! The winning team is: ";
    public static final String QUIT = Color.ANSI_BLUE + "Thanks " + Color.ANSI_RED + "for " + Color.ANSI_GREEN +
            "playing! " + Color.ANSI_RESET + "[Press enter to go back to terminal]";
    public static final String MAIN_MENU = Color.ANSI_GREEN + "Main menu" + MENULINE + Color.ANSI_RESET;
    public static final String WELCOME = Color.ANSI_GREEN + "\nWELCOME PLAYER! " +
            "Maximise your window for a better game experience \nPlease insert your name: " + Color.ANSI_RESET;
    public static final String JOINGAME = Color.ANSI_GREEN + "Choose a game type" + MENULINE + Color.ANSI_RESET;

    public static final String[] gameInstructions = {
            "GAME INSTRUCTIONS",
            "_________________",
            "",
            "Don't lose rope, meaning you can't lose rope. You lose rope whenever a player from the opposing team scores at a challenge.",
            "There are 2 types of challenges, the words challenge and the equation challenge.",
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
            "Don't Lose Rope has a few pre-created game rooms to which you can join or you could create your own games.",
            "Game starts when all players have joined the game.",
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