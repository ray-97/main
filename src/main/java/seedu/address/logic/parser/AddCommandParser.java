package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CARBOHYDRATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROTEIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Carbohydrate;
import seedu.address.model.person.Fat;
import seedu.address.model.person.Protein;
import seedu.address.model.person.Name;
import seedu.address.model.person.Food;
import seedu.address.model.person.Calorie;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CALORIES,
                        PREFIX_PROTEIN, PREFIX_CARBOHYDRATE, PREFIX_FAT,
                        PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_CALORIES,
                PREFIX_PROTEIN, PREFIX_FAT, PREFIX_CARBOHYDRATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Calorie calorie = ParserUtil.parseCalorie(argMultimap.getValue(PREFIX_CALORIES).get());
        Protein protein = ParserUtil.parseProtein(argMultimap.getValue(PREFIX_PROTEIN).get());
        Carbohydrate carbohydrate = ParserUtil.parseCarbohydrate(argMultimap.getValue(PREFIX_CARBOHYDRATE).get());
        Fat fat = ParserUtil.parseFat(argMultimap.getValue(PREFIX_FAT).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Food food = new Food(name, calorie, protein, carbohydrate, fat, tagList);

        return new AddCommand(food);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
