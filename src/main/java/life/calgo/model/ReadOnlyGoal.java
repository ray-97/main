package life.calgo.model;

/**
 * Unmodifiable view of a daily caloric goal.
 */
public interface ReadOnlyGoal {

    /**
     * Returns an unmodifiable view of the daily caloric goal.
     */
    Integer getGoal();
}
