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
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.food.Food;

/**
 * Parses input arguments and creates a new VomitCommand object
 */
public class VomitCommandParser implements Parser<VomitCommand> {

    public static final String MESSAGE_FOOD_NOT_IN_LOG = "You cannot vomit something that's not in your stomach!";
    public static final String MESSAGE_NONEXISTENT_LOG = "You have not eaten on %s yet!";

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

        DailyFoodLog foodLog = new DailyFoodLog();
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            foodLog = foodLog.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
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
        } // after GUI
        Optional<Food> optionalFood = model.getFoodByName(
                ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        if (!model.hasLogWithSameDate(foodLog)) {
            throw new ParseException(String.format(MESSAGE_NONEXISTENT_LOG, foodLog.getLocalDate()));
        }
        foodLog = model.getLogByDate(foodLog.getLocalDate());
        try {
            foodLog = foodLog.vomit(optionalFood.get(), portion);
        } catch (IllegalArgumentException e) {
            // when food item does not exist in log
            throw new ParseException(MESSAGE_FOOD_NOT_IN_LOG);
        }

        return new VomitCommand(foodLog, optionalFood.get());
    }

}
