package f11_1.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import f11_1.calgo.logic.parser.CliSyntax;
import f11_1.calgo.model.Model;
import f11_1.calgo.logic.commands.exceptions.CommandException;
import f11_1.calgo.model.food.Food;

/**
 * Adds a food to the food record.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a food to the food record. "
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

    public static final String MESSAGE_SUCCESS = "New food added: %1$s";
    public static final String MESSAGE_DUPLICATE_FOOD = "This food already exists in the food record";

    private final Food toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Food food) {
        requireNonNull(food);
        toAdd = food;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasFood(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FOOD);
        }

        model.addFood(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
