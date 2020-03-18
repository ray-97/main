package life.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import life.calgo.commons.util.CollectionUtil;
import life.calgo.logic.commands.exceptions.CommandException;
import life.calgo.model.Model;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.food.Food;

/**
 * Command to decrement quantity of consumption of a food item from a given day
 */
public class VomitCommand extends Command {

    public static final String COMMAND_WORD = "vomit";

    public static final String MESSAGE_SUCCESS = "Successfully throw up %1$s"; // %d portion of %s was consumed on %s

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
        model.updateCurrentFilteredDailyList(Model.PREDICATE_SHOW_ALL_CONSUMED_FOODS,
                foodLog.getLocalDate());
        return new CommandResult(String.format(MESSAGE_SUCCESS, foodVomited));
        // dayVomited.getPortion(foodVomited), foodVomited, dayVomited.getLocalDate()));
    }
}
