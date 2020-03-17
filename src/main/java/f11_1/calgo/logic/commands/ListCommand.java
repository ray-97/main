package f11_1.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import f11_1.calgo.model.Model;

/**
 * Lists all foods in the food record to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all foods";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredFoodRecord(Model.PREDICATE_SHOW_ALL_FOODS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
