package life.calgo.model.day;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import life.calgo.model.ReadOnlyGoal;

/**
 * Represents the daily number of calories the user is aiming to consume.
 */
public class DailyGoal implements ReadOnlyGoal {
    public static final int DUMMY_VALUE = 0;

    public static final String MESSAGE_CONSTRAINTS = "Daily caloric goals should be positive integers. "
            + "The general rule of thumb is 2000 calories for females and 2500 calories for males.";

    private int targetDailyCalories;

    public DailyGoal(int numCalories) {
        this.targetDailyCalories = numCalories;
    }

    public DailyGoal(ReadOnlyGoal readOnlyGoal) {
        this.targetDailyCalories = convertToInteger(readOnlyGoal);
    }

    public DailyGoal() {
        this.targetDailyCalories = DUMMY_VALUE;
    }

    /**
     * Converts a readOnlyGoal to its integer value.
     * @param readOnlyGoal a readOnlyGoal meant for GUI.
     * @return an integer representing number of calories to consume in a day, set by user.
     */
    public static int convertToInteger(ReadOnlyGoal readOnlyGoal) {
        ObservableList<Integer> goalList = readOnlyGoal.getGoal();
        assert goalList.size() == 1 : "There is a wrong number of goals in the storage.";
        return goalList.get(0);
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

    public ObservableList<Integer> getGoal() {
        ObservableList<Integer> goalList = FXCollections.observableArrayList();
        goalList.add(targetDailyCalories);
        return FXCollections.unmodifiableObservableList(goalList);
    }
}

