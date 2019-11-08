package org.academiadecodigo.thunderstructs.charlie.Generators;

public class ChallengeGenerator {

    /**
     * Generates random equations as strings and it's result
     *
     * @param difficulty int from 1 to 4 indicating the difficulty level
     * @return a String[] with a pair equation - result
     */
    public static String[] generateEquation(int difficulty) {

        int rand = (int) (Math.random() * difficulty);
        String[] challenge = new String[2];

        switch (rand) {
            case 0:
                return addition();

            case 1:
                return subtraction();

            case 2:
                return multiplication();

            case 3:
                return division();

            default:
                return challenge;

        }
    }


    /**
     * provides a random word
     *
     * @param difficulty int from 1 to 3 indicating the difficulty level
     * @return a String representing a word of variable length according to difficulty
     */
    public static String generateWord(int difficulty) {
        String[] words = {"apple", "orange", "table", "coffee", "mouse", "cookies", "computer", "chair", "keyboard", "school", "code", "academy"};
        return words[(int) (Math.random() * words.length)];
    }

    private static String[] multiplication() {

        int firstNum = (int) (Math.random() * 10) + 1;
        int secondNum = (int) (Math.random() * 10) + 1;
        String result = "" + (firstNum * secondNum);
        String equation = "" + firstNum + " x " + secondNum;

        return new String[]{equation, result};
    }

    private static String[] division() {
        int firstNum = (int) (Math.random() * 10) + 1;
        int secondNum = (int) (Math.random() * 10) + 1;
        String result = "" + (firstNum / secondNum);
        String equation = "" + firstNum + " / " + secondNum;

        return new String[]{equation, result};
    }

    private static String[] addition() {
        int firstNum = (int) (Math.random() * 10) + 1;
        int secondNum = (int) (Math.random() * 10) + 1;
        String result = "" + (firstNum + secondNum);
        String equation = "" + firstNum + " + " + secondNum;

        return new String[]{equation, result};
    }

    private static String[] subtraction() {
        int firstNum = (int) (Math.random() * 10) + 1;
        int secondNum = (int) (Math.random() * 10) + 1;
        String result = "" + (firstNum - secondNum);
        String equation = "" + firstNum + " - " + secondNum;

        return new String[]{equation, result};
    }

}