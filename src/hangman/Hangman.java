package hangman;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Hangman {

    private ArrayList<String> wordList;

    private String word;

    private int wrongGuesses;

    private boolean wordFound;

    private ArrayList<Character> allLettersGuessed;
    private HashMap<String, Boolean> correctLettersGuessed;
    private char[] wrongLettersGuessed;

    public Hangman() {
        wrongGuesses = 0;
        wordList = getWordList();
        word = pickWord();
        wrongLettersGuessed = new char[6];
        initLetters();
        wordFound = false;
        allLettersGuessed = new ArrayList<>();
        gameLoop();
        if (wordFound) {
            System.out.println("You win!");
            System.out.println("The word was: " + word);
        }
    }

    private void gameLoop() {
        System.out.println(word);
        while (wrongGuesses<6 && !wordFound) {
            if(isLetterInWord(takeInput()))
                System.out.println("Correct!");
            printProgress();
        }
    }

    private void printProgress() {
        switch(wrongGuesses) {
            case 0:
                printStage1();
                break;
            case 1:
                printStage2();
                break;
            case 2:
                printStage3();
                break;
            case 3:
                printStage4();
                break;
            case 4:
                printStage5();
                break;
            case 5:
                printStage6();
                break;
            case 6:
                printLose();
                break;
        }
        StringBuilder wrongLetters = new StringBuilder();
        wrongLetters.append("'");
        for (char x: wrongLettersGuessed) {
            wrongLetters.append(x);
            wrongLetters.append("', '");
        }
        System.out.println("Letters wrong: " + wrongLetters);
        StringBuilder currentlyFoundWord = new StringBuilder();
        String[] wordArray = word.split("");
        for (int i=0;i <correctLettersGuessed.size(); i++) {
            if (correctLettersGuessed.get(wordArray[i])) {
                currentlyFoundWord.append(wordArray[i]);
            } else {
                currentlyFoundWord.append("_");
            }

        }
        if (!wordFound)
            System.out.println(currentlyFoundWord.toString());
    }



    private void printStage1() {
        System.out.println("____________");
        System.out.println("|          |");
        System.out.println("           |");
        System.out.println("           |");
        System.out.println("           |");
        System.out.println("           |");
        System.out.println("           |");
        System.out.println("           |");
        System.out.println("           |");
        System.out.println("---------------");
    }

    private void printStage2() {
        System.out.println("____________");
        System.out.println("|          |");
        System.out.println("/ \\        |");
        System.out.println("\\_/        |");
        System.out.println("           |");
        System.out.println("           |");
        System.out.println("           |");
        System.out.println("           |");
        System.out.println("           |");
        System.out.println("---------------");
    }

    private void printStage3() {
        System.out.println("____________");
        System.out.println("|          |");
        System.out.println("/ \\        |");
        System.out.println("\\_/        |");
        System.out.println(" |         |");
        System.out.println(" |         |");
        System.out.println("           |");
        System.out.println("           |");
        System.out.println("           |");
        System.out.println("---------------");
    }

    private void printStage4() {
        System.out.println("____________");
        System.out.println("|          |");
        System.out.println("/ \\        |");
        System.out.println("\\_/        |");
        System.out.println(" |         |");
        System.out.println(" |         |");
        System.out.println("/           |");
        System.out.println("           |");
        System.out.println("           |");
        System.out.println("---------------");
    }

    private void printStage5() {
        System.out.println("____________");
        System.out.println("|          |");
        System.out.println("/ \\        |");
        System.out.println("\\_/        |");
        System.out.println(" |         |");
        System.out.println(" |         |");
        System.out.println("/ \\        |");
        System.out.println("           |");
        System.out.println("           |");
        System.out.println("---------------");
    }

    private void printStage6() {
        System.out.println(" ____________");
        System.out.println(" |          |");
        System.out.println(" / \\        |");
        System.out.println(" \\_/        |");
        System.out.println("\\_|         |");
        System.out.println("  |         |");
        System.out.println(" / \\        |");
        System.out.println("            |");
        System.out.println("            |");
        System.out.println(" ---------------");
    }

    private void printLose() {
        System.out.println(" ____________");
        System.out.println("  |          |");
        System.out.println(" / \\        |");
        System.out.println(" \\_/        |");
        System.out.println("\\_|_/       |");
        System.out.println("  |         |");
        System.out.println(" / \\        |");
        System.out.println("            |");
        System.out.println("            |");
        System.out.println(" ---------------");
        System.out.println("!!!!YOU LOSE!!!!");
    }

    private String takeInput() {
        Scanner scan = new Scanner(System.in);
        return scan.next().toLowerCase();
    }

    private boolean isLetterInWord(String character) {
        for (int i=0;i<allLettersGuessed.size();i++) {
            if (character.equalsIgnoreCase(allLettersGuessed.get(i).toString())) {
                System.out.println("You've already guessed that! Guess something else.");
                return false;
            }
        }
        allLettersGuessed.add(character.charAt(0));
        String[] wordArray = word.split("");
        for (int i=0;i<wordArray.length;i++) {
            if (character.equalsIgnoreCase(wordArray[i])) {
                correctLettersGuessed.replace(character, true);
                if (!correctLettersGuessed.containsValue(false))
                    wordFound = true;
                return true;
            }
        }
        wrongLettersGuessed[wrongGuesses] = character.charAt(0);
        wrongGuesses++;
        return false;
    }

    private void initLetters() {
        correctLettersGuessed = new HashMap<>();
        String[] lettersInWord = word.split("");
        for (int i=0; i< lettersInWord.length ; i++) {
            correctLettersGuessed.put(lettersInWord[i], false);
        }

    }

    private String pickWord() {
        Random random = new Random();
        return wordList.get(random.nextInt(wordList.size()));
    }

    private ArrayList<String> getWordList() {
        String filename = "words.txt";
        String line = null;
        ArrayList<String> words = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                //removes spaces
                words.add(line.replace(" ", ""));
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" + filename + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + filename + "'");
        }
        return words;
    }

}
