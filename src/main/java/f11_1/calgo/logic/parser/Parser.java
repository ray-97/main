package f11_1.calgo.logic.parser;

<<<<<<< HEAD:src/main/java/seedu/address/logic/parser/Parser.java
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
=======
import f11_1.calgo.logic.commands.Command;
import f11_1.calgo.logic.parser.exceptions.ParseException;
>>>>>>> workingbranchfor1.2:src/main/java/f11_1/calgo/logic/parser/Parser.java

/**
 * Represents a Parser that is able to parse user input into a {@code Command} of type {@code T}.
 */
public interface Parser<T extends Command> {

    /**
     * Parses {@code userInput} into a command and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    T parse(String userInput) throws ParseException;
}
