package hangman;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class EvilHangman {

    public static void main(String[] args) {
        String fileName = args[0];
        int wordLength = Integer.parseInt(args[1]);
        int guessCount = Integer.parseInt(args[2]);

        // Setup Game
        var game = new EvilHangmanGame();
        File dict = new File(fileName);
        while (true) {
            try {
                game.startGame(dict, wordLength);
                break;
            } catch (IOException e) {
                System.out.println("IOEXCEPTION:");
                System.out.println(e.getMessage());
            } catch (EmptyDictionaryException e) {
                System.out.println("DICTIONARY EXCEPTION:");
                System.out.println(e.getMessage());
            }

        }

        var userInput = new Scanner(System.in);

        var currKey = game.getMapKey();
        Set<String> returnedWords = null;
        // Take turns
        for (int i = guessCount; i > 0; i--) {
            System.out.println(String.format("You have %d guesses left", i));
            var usedLetters = game.getGuessedLetters();
            System.out.print("Used Letters:");
            for (var s : usedLetters) {
                System.out.print(" " + s);
            }
            System.out.print("\n");

            System.out.println(String.format("Word: %s", currKey));

            System.out.print("Enter guess: ");

            String guess = userInput.nextLine();
            char guessChar = guess.charAt(0);

            try {
                returnedWords = game.makeGuess(guessChar);
                currKey = game.getMapKey();
                var currKeyChars = currKey.toCharArray();
                int newLetterCount = 0;
                for (int j = 0; j < currKeyChars.length; j++) {
                    if (currKeyChars[i] == guessChar) {
                        newLetterCount += 1;
                    }
                }

                if (newLetterCount > 1) {
                    System.out.println(String.format("Yes, there are %d %c's", newLetterCount, guessChar));
                    i += 1;
                } else if (newLetterCount > 0) {
                    System.out.println(String.format("Yes, there is %d %c", newLetterCount, guessChar));
                    i += 1;
                } else {
                    System.out.println(String.format("Sorry, there are no %c's", guessChar));
                }

            } catch (GuessAlreadyMadeException e) {
                System.out.println(String.format("You already guessed '&s'. Please try again", guessChar));
                i += 1;
                continue;
            }

        }

        // End Game
        if (returnedWords.size() == 1) {
            System.out.println("You Win!");
            System.out.println(String.format("The word was: %s", returnedWords.toArray()[0]));
        } else {
            System.out.println("You Lose!");
            System.out.println(String.format("The word was: %s", returnedWords.toArray()[0]));
        }

        userInput.close();
    }

}
