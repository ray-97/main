package f11_1.calgo.logic.parser;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_PORTION,
                        PREFIX_CALORIES, PREFIX_PROTEIN, PREFIX_CARBOHYDRATE, PREFIX_FAT,
                        PREFIX_POSITION, PREFIX_TAG);

        Day dayVomited = new Day();
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            dayVomited = dayVomited.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        OptionalDouble portion = OptionalDouble.empty();
        if (argMultimap.getValue(PREFIX_PORTION).isPresent()) { // we need to check if "" is present
            double parsedValue = ParserUtil.parsePortion(argMultimap.getValue(PREFIX_PORTION).get());
            portion = OptionalDouble.of(parsedValue);
        }
        // if empty, delete entry entirely. get portion of current food first.
        OptionalInt IndexOfFood = OptionalInt.empty();
        if (argMultimap.getValue(PREFIX_POSITION).isPresent()) {
            IndexOfFood = ParserUtil.parsePosition(argMultimap.getValue(PREFIX_POSITION).get());
        }
        Optional<Food> optionalFood = model.getFoodByName(
                ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));

        dayVomited = dayVomited.vomit(optionalFood.get(), portion);
        return new VomitCommand(dayVomited, optionalFood.get());
    }

}
