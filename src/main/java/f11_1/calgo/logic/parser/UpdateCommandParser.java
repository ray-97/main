package f11_1.calgo.logic.parser;

import static f11_1.calgo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static f11_1.calgo.logic.parser.CliSyntax.*;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.stream.Stream;

import f11_1.calgo.logic.commands.UpdateCommand;
import f11_1.calgo.logic.parser.exceptions.ParseException;
import f11_1.calgo.model.food.*;
import f11_1.calgo.model.tag.Tag;

public class UpdateCommandParser implements Parser<UpdateCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the UpdateCommand
     * and returns an UpdateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateCommand parse(String args) throws ParseException {
        System.out.println("I AM BEING PARSED");
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CALORIES,
                        PREFIX_PROTEIN, PREFIX_CARBOHYDRATE, PREFIX_FAT, PREFIX_TAG);
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_CALORIES,
                PREFIX_PROTEIN, PREFIX_FAT, PREFIX_CARBOHYDRATE)
                || !argMultimap.getPreamble().isEmpty()) {
            // if not all prefixes are present then throw error
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Calorie calorie = ParserUtil.parseCalorie(argMultimap.getValue(PREFIX_CALORIES).get());
        Protein protein = ParserUtil.parseProtein(argMultimap.getValue(PREFIX_PROTEIN).get());
        Carbohydrate carbohydrate = ParserUtil.parseCarbohydrate(argMultimap.getValue(PREFIX_CARBOHYDRATE).get());
        Fat fat = ParserUtil.parseFat(argMultimap.getValue(PREFIX_FAT).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Food food = new Food(name, calorie, protein, carbohydrate, fat, tagList);

        return new UpdateCommand(food);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {

        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
