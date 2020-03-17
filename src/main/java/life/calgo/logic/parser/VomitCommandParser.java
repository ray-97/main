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
import static life.calgo.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;

import life.calgo.logic.commands.VomitCommand;
import life.calgo.logic.parser.exceptions.ParseException;
import life.calgo.model.Model;
import life.calgo.model.day.Day;
import life.calgo.model.food.Food;

/**
 * Parses input arguments and creates a new VomitCommand object
 */
public class VomitCommandParser implements Parser<VomitCommand> {

    private final Model model;

    public VomitCommandParser(Model model) {
        this.model = model;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the VomitCommand
     * @param args given String of arguments
     * @return a VomitCommand object for execution
     * @throws ParseException if the user does not conform to the expected format
     */
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
        OptionalInt indexOfFood = OptionalInt.empty();
        if (argMultimap.getValue(PREFIX_POSITION).isPresent()) {
            indexOfFood = ParserUtil.parsePosition(argMultimap.getValue(PREFIX_POSITION).get());
        }
        Optional<Food> optionalFood = model.getFoodByName(
                ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));

        dayVomited = dayVomited.vomit(optionalFood.get(), portion);
        return new VomitCommand(dayVomited, optionalFood.get());
    }

}
