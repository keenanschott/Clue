package clueGame;

/**
 * BadConfigFormatException
 * A part of Clue Init to detect a bad configuration.
 * @author Keenan Schott
 * @author Finn Burns
 */
public class BadConfigFormatException extends Exception {

    /**
     * Constructor with no parameters; default message.
     */
    public BadConfigFormatException() {
        super("Invalid file configuration.");
    }

    /**
     * Constructor with a String parameter.
     * 
     * @param errorMessage The given error message for the exception.
     */
    public BadConfigFormatException(String errorMessage) {
        super(errorMessage);
    }
}