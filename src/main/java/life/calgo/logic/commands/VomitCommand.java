package life.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import life.calgo.commons.util.CollectionUtil;
import life.calgo.logic.commands.exceptions.CommandException;
import life.calgo.logic.parser.CliSyntax;
import life.calgo.model.Model;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.food.Food;

/**
 * Command to decrement quantity of consumption of a food item from a given day.
 */
public class VomitCommand extends Command {

    public static final String COMMAND_WORD = "vomit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a certain amount of food consumed. "
            + "Parameters: "
            + CliSyntax.PREFIX_POSITION + "POSITION "
            + "[" + CliSyntax.PREFIX_DATE + "DATE] "
            + "[" + CliSyntax.PREFIX_PORTION + "PORTION]\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_POSITION + "1 "
            + CliSyntax.PREFIX_DATE + "2019-01-03 "
            + CliSyntax.PREFIX_PORTION + "2";

    private static final String MESSAGE_SUCCESS = "Successfully throw up %1$s";

    private final DailyFoodLog foodLog;
    private final Food foodVomited;

    public VomitCommand(DailyFoodLog foodLog, Food foodVomited) {
        CollectionUtil.requireAllNonNull(foodLog, foodVomited);
        this.foodLog = foodLog;
        this.foodVomited = foodVomited;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateLog(foodLog);
        model.updateDate(foodLog.getLocalDate());
        model.updateCurrentFilteredDailyList(Model.PREDICATE_SHOW_ALL_CONSUMED_FOODS,
                foodLog.getLocalDate());
        return new CommandResult(String.format(MESSAGE_SUCCESS, foodVomited));
    }
}
