package life.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import life.calgo.model.Model;

/**
 * Updates daily caloric goal of user.
 */
public class GoalCommand extends Command {

    public static final String COMMAND_WORD = "goal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates your goal of how many calories to consume"
            + "daily.\n"
            + "Parameters: goal GOAL\n"
            + "Example: " + COMMAND_WORD + " 2800";

    public static final String MESSAGE_SUCCESS = "Successfully updated your daily caloric goal to %1$d.";

    public static final String MESSAGE_FAILURE_TYPE = "Please key in a non-zero whole number for your "
            + "daily caloric goal.";

    public static final String MESSAGE_FAILURE_NEGATIVE = "Please key in a positive whole number for your "
            + "daily caloric goal.";

    public static final String MESSAGE_WARNING = "That is a really low goal to set. Warning: You may suffer from"
            + " malnutrition. Don't worry! Calgo is here to help you build healthier eating habits.";

    private final int numCaloriesDaily;

    public GoalCommand(int numberCaloriesDaily) {
        this.numCaloriesDaily = numberCaloriesDaily;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateDailyGoal(this.numCaloriesDaily);
        if (this.numCaloriesDaily <= 1000) {
            return new CommandResult(MESSAGE_WARNING);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, numCaloriesDaily));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GoalCommand // instanceof handles nulls
                && numCaloriesDaily == ((GoalCommand) other).numCaloriesDaily); // state check
    }

}
