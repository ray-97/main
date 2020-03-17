package f11_1.calgo.logic.parser;

import static f11_1.calgo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static f11_1.calgo.logic.parser.CliSyntax.*;

import java.util.HashSet;
import java.util.Set;

import f11_1.calgo.logic.commands.DeleteCommand;
import f11_1.calgo.logic.parser.exceptions.ParseException;
import f11_1.calgo.model.food.*;
import f11_1.calgo.model.tag.Tag;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!isNamePrefixPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Calorie calorie = new Calorie("0");
        Protein protein = new Protein("0");
        Carbohydrate carbohydrate = new Carbohydrate("0");
        Fat fat = new Fat("0");
        Set<Tag> tagList = new HashSet<>();

        Food food = new Food(name, calorie, protein, carbohydrate, fat, tagList);

        return new DeleteCommand(food);

    }

    /**
     * Returns true if the name prefix is present {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isNamePrefixPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }

}
