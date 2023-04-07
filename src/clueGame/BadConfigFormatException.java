package clueGame;

/**
 * BadConfigFormatException
 * The Exception to be thrown when we encounter a bad configuration in the Board
 * class when we read in the input files; either a standard message or custom
 * message can be used.
 * DATE: 3/26/2023
 * 
 * @author Keenan Schott
 * @author Finn Burns
 */
public class BadConfigFormatException extends Exception {

    /**
     * Constructor with no parameters; default message.
     */
    public BadConfigFormatException() {
        super("Setup file contains configuration errors!");
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