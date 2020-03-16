package f11_1.calgo.logic.commands;

import static f11_1.calgo.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import f11_1.calgo.logic.parser.CliSyntax;
import f11_1.calgo.model.Model;
import f11_1.calgo.model.day.Day;
import f11_1.calgo.model.food.Food;

public class NomCommand extends Command {

    public static final String COMMAND_WORD = "nom";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a food to the food record. "
            + "Parameters: "
            + CliSyntax.PREFIX_NAME + "NAME "
            + CliSyntax.PREFIX_DATE + "DATE"
            + CliSyntax.PREFIX_PORTION + "PORTION\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "Kiwi "
            + CliSyntax.PREFIX_DATE + "2020-14-03 "
            + CliSyntax.PREFIX_PORTION + "2 ";

    public static final String MESSAGE_SUCCESS = "Successfully consumed %1$s"; // %d portion of %s was consumed on %s

    private final Day dayConsumed;
    private final Food foodConsumed;

    public NomCommand(Day dayConsumed, Food foodConsumed) {
        requireAllNonNull(dayConsumed, foodConsumed);
        this.dayConsumed = dayConsumed;
        this.foodConsumed = foodConsumed;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (!model.hasDay(dayConsumed)) {
            model.addDay(dayConsumed);
        }
        model.addConsumption(dayConsumed);
        model.updateCurrentFilteredDailyList(Model.PREDICATE_SHOW_ALL_CONSUMED_FOODS,
                dayConsumed.getLocalDate()); // updates display
        return new CommandResult(String.format(MESSAGE_SUCCESS, foodConsumed));
                // , dayConsumed.getPortion(foodConsumed), dayConsumed.getLocalDate()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NomCommand // instanceof handles nulls
                && foodConsumed.equals(((NomCommand) other).foodConsumed)
                && dayConsumed.equals(((NomCommand) other).dayConsumed));
    }

}