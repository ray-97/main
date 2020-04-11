package life.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import life.calgo.model.FoodRecord;
import life.calgo.model.Model;

/**
 * Clears the food record.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Food Record has been cleared! "
            + "Use the update command to add new food into your Food Record.";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setFoodRecord(new FoodRecord());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
