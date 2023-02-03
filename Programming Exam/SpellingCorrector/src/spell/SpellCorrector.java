package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

public class SpellCorrector implements ISpellCorrector {

    private Trie _dictionary = new Trie();
    private TreeSet<String> potentialWords = new TreeSet<String>();

    public void useDictionary(String dictionaryFileName) throws IOException {
        File file = new File(dictionaryFileName);
        var scanner = new Scanner(file);

        while (scanner.hasNext()) {
            _dictionary.add(scanner.next());
        }

        scanner.close();
    }

    public String suggestSimilarWord(String inputWord) {
        potentialWords.clear();
        inputWord = inputWord.toLowerCase();
        if (_dictionary.find(inputWord) != null) {
            return inputWord;
        }

        doDeletion(inputWord);
        doInsertion(inputWord);
        doAlteration(inputWord);
        doTransposition(inputWord);

        var similarWordNode = new Node();
        var similarWord = inputWord;
        for (var word : potentialWords) {
            var foundWord = _dictionary.find(word);
            if (foundWord != null && foundWord.getValue() > similarWordNode.getValue()) {
                similarWordNode = foundWord;
                similarWord = word;
            }
        }

        if (similarWord != inputWord) {
            return similarWord;
        } else {
            TreeSet<String> potentialWordsCopy = (TreeSet<String>) potentialWords.clone();
            potentialWords.clear();
            for (var word : potentialWordsCopy) {
                doDeletion(word);
                doInsertion(word);
                doAlteration(word);
                doTransposition(word);
            }

            for (var word : potentialWords) {
                var foundWord = _dictionary.find(word);
                if (foundWord != null && foundWord.getValue() > similarWordNode.getValue()) {
                    similarWordNode = foundWord;
                    similarWord = word;
                }
            }
            if (similarWord != inputWord) {
                return similarWord;
            } else {
                return null;
            }
        }
    }

    private void doDeletion(String inputWord) {
        for (int i = 0; i < inputWord.length(); i++) {
            var sb = new StringBuilder(inputWord);
            sb.deleteCharAt(i);
            potentialWords.add(sb.toString());
        }

    }

    private void doInsertion(String inputWord) {
        for (int i = 0; i <= inputWord.length(); i++) {
            for (int j = 0; j < 26; j++) {
                var sb = new StringBuilder(inputWord);
                sb.insert(i, (char) ('a' + j));
                potentialWords.add(sb.toString());
            }
        }

    }

    private void doAlteration(String inputWord) {
        for (int i = 0; i < inputWord.length(); i++) {
            for (int j = 0; j < 26; j++) {
                var sb = new StringBuilder(inputWord);
                sb.deleteCharAt(i);
                sb.insert(i, (char) ('a' + j));
                potentialWords.add(sb.toString());
            }
        }

    }

    private void doTransposition(String inputWord) {
        for (int i = 0; i < inputWord.length() - 1; i++) {
            var sb = new StringBuilder(inputWord);
            char temp = sb.charAt(i);
            sb.setCharAt(i, sb.charAt(i + 1));
            sb.setCharAt(i + 1, temp);
            potentialWords.add(sb.toString());
        }
    }

}
