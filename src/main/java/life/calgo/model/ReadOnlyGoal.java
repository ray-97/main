package life.calgo.model;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a daily caloric goal.
 */
public interface ReadOnlyGoal {

    /**
     * Returns an unmodifiable view of the daily caloric goal.
     */
    ObservableList<Integer> getGoal();
}
