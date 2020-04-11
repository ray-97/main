package life.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import life.calgo.logic.commands.exceptions.CommandException;
import life.calgo.logic.parser.CliSyntax;
import life.calgo.model.Model;
import life.calgo.model.food.Food;

/**
 * Updates Food Record with a given food object
 */
public class UpdateCommand extends Command {
    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the food entered into the Food Record."
            + " If the food entered already exists, it will be overwritten by input values.\n"
            + "Parameters: "
            + CliSyntax.PREFIX_NAME + "NAME "
            + CliSyntax.PREFIX_CALORIES + "CALORIES "
            + CliSyntax.PREFIX_PROTEIN + "PROTEIN "
            + CliSyntax.PREFIX_CARBOHYDRATE + "CARBOHYDRATE "
            + CliSyntax.PREFIX_FAT + "FAT "
            + "[" + CliSyntax.PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "Kiwi "
            + CliSyntax.PREFIX_CALORIES + "150 "
            + CliSyntax.PREFIX_PROTEIN + "2 "
            + CliSyntax.PREFIX_CARBOHYDRATE + "25 "
            + CliSyntax.PREFIX_FAT + "3 "
            + CliSyntax.PREFIX_TAG + "Green "
            + CliSyntax.PREFIX_TAG + "Sweet";

    public static final String MESSAGE_SUCCESS = "Updated all foods into Food Record:\n%1$s";
    public static final String MESSAGE_UPDATE_EXISTING_FOOD_SUCCESS =
            "Updated existing food item in Food Record:\n%1$s";
    public static final String MESSAGE_UPDATE_EXISTING_FOOD_SAME_VALUES_FAILED =
        "The nutritional value that you have entered is exactly the same as %1$s in the Food Record!";

    private final Food toAdd;

    /**
     * Creates an UpdateCommand to update the specified {@code Food}
     */
    public UpdateCommand(Food food) {
        requireNonNull(food);
        toAdd = food;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasFood(toAdd)) {
            Food existingFood = model.getExistingFood(toAdd);
            if (existingFood.equals(toAdd)) {
                throw new CommandException(String.format(
                        MESSAGE_UPDATE_EXISTING_FOOD_SAME_VALUES_FAILED, existingFood.getName().fullName));
            }
            model.setFood(existingFood, toAdd);
            model.updateConsumedLists(toAdd);
            model.updateFilteredFoodRecord(Model.PREDICATE_SHOW_ALL_FOODS);
            return new CommandResult(String.format(MESSAGE_UPDATE_EXISTING_FOOD_SUCCESS, toAdd));
        } else {
            model.addFood(toAdd);
            model.updateConsumedLists(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UpdateCommand // instanceof handles nulls
                && toAdd.equals(((UpdateCommand) other).toAdd));
    }

}
