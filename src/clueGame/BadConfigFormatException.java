package clueGame;

/**
 * BadConfigFormatException
 * A part of Clue Init to detect a bad configuration.
 * @author Keenan Schott
 * @author Finn Burns
 */
public class BadConfigFormatException extends Exception {

    public BadConfigFormatException() {
        super("Invalid file configuration.");
    }

    public BadConfigFormatException(String errorMessage) {
        super(errorMessage);
    }
    
}