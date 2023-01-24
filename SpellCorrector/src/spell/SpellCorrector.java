package spell;

import java.io.IOException;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {

    private Trie dictionary = new Trie();

    public void useDictionary(String dictionaryFileName) throws IOException {
        var scanner = new Scanner(dictionaryFileName);

        while (scanner.hasNextLine()) {
            var line = scanner.nextLine();
            dictionary.add(line);
        }
        scanner.close();
    }

    public String suggestSimilarWord(String inputWord) {
        return "";
    }
}
