package life.calgo.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import life.calgo.logic.commands.StomachCommand;
import life.calgo.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new StomachCommand object
 */
public class StomachCommandParser implements Parser<StomachCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StomachCommand
     * @param args a String of arguments provided by user
     * @return a StomachCommand object for execution
     * @throws ParseException if the user does not conform to the expected format
     */
    public StomachCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_DATE, CliSyntax.PREFIX_PORTION,
                        CliSyntax.PREFIX_CALORIES, CliSyntax.PREFIX_PROTEIN, CliSyntax.PREFIX_CARBOHYDRATE,
                        CliSyntax.PREFIX_FAT, CliSyntax.PREFIX_POSITION, CliSyntax.PREFIX_TAG);
        LocalDate date = LocalDate.now();
        if (argMultimap.getValue(CliSyntax.PREFIX_DATE).isPresent()) {
            date = ParserUtil.parseDate(argMultimap.getValue(CliSyntax.PREFIX_DATE).get());
        }
        return new StomachCommand(date);
    }

}
