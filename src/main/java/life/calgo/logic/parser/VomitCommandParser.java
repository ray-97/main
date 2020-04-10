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

import java.util.Optional;
import java.util.OptionalDouble;

import life.calgo.commons.core.Messages;
import life.calgo.logic.commands.VomitCommand;
import life.calgo.logic.parser.exceptions.ParseException;
import life.calgo.model.Model;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.food.Food;

/**
 * Parses input arguments and creates a new VomitCommand object.
 */
public class VomitCommandParser implements Parser<VomitCommand> {

    public static final String MESSAGE_NONEXISTENT_LOG =
            "Record not initialized yet as you have not eaten anything on %s before.";
    public static final String MESSAGE_INVALID_POSITION = "Position required an integer within range of list!";

    private final Model model;

    public VomitCommandParser(Model model) {
        this.model = model;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the VomitCommand.
     *
     * @param args given String of arguments.
     * @return a VomitCommand object for execution.
     * @throws ParseException if the user does not conform to the expected format.
     */
    public VomitCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_PORTION, PREFIX_RATING,
                        PREFIX_CALORIES, PREFIX_PROTEIN, PREFIX_CARBOHYDRATE, PREFIX_FAT,
                        PREFIX_POSITION, PREFIX_TAG);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_POSITION)) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, VomitCommand.MESSAGE_USAGE));
        }

        DailyFoodLog foodLog = fixVomitDate(new DailyFoodLog(), argMultimap);

        OptionalDouble portion = fixVomitPortion(argMultimap);

        int indexOfFood = fixVomitIndex(argMultimap);

        foodLog = fixVomitFoodLogByDate(foodLog, model);

        Optional<Food> optionalFood = fixVomitFood(foodLog, indexOfFood);

        foodLog = foodLog.vomit(optionalFood.get(), portion);

        assert (!optionalFood.get().equals(Optional.empty()));

        return new VomitCommand(foodLog, optionalFood.get());
    }

    /**
     * Acts as a helper function for getting the date that vomit occurs.
     *
     * @param toFix DailyFoodLog that needs date to be set.
     * @param argMultimap ArgumentMultimap containing prefix of date mapped to its value.
     * @return DailyFoodLog with the date set.
     * @throws ParseException If date is not valid according to calendar, or if date has invalid format.
     */
    private DailyFoodLog fixVomitDate(DailyFoodLog toFix, ArgumentMultimap argMultimap) throws ParseException {
        DailyFoodLog foodLog = toFix;
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            foodLog = foodLog.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        return foodLog;
    }

    /**
     * Acts as a helper function for getting the portion that you wish to vomit.
     *
     * @param argMultimap ArgumentMultimap containing prefix of portion mapped to its value.
     * @return OptionalDouble representing the portion.
     * @throws ParseException If value's string representation exceeds 10 character or is negative.
     */
    private OptionalDouble fixVomitPortion(ArgumentMultimap argMultimap) throws ParseException {
        OptionalDouble portion = OptionalDouble.empty();
        if (argMultimap.getValue(PREFIX_PORTION).isPresent()) {
            double parsedValue = ParserUtil.parsePortion(argMultimap.getValue(PREFIX_PORTION).get());
            portion = OptionalDouble.of(parsedValue);
        }
        return portion;
    }

    /**
     * Acts as a helper function for getting the index of food that you wish to vomit.
     *
     * @param argMultimap ArgumentMultimap containing prefix of portion mapped to its value.
     * @return Int representing the index of food to be removed.
     * @throws ParseException If index is out of bound.
     */
    private int fixVomitIndex(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parsePosition(argMultimap.getValue(PREFIX_POSITION).get()) - 1;
    }

    /**
     * Acts as a helper function for getting the corresponding DailyFoodLog to the date where vomit happens.
     *
     * @param toFix The DailyFoodLog used to retrieve an existing DailyFoodLog in model with the same date.
     * @param model Model representing all the data of the program.
     * @return DailyFoodLog that is retrieved from model.
     * @throws ParseException If user has not consumed anything on the date before, hence log does not exist.
     */
    private DailyFoodLog fixVomitFoodLogByDate(DailyFoodLog toFix, Model model) throws ParseException {
        DailyFoodLog foodLog = toFix;
        if (!model.hasLogWithSameDate(foodLog)) {
            throw new ParseException(String.format(MESSAGE_NONEXISTENT_LOG, foodLog.getLocalDate()));
        }
        foodLog = model.getLogByDate(foodLog.getLocalDate());
        return foodLog;
    }

    /**
     * Acts as a helper function for getting the type of Food to vomit.
     *
     * @param foodLog DailyFoodLog that contains food to vomit.
     * @param indexOfFood Index of food to vomit.
     * @return Optional wrapped Food object at specified index.
     * @throws ParseException If index out of bound.
     */
    private Optional<Food> fixVomitFood(DailyFoodLog foodLog, int indexOfFood) throws ParseException {
        try {
            return foodLog.getFoodByIndex(indexOfFood);
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(MESSAGE_INVALID_POSITION);
        }
    }

}
