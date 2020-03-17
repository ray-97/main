package life.calgo.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import life.calgo.logic.commands.StomachCommand;
import life.calgo.logic.parser.exceptions.ParseException;

public class StomachCommandParser implements Parser<StomachCommand> {

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
