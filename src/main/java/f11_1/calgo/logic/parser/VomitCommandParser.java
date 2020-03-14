package f11_1.calgo.logic.parser;

import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_DATE;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_NAME;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_PORTION;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_POSITION;
import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;

import f11_1.calgo.logic.commands.VomitCommand;
import f11_1.calgo.logic.parser.exceptions.ParseException;
import f11_1.calgo.model.Model;
import f11_1.calgo.model.day.Day;
import f11_1.calgo.model.food.Food;

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

        Day dayVommited = new Day();
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            dayVommited = dayVommited.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
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
        Optional<Food> optionalFood = model.getFoodByName(
                ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));

        dayVommited = dayVommited.vomit(optionalFood.get(), portion);
        return new VomitCommand(dayVommited, optionalFood.get());
    }

}
