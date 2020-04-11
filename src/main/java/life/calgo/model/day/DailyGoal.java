package life.calgo.model.day;

import life.calgo.model.ReadOnlyGoal;

/**
 * Represents the daily number of calories the user aims to consume in a day.
 */
public class DailyGoal implements ReadOnlyGoal {

    // Values used for GoalCommandParser when parsing user inputted goals.
    public static final int MINIMUM_HEALTHY_CALORIES = 1200;

    public static final int MINIMUM_ACCEPTABLE_CALORIES = 1;

    public static final int MAXIMUM_ACCEPTABLE_CALORIES = 99999;

    // Default value, when user does not input a goal.
    public static final int DUMMY_VALUE = 0;

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

    // Getter method

    public Integer getGoal() {
        return targetDailyCalories;
    }

    // Setter method

    /**
     * Updates value of <code>targetDailyCalories</code> to <code>newTarget</code>.
     *
     * @param newTarget The new desired number of calories to consume each day.
     * @return Updated DailyGoal object.
     */
    public DailyGoal updateDailyGoal(int newTarget) {
        return new DailyGoal(newTarget);
    }

    // Utility methods

    public static boolean isValidGoal(int targetDailyCalories) {
        return targetDailyCalories >= MINIMUM_ACCEPTABLE_CALORIES && targetDailyCalories <= MAXIMUM_ACCEPTABLE_CALORIES;
    }

    @Override
    public String toString() {
        return "Calorie goal: " + this.targetDailyCalories;
    }

}

