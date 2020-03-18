package life.calgo.logic.parser;

import static java.util.Objects.requireNonNull;

import static life.calgo.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;
import java.util.stream.Stream;

import life.calgo.commons.core.Messages;
import life.calgo.logic.commands.ReportCommand;
import life.calgo.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments in order to create a new ReportCommand object.
 */
public class ReportCommandParser implements Parser<ReportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ReportCommand
     * @param args a String of arguments provided by user
     * @return a ReportCommand object for execution
     * @throws ParseException if user does not conform to expected format
     */
    public ReportCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    ReportCommand.MESSAGE_USAGE));
        }

        // at this breakpoint, PREFIX_DATE is present in args
        LocalDate queryDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        return new ReportCommand(queryDate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
