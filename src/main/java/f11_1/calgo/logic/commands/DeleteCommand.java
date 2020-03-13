package f11_1.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import f11_1.calgo.model.Model;
import f11_1.calgo.commons.core.Messages;
import f11_1.calgo.commons.core.index.Index;
import f11_1.calgo.logic.commands.exceptions.CommandException;
import f11_1.calgo.model.food.Food;

/**
 * Deletes a food identified using its displayed index from the food record.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the food identified by the index number used in the displayed food record.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_FOOD_SUCCESS = "Deleted food: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Food> lastShownList = model.getFilteredFoodRecord();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
        }

        Food foodToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteFood(foodToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_FOOD_SUCCESS, foodToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
