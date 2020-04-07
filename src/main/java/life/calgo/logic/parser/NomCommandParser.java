package life.calgo.logic.parser;

import static java.util.Objects.requireNonNull;
import static life.calgo.logic.parser.CliSyntax.PREFIX_CALORIES;
import static life.calgo.logic.parser.CliSyntax.PREFIX_CARBOHYDRATE;
import static life.calgo.logic.parser.CliSyntax.PREFIX_DATE;
import static life.calgo.logic.parser.CliSyntax.PREFIX_FAT;
import static life.calgo.logic.parser.CliSyntax.PREFIX_NAME;
import static life.calgo.logic.parser.CliSyntax.PREFIX_PORTION;
import static life.calgo.logic.parser.CliSyntax.PREFIX_PROTEIN;
import static life.calgo.logic.parser.CliSyntax.PREFIX_RATING;
import static life.calgo.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;

import life.calgo.commons.core.Messages;
import life.calgo.logic.commands.NomCommand;
import life.calgo.logic.parser.exceptions.ParseException;
import life.calgo.model.Model;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.food.Food;

/**
 * Parses input arguments in order to create a new NomCommand object.
 */
public class NomCommandParser implements Parser<NomCommand> {

    public static final String MESSAGE_NONEXISTENT_FOOD =
            "You can't eat that because it does not exist in food record.";

    private final Model model;

    private static final double DEFAULT_PORTION = 1.0;

    public NomCommandParser(Model model) {
        this.model = model;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the NomCommand.
     * @param args A String of arguments provided by user.
     * @return A NomCommand object for execution.
     * @throws ParseException If user does not conform to expected format.
     */
    public NomCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_PORTION, PREFIX_RATING,
                        PREFIX_CALORIES, PREFIX_PROTEIN, PREFIX_CARBOHYDRATE, PREFIX_FAT, PREFIX_TAG);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, NomCommand.MESSAGE_USAGE));
        }

        DailyFoodLog foodLog = fixNomDate(new DailyFoodLog(), argMultimap);
        double portion = fixNomPortion(argMultimap);
        Optional<Food> optionalFood = fixNomFood(argMultimap);

        assert (!optionalFood.get().equals(Optional.empty()));

        foodLog = foodLog.consume(optionalFood.get(), portion);
        foodLog = fixNomRating(foodLog, optionalFood, argMultimap);

        return new NomCommand(foodLog, optionalFood.get());
    }

    /**
     * Returns a DailyFoodLog with date set from input stored in an ArgumentMultimap.
     * @param toFix DailyFoodLog that you want to have date set.
     * @param argMultimap ArgumentMultimap containing value of date.
     * @return DailyFoodLog with updated date.
     * @throws ParseException If date is not valid, compared to Calendar.
     */
    private DailyFoodLog fixNomDate(DailyFoodLog toFix, ArgumentMultimap argMultimap) throws ParseException {
        DailyFoodLog foodLog = toFix;
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            foodLog = foodLog.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (model.hasLogWithSameDate(foodLog)) {
            foodLog = model.getLogByDate(foodLog.getLocalDate());
        }
        return foodLog;
    }

    private double fixNomPortion(ArgumentMultimap argMultimap) throws ParseException {
        double portion = DEFAULT_PORTION;
        if (argMultimap.getValue(PREFIX_PORTION).isPresent()) {
            portion = ParserUtil.parsePortion(argMultimap.getValue(PREFIX_PORTION).get());
        }
        return portion;
    }

    private Optional<Food> fixNomFood(ArgumentMultimap argMultimap) throws ParseException {
        Optional<Food> optionalFood = model.getFoodByName(
                ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        if (!optionalFood.isPresent()) {
            throw new ParseException(MESSAGE_NONEXISTENT_FOOD);
        }
        return optionalFood;
    }

    private DailyFoodLog fixNomRating(
            DailyFoodLog toFix, Optional<Food> optionalFood, ArgumentMultimap argMultimap)
            throws ParseException {
        DailyFoodLog foodLog = toFix;
        if (argMultimap.getValue(PREFIX_RATING).isPresent()) {
            int rating = ParserUtil.parseRating(argMultimap.getValue(PREFIX_RATING).get());
            foodLog = foodLog.addRating(optionalFood.get(), rating);
        }
        return foodLog;
    }
}

