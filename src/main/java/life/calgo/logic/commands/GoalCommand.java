package life.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import life.calgo.model.Model;
import life.calgo.model.day.DailyGoal;

/**
 * Updates daily caloric goal of user.
 */
public class GoalCommand extends Command {

    // Command and related Messages

    public static final String COMMAND_WORD = "goal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates your goal of how many calories to consume "
            + "daily.\n Calgo will subsequently help you to keep track of your consumption to achieve this goal.\n"
            + "The following will help you to understand how to use the command:\n"
            + "Parameters: " + COMMAND_WORD + " GOAL\n"
            + "Example: To set a goal to consume 2800 calories each day, enter this: " + COMMAND_WORD + " 2800";

    public static final String MESSAGE_SUCCESS = "Successfully updated your daily caloric goal to %1$d.";

    public static final String MESSAGE_FAILURE = "Please key in a whole number that is at least %d calorie and"
        + " at most %d calories.";

    public static final String MESSAGE_WARNING = "That is a really low goal to set. Warning: You may suffer from"
            + " malnutrition." + "\n"
            + "We'll accept this now because Calgo will eventually help you to reach a daily calorie count of \n"
            + "%d, which is the minimum calories you should eat to stay moderately healthy.";

    // the number of calories the user enters
    private final int numCaloriesDaily;

    public GoalCommand(int numberCaloriesDaily) {
        this.numCaloriesDaily = numberCaloriesDaily;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateDailyGoal(this.numCaloriesDaily);

        // if user enters an unhealthy calorie goal
        if (this.numCaloriesDaily < DailyGoal.MINIMUM_HEALTHY_CALORIES) {
            return new CommandResult(String.format(MESSAGE_WARNING, DailyGoal.MINIMUM_HEALTHY_CALORIES));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, numCaloriesDaily));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GoalCommand // instanceof handles nulls
                && numCaloriesDaily == ((GoalCommand) other).numCaloriesDaily); // attribute check
    }

}
