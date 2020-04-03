package life.calgo.model.day;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import life.calgo.testutil.TypicalDailyFoodLog;
import life.calgo.testutil.TypicalFoodItems;

public class DailyFoodLogTest {

    @Test
    public void copy_typicalDailyFoodLog_returnsTrue() {
        assertTrue(TypicalDailyFoodLog.DAILY_FOOD_LOG_TODAY
                .equals(TypicalDailyFoodLog.DAILY_FOOD_LOG_TODAY.copy()));
    }

    @Test
    public void getRating_ratingOf_returnsTrue() {
        assertEquals(TypicalDailyFoodLog.DAILY_FOOD_LOG_TODAY.getRating(TypicalFoodItems.BANANA),
                (double) (6 + 9) / 2);
    }

    @Test
    public void getFoodByIndex_typicalDailyFoodLog_returnsTrue() {
        assertEquals(TypicalFoodItems.ALMOND, TypicalDailyFoodLog.DAILY_FOOD_LOG_TODAY.getFoodByIndex(0).get());
    }

}
