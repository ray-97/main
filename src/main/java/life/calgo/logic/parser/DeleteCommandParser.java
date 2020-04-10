package life.calgo.logic.parser;

import static life.calgo.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import life.calgo.commons.core.Messages;
import life.calgo.logic.commands.DeleteCommand;
import life.calgo.logic.parser.exceptions.ParseException;
import life.calgo.model.food.Calorie;
import life.calgo.model.food.Carbohydrate;
import life.calgo.model.food.Fat;
import life.calgo.model.food.Food;
import life.calgo.model.food.Name;
import life.calgo.model.food.Protein;
import life.calgo.model.tag.Tag;

/**
 * Parses input arguments and creates a new DeleteCommand object.
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand.
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public DeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!isNamePrefixPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteCommand.MESSAGE_USAGE));
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
    private static boolean isNamePrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
