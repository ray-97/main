package life.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import life.calgo.model.Model;

/**
 * Lists all Food entries in the FoodRecord for the user to easily refer to.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS =
            "Listed all foods.\nIf your Food Record appears empty, it's time to start making entries! :)";

    /**
     * Lists all the Food in the GUI.
     * This relies on a Predicate that all Food entries satisfy, which is used in filtering through the FoodRecord.
     *
     * @param model {@code Model} which the command should operate on.
     * @return the CommandResult which shows the appropriate response for the user for this command.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // the updateFilteredFooodRecord method only shows Food entries satisfying the predicate
        model.updateFilteredFoodRecord(Model.PREDICATE_SHOW_ALL_FOODS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
