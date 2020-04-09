package life.calgo.logic.parser;

import static java.util.Objects.requireNonNull;
import static life.calgo.logic.parser.CliSyntax.PREFIX_CALORIES;
import static life.calgo.logic.parser.CliSyntax.PREFIX_CARBOHYDRATE;
import static life.calgo.logic.parser.CliSyntax.PREFIX_DATE;
import static life.calgo.logic.parser.CliSyntax.PREFIX_FAT;
import static life.calgo.logic.parser.CliSyntax.PREFIX_NAME;
import static life.calgo.logic.parser.CliSyntax.PREFIX_PORTION;
import static life.calgo.logic.parser.CliSyntax.PREFIX_POSITION;
import static life.calgo.logic.parser.CliSyntax.PREFIX_PROTEIN;
import static life.calgo.logic.parser.CliSyntax.PREFIX_RATING;
import static life.calgo.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDate;

import life.calgo.commons.core.Messages;
import life.calgo.logic.commands.StomachCommand;
import life.calgo.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new StomachCommand object.
 */
public class StomachCommandParser implements Parser<StomachCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StomachCommand.
     *
     * @param args a String of arguments provided by user.
     * @return a StomachCommand object for execution.
     * @throws ParseException if the user does not conform to the expected format.
     */
    public StomachCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_PORTION, PREFIX_RATING,
                        PREFIX_CALORIES, PREFIX_PROTEIN, PREFIX_CARBOHYDRATE,
                        PREFIX_FAT, PREFIX_POSITION, PREFIX_TAG);
        LocalDate date = LocalDate.now();
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        }
        boolean hasInvalidArg = argMultimap.getValue(PREFIX_DATE).isEmpty() && args.split(" ").length > 1;
        if (hasInvalidArg) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, StomachCommand.MESSAGE_USAGE));
        }
        return new StomachCommand(date);
    }

}
