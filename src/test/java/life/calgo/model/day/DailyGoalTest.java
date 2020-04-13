package life.calgo.model.day;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import life.calgo.testutil.Assert;

public class DailyGoalTest {

    @Test
    public void constructor_nullDailyGoal_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DailyGoal((Integer) null));
    }

    @Test
    public void constructor_invalidDailyGoal_throwsIllegalArgumentException() {
        int invalidGoal = -1;
        Assert.assertThrows(IllegalArgumentException.class, () -> new DailyGoal(invalidGoal));
    }

    // Acceptance tests for DailyGoal
    @Test
    public void isValidGoal() {
        // null daily goal
        Assert.assertThrows(NullPointerException.class, () -> DailyGoal.isValidGoal(null));

        // perform Boundary Value Analysis

        // invalid daily goal
        assertFalse(DailyGoal.isValidGoal(DailyGoal.DUMMY_VALUE)); // Default value that is not valid
        assertFalse(DailyGoal.isValidGoal(-1)); // Negative value
        assertFalse(DailyGoal.isValidGoal(DailyGoal.MAXIMUM_ACCEPTABLE_CALORIES + 1));

        // valid daily goal
        assertTrue(DailyGoal.isValidGoal(DailyGoal.MINIMUM_ACCEPTABLE_CALORIES));
        assertTrue(DailyGoal.isValidGoal(DailyGoal.MINIMUM_HEALTHY_CALORIES + 1));
        assertTrue(DailyGoal.isValidGoal(DailyGoal.MAXIMUM_ACCEPTABLE_CALORIES));
        assertTrue(DailyGoal.isValidGoal(DailyGoal.MAXIMUM_ACCEPTABLE_CALORIES - 1));
        assertTrue(DailyGoal.isValidGoal(DailyGoal.MINIMUM_HEALTHY_CALORIES));
        assertTrue(DailyGoal.isValidGoal(DailyGoal.MINIMUM_HEALTHY_CALORIES + 1));
        assertTrue(DailyGoal.isValidGoal(DailyGoal.MINIMUM_HEALTHY_CALORIES - 1));
    }


}
