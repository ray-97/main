package f11_1.calgo.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PORTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.OptionalDouble;
import java.util.OptionalInt;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import f11_1.calgo.logic.commands.VomitCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import f11_1.calgo.model.day.Day;
import seedu.address.model.food.Food;

public class VomitCommandParser implements Parser<VomitCommand> {

    private final Model model;

    public VomitCommandParser(Model model) {
        this.model = model;
    }

    public VomitCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_POSITION, PREFIX_PORTION, PREFIX_DATE);
        // are we supposed to include all existing prefixes then see if the ones interested are present?

        Day dayConsumed = new Day();
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            dayConsumed = dayConsumed.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
            // figure out how exceptions are handled for AB3.
        }
        OptionalDouble portion = OptionalDouble.empty();
        if (argMultimap.getValue(PREFIX_PORTION).isPresent()) {
            double parsedValue = ParserUtil.parsePortion(argMultimap.getValue(PREFIX_PORTION).get());
            portion = OptionalDouble.of(parsedValue);
        }
        // if empty, delete entry entirely. get portion of current food first.
        OptionalInt IndexOfFood = OptionalInt.empty();
        if (argMultimap.getValue(PREFIX_POSITION).isPresent()) {
            IndexOfFood = ParserUtil.parsePosition(argMultimap.getValue(PREFIX_POSITION).get());
        }
        // now we got portion, figure out how to deal with food. extract from daily log, then use num and portion!
        Food food = setPortion(portion);
        dayConsumed = dayConsumed.consume(food);

        return new VomitCommand(model, );
    }

}
