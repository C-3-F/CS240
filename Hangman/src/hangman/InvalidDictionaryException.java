package hangman;

public class InvalidDictionaryException extends Exception {

    public InvalidDictionaryException() {

    }

    public InvalidDictionaryException(String message) {
        super(message);
    }
}
