package life.calgo.model.day;

/**
 * Represents the daily number of calories the user is aiming to consume.
 */
public class DailyGoal {
    private int targetDailyCalories;

    public static final String MESSAGE_CONSTRAINTS = "Daily caloric goals should be positive integers. "
            + "The general rule of thumb is 2000 calories for females and 2500 calories for males.";

    public DailyGoal(int numCalories) {
        this.targetDailyCalories = numCalories;
    }

    /**
     * Changes <code>targetDailyCalories</code>  to <code>newTarget</code>
     * @param newTarget the new desired number of calories to consume each day
     * @return update on successful change of daily goal
     */
    public DailyGoal updateDailyGoal(int newTarget) {
        return new DailyGoal(newTarget);
    }

    public static boolean isValidGoal(int targetDailyCalories) {
        return targetDailyCalories > 0;
    }

    public int getTargetDailyCalories() {
        return this.targetDailyCalories;
    }
    @Override
    public String toString() {
        return "Target number of calories to consume: " + String.valueOf(this.targetDailyCalories);
    }
}

