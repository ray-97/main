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

import life.calgo.commons.core.Messages;
import life.calgo.logic.commands.NomCommand;
import life.calgo.logic.parser.exceptions.ParseException;
import life.calgo.model.Model;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.food.Food;

/**
 * Parses input arguments in order to create a new NomCommand object
 */
public class NomCommandParser implements Parser<NomCommand> {

    public static final String MESSAGE_EMPTY_NAME = "You can't eat that, you have to name a food that exists";

    private final Model model;

    public NomCommandParser(Model model) {
        this.model = model;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the NomCommand
     * @param args a String of arguments provided by user
     * @return a NomCommand object for execution
     * @throws ParseException if user does not conform to expected format
     */
    public NomCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_PORTION,
                        PREFIX_CALORIES, PREFIX_PROTEIN, PREFIX_CARBOHYDRATE, PREFIX_FAT,
                        PREFIX_POSITION, PREFIX_TAG);
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, NomCommand.MESSAGE_USAGE));
        }

        DailyFoodLog foodLog = new DailyFoodLog();
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            foodLog = foodLog.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (model.hasLogWithSameDate(foodLog)) {
            foodLog = model.getLogByDate(foodLog.getLocalDate());
        }
        double portion = 1;
        if (argMultimap.getValue(PREFIX_PORTION).isPresent()) {
            portion = ParserUtil.parsePortion(argMultimap.getValue(PREFIX_PORTION).get());
        }
        Optional<Food> optionalFood = model.getFoodByName(
                ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        if (!optionalFood.isPresent()) {
            throw new ParseException(MESSAGE_EMPTY_NAME);
        }
        foodLog = foodLog.consume(optionalFood.get(), portion);
        return new NomCommand(foodLog, optionalFood.get());
    }
}
