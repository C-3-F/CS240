package spell;

import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.io.File;

public class SpellCorrector implements ISpellCorrector {

    private Trie dictionary = new Trie();
    Set<String> editStrings = new HashSet<String>();

    public void useDictionary(String dictionaryFileName) throws IOException {
        var file = new File(dictionaryFileName);
        var scanner = new Scanner(file);


        while (scanner.hasNext())
        {
            var word = scanner.next();
            dictionary.add(word);
        }
        scanner.close();
    }

    public String suggestSimilarWord(String inputWord) {

        editStrings.clear();
        inputWord = inputWord.toLowerCase();

        var foundNode = dictionary.find(inputWord);
        if (foundNode != null) {
            return inputWord;
        }

        alteration(inputWord);
        deletion(inputWord);
        insertion(inputWord);
        transposition(inputWord);

        Node suggestedNode = new Node();
        String suggestedWord = inputWord;

        for (String s : editStrings) {
            var tempNode = dictionary.find(s);
            if (tempNode != null && tempNode.getValue() > suggestedNode.getValue()) {
                suggestedNode = tempNode;
                suggestedWord = s;
            }
        }

        // Checking to see if a word was found
        if (suggestedWord != inputWord) {
            return suggestedWord;
        }

        var tempSet  = new HashSet<>(editStrings);
        for (String s : tempSet) {
            alteration(s);
            deletion(s);
            insertion(s);
            transposition(s);
        }

        for (String s : editStrings) {
            var tempNode = dictionary.find(s);
            if (tempNode != null && tempNode.getValue() > suggestedNode.getValue()) {
                suggestedNode = tempNode;
                suggestedWord = s;
            }
        }

        if (suggestedWord != inputWord) {
            return suggestedWord;
        } else {
            return null;
        }
    }

    private void alteration(String word) {
        final char[] charWord = word.toCharArray();
        char[] temp = charWord.clone();
        for (int i = 0; i < charWord.length; i++) {
            for (int j = 0; j < 26; j++) {
                temp = charWord.clone();
                temp[i] = (char) ('a' + j);
                editStrings.add(new String(temp));
            }
        }
    }

    private void deletion(String word) {
        for (int i = 0; i < word.length(); i++) {
            var sb = new StringBuilder(word);
            sb.deleteCharAt(i);
            editStrings.add(sb.toString());
        }
    }

    private void insertion(String word) {
        for (int i = 0; i < word.length() + 1; i++) {
            for (int j = 0; j < 26; j++) {
                var sb = new StringBuilder(word);
                sb.insert(i, (char) ('a' + j));
                editStrings.add(sb.toString());
            }
        }
    }

    private void transposition(String word) {
        for (int i = 0; i < word.length() - 1; i++) {
            var charArr = word.toCharArray();
            char temp = charArr[i];
            charArr[i] = charArr[i + 1];
            charArr[i + 1] = temp;
            editStrings.add(new String(charArr));
        }
    }
}
