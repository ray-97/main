package life.calgo.logic.parser.exceptions;

import life.calgo.commons.exceptions.IllegalValueException;

/**
 * Represents a parse error encountered by a parser.
 */
public class ParseException extends IllegalValueException {

    /**
     * Constructor for ParseException.
     *
     * @param message The message to represent the ParseException.
     */
    public ParseException(String message) {
        super(message);
    }

    /**
     * Constructor for ParseException stating the cause of the exception.
     *
     * @param message The message to represent the ParseException.
     * @param cause The cause of the ParseException.
     */
    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
