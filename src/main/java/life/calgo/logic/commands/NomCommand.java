package life.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import life.calgo.commons.util.CollectionUtil;
import life.calgo.logic.commands.exceptions.CommandException;
import life.calgo.logic.parser.CliSyntax;
import life.calgo.model.Model;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.food.Food;

/**
 * Updates the food consumed on a given day.
 */
public class NomCommand extends Command {

    public static final String COMMAND_WORD = "nom";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a food to your consumption record. "
            + "Parameters: "
            + CliSyntax.PREFIX_NAME + "NAME "
            + "[" + CliSyntax.PREFIX_DATE + "DATE] "
            + "[" + CliSyntax.PREFIX_PORTION + "PORTION]\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "Kiwi "
            + CliSyntax.PREFIX_DATE + "2020-03-14 "
            + CliSyntax.PREFIX_PORTION + "2";

    public static final String MESSAGE_SUCCESS = "Successfully consumed %1$s";

    private final DailyFoodLog foodLog;
    private final Food foodConsumed;

    public NomCommand(DailyFoodLog foodLog, Food foodConsumed) {
        CollectionUtil.requireAllNonNull(foodLog, foodConsumed);
        this.foodLog = foodLog;
        this.foodConsumed = foodConsumed;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasLogWithSameDate(foodLog)) {
            model.addLog(foodLog);
        } else {
            model.updateLog(foodLog);
        }
        model.updateCurrentFilteredDailyList(Model.PREDICATE_SHOW_ALL_CONSUMED_FOODS,
                foodLog.getLocalDate()); // updates display
        return new CommandResult(String.format(MESSAGE_SUCCESS, foodConsumed));
        // , dayConsumed.getPortion(foodConsumed), dayConsumed.getLocalDate()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NomCommand // instanceof handles nulls
                && foodConsumed.equals(((NomCommand) other).foodConsumed)
                && foodLog.equals(((NomCommand) other).foodLog));
    }
}
