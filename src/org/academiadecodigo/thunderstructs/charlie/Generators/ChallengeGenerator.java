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
        
        String[] words1 = {"fax", "job", "joe", "joy", "lazy", "hot", "jazz", "joke", "jump", "code", "java", "quad", "jail", "jade", "zap", "vox", "fox", "max", "zen", "log", "wax", "jar", "hot", "cup", "gym", "hot", "hot"};
        String[] words2 = {"apple", "orange", "buzzed", "coffee", "mouse", "cookies", "computer", "frizzy", "keyboard", "school", "pizzas", "academy"};
        String[] words3 = {"blackjacks", "complexify", "carjacking", "exorcizing", "kickboxing", "maximizers", "backpacker", "lumberjack", "keyboard", "complexing", "chickenpox", "jockstraps", "humanizing", "subjective", "quicksteps", "polygamize", "pickpocket", "microquake", "equalizers", "enzymology"};
        String[] words4 = {"numerale", "sepulcrum", "organizzare", "migliore", "scegliere", "settimana", "piuttosto", "particolarmente", "rispondere", "susceptum", "suscipere", "ripetizione", "quoziente", "dovrebbe", "verificarsi", "moltiplicare", "spostare", "mezzogiorno", "uterque", "utrumque", "spettacolo", "mescolare"};

        switch(difficulty){
            case 1:
                return words1[(int) (Math.random() * words1.length)];

            case 2:
                return words2[(int) (Math.random() * words2.length)];

            case 3:
                return words3[(int) (Math.random() * words3.length)];

            case 4:
                return words4[(int) (Math.random() * words4.length)];

            default:
                return null;

        }

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