package f11_1.calgo.logic.parser;

import static f11_1.calgo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_CALORIES;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_CARBOHYDRATE;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_DATE;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_FAT;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_NAME;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_PORTION;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_POSITION;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_PROTEIN;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import f11_1.calgo.logic.commands.NomCommand;
import f11_1.calgo.logic.commands.StomachCommand;
import f11_1.calgo.logic.parser.exceptions.ParseException;

public class StomachCommandParser implements Parser<StomachCommand> {

    public StomachCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_PORTION,
                        PREFIX_CALORIES, PREFIX_PROTEIN, PREFIX_CARBOHYDRATE, PREFIX_FAT,
                        PREFIX_POSITION, PREFIX_TAG);
        LocalDate date = LocalDate.now();
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        }
        return new StomachCommand(date);
    }

}
