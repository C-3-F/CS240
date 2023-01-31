package hangman;

import java.io.IOException;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.lang.model.util.ElementScanner14;

public class EvilHangmanGame implements IEvilHangmanGame {

    private SortedSet<Character> _guessedLetters = new TreeSet<Character>();
    private Set<String> _dictionary = new HashSet<String>();
    private String mapKey = "";

    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        _dictionary.clear();
        mapKey = "";
        var scanner = new Scanner(dictionary);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wordLength; i++) {
            sb.append('-');
        }
        mapKey = sb.toString();

        while (scanner.hasNext()) {
            var word = scanner.next();
            if (!word.matches("[a-zA-Z]+")) {
                scanner.close();
                throw new EmptyDictionaryException("Dictionary contains invalid characters");
            }
            if (word.length() == wordLength) {
                _dictionary.add(word);
            }
        }
        scanner.close();

        if (_dictionary.size() == 0) {
            throw new EmptyDictionaryException();
        }

    }

    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {

        guess = Character.toLowerCase(guess);

        if (_guessedLetters.contains(guess)) {
            throw new GuessAlreadyMadeException();
        }
        _guessedLetters.add(guess);

        var wordPartition = new HashMap<String, HashSet<String>>();
        for (String w : _dictionary) {
            var sb = new StringBuilder(mapKey);
            var wordChars = w.toCharArray();
            for (int i = 0; i < wordChars.length; i++) {
                if (wordChars[i] == guess) {
                    sb.insert(i, guess);
                    sb.deleteCharAt(i+1);

                }
            }
            var partitionSet = wordPartition.get(sb.toString());
            if (partitionSet == null) {
                partitionSet = new HashSet<String>();
                wordPartition.put(sb.toString(), partitionSet);
            }
            partitionSet.add(w);
        }

        var biggestSet = new HashSet<String>();
        String biggestSetKey = "";
        for (Map.Entry<String, HashSet<String>> set : wordPartition.entrySet()) {
            if (set.getValue().size() > biggestSet.size()) {
                biggestSet = set.getValue();
                biggestSetKey = set.getKey();
            }

            // TIEBREAKER
            if (set.getValue().size() == biggestSet.size() && determineTiebreaker(biggestSetKey, set.getKey(), guess)) {
                biggestSet = set.getValue();
                biggestSetKey = set.getKey();
            }
        }

        mapKey = biggestSetKey;
        _dictionary = biggestSet;
        return _dictionary;
    }

    public SortedSet<Character> getGuessedLetters() {
        return _guessedLetters;
    }

    public String getMapKey() {
        return mapKey;
    }

    private boolean determineTiebreaker(String originalKey, String tiebreakerKey, char guess) {
        if (originalKey == tiebreakerKey)
        {
            return false;
        }
        if (tiebreakerKey == mapKey) { // Checks if the biggest set is the one that didn't add the letter.
            return true;
        } else {
            var biggestSetKeyChars = originalKey.toCharArray();
            var tieSetKeyChars = tiebreakerKey.toCharArray();
            int bigGuessCharCount = 0;
            int tieGuessCharCount = 0;
            for (int i = biggestSetKeyChars.length - 1; i >= 0; i--) { // Iterates through each set and tallies
                                                                      // amount
                // of times the new letter was added
                if (biggestSetKeyChars[i] == guess) {
                    bigGuessCharCount += 1;
                }
                if (tieSetKeyChars[i] == guess) {
                    tieGuessCharCount += 1;
                }
            }
            if (tieGuessCharCount < bigGuessCharCount) {
                return true;
            }
            if (tieGuessCharCount == bigGuessCharCount) {
                for (int i = biggestSetKeyChars.length - 1; i >= 0; i--) {
                    if (biggestSetKeyChars[i] == guess && tieSetKeyChars[i] == guess) {
                        continue;
                    }
                    if (biggestSetKeyChars[i] == guess && tieSetKeyChars[i] != guess) {
                        return false;
                    }
                    if (biggestSetKeyChars[i] != guess && tieSetKeyChars[i] == guess) {
                        return true;

                    }
                }
            }
        }
        return false;
    }

}
