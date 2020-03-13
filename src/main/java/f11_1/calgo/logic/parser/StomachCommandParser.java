package f11_1.calgo.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PORTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;

import seedu.address.logic.commands.Command;
import f11_1.calgo.logic.commands.StomachCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import f11_1.calgo.model.day.Day;

public class StomachCommandParser extends Parser<StomachCommand> {

    private final Model model;

    public StomachCommandParser(Model model) {
        this.model = model;
    }

    public StomachCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_POSITION, PREFIX_PORTION, PREFIX_DATE);
        // are we supposed to include all existing prefixes then see if the ones interested are present?

        Day dayConsumed = new Day();
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            dayConsumed = dayConsumed.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
            // figure out how exceptions are handled for AB3.
        }

        // does something with the filtered list -> displays stuff
}
