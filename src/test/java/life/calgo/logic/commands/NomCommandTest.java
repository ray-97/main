package life.calgo.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import life.calgo.model.ConsumptionRecord;
import life.calgo.model.Model;
import life.calgo.model.ModelManager;
import life.calgo.model.UserPrefs;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.day.DailyGoal;
import life.calgo.model.food.Food;
import life.calgo.testutil.Assert;
import life.calgo.testutil.FoodBuilder;
import life.calgo.testutil.TypicalDailyFoodLog;
import life.calgo.testutil.TypicalFoodItems;

public class NomCommandTest {

    private Model model = new ModelManager(
            TypicalFoodItems.getTypicalFoodRecord(), new ConsumptionRecord(),
            new UserPrefs(), new DailyGoal());
    private Model expectedModel = new ModelManager(
            TypicalFoodItems.getTypicalFoodRecord(), new ConsumptionRecord(),
            new UserPrefs(), new DailyGoal());

    @Test
    public void constructor_nullFood_throwsNullPointerException() {
        DailyFoodLog validDailyFoodLog = TypicalDailyFoodLog.getAppleOnlyLog();
        Assert.assertThrows(NullPointerException.class, () -> new NomCommand(validDailyFoodLog, null));
    }

    @Test
    public void constructor_nullDailyFoodLog_throwsNullPointerException() {
        Food validFood = new FoodBuilder().build();
        Assert.assertThrows(NullPointerException.class, () -> new NomCommand(null, validFood));
    }

    @Test
    public void execute_existingFoodAcceptedByDailyFoodLog_nomSuccessful() throws Exception {
        Food validApple = new FoodBuilder(TypicalFoodItems.APPLE).build();
        DailyFoodLog validDailyFoodLog = TypicalDailyFoodLog.getAppleOnlyLog();

        CommandResult commandResult = new NomCommand(validDailyFoodLog, validApple).execute(model);

        assertEquals(String.format(NomCommand.MESSAGE_SUCCESS, validApple), commandResult.getFeedbackToUser());
        assertTrue(model.getCurrentFilteredDailyList().contains(validApple));
    }

    @Test
    public void execute_validFood_nomSuccessful() throws Exception {
        Food validApple = new FoodBuilder(TypicalFoodItems.APPLE).build();
        DailyFoodLog validDailyFoodLog = TypicalDailyFoodLog.getAppleOnlyLog();

        CommandResult commandResult = new NomCommand(validDailyFoodLog, validApple).execute(model);

        assertEquals(String.format(NomCommand.MESSAGE_SUCCESS, validApple), commandResult.getFeedbackToUser());
        assertTrue(model.getCurrentFilteredDailyList().contains(validApple));
    }

    @Test
    public void equals() {
        Food apple = new FoodBuilder().withName("Apple").build();
        Food banana = new FoodBuilder().withName("Banana").build();
        DailyFoodLog validDailyFoodLog = TypicalDailyFoodLog.DAILY_FOOD_LOG_TODAY;
        NomCommand nomAppleCommand = new NomCommand(validDailyFoodLog, apple);
        NomCommand nomBananaCommand = new NomCommand(validDailyFoodLog, banana);

        // same object -> returns true
        assertTrue(nomAppleCommand.equals(nomAppleCommand));

        // same values -> returns true
        NomCommand nomAppleCommandCopy = new NomCommand(validDailyFoodLog, apple);
        assertTrue(nomAppleCommand.equals(nomAppleCommandCopy));

        // different types -> returns false
        assertFalse(nomAppleCommand.equals(1));

        // null -> returns false
        assertFalse(nomAppleCommand.equals(null));

        // different command object -> returns false
        assertFalse(nomAppleCommand.equals(nomBananaCommand));
    }

}
