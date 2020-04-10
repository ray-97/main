package life.calgo.model.day;

import life.calgo.model.ReadOnlyGoal;

/**
 * Represents the daily number of calories the user is aiming to consume.
 */
public class DailyGoal implements ReadOnlyGoal {
    public static final int MINIMUM_HEALTHY_CALORIES = 1200;

    public static final int MINIMUM_ACCEPTABLE_CALORIES = 1;

    public static final int MAXIMUM_ACCEPTABLE_CALORIES = 99999;

    public static final int DUMMY_VALUE = 0;

    public static final String MESSAGE_CONSTRAINTS = "Daily caloric goals should be positive integers. "
            + "The general rule of thumb is 2000 calories for females and 2500 calories for males.";

    private int targetDailyCalories;

    public DailyGoal(int numCalories) {
        this.targetDailyCalories = numCalories;
    }

    public DailyGoal(ReadOnlyGoal readOnlyGoal) {
        this.targetDailyCalories = readOnlyGoal.getGoal();
    }

    public DailyGoal() {
        this.targetDailyCalories = DUMMY_VALUE;
    }

    /**
     * Changes <code>targetDailyCalories</code>  to <code>newTarget</code>
     *
     * @param newTarget the new desired number of calories to consume each day.
     * @return update on successful change of daily goal.
     */
    public DailyGoal updateDailyGoal(int newTarget) {
        return new DailyGoal(newTarget);
    }

    public static boolean isValidGoal(int targetDailyCalories) {
        return targetDailyCalories >= MINIMUM_ACCEPTABLE_CALORIES && targetDailyCalories <= MAXIMUM_ACCEPTABLE_CALORIES;
    }

    @Override
    public String toString() {
        return "Calorie goal: " + this.targetDailyCalories;
    }

    public Integer getGoal() {
        return targetDailyCalories;
    }
}

