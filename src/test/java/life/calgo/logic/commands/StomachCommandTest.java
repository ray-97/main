package life.calgo.logic.commands;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import life.calgo.model.ConsumptionRecord;
import life.calgo.model.Model;
import life.calgo.model.ModelManager;
import life.calgo.model.UserPrefs;
import life.calgo.model.day.DailyGoal;
import life.calgo.testutil.Assert;
import life.calgo.testutil.TypicalFoodItems;

public class StomachCommandTest {

    private Model model = new ModelManager(
            TypicalFoodItems.getTypicalFoodRecord(), new ConsumptionRecord(),
            new UserPrefs(), new DailyGoal());
    private Model expectedModel = new ModelManager(
            TypicalFoodItems.getTypicalFoodRecord(), new ConsumptionRecord(),
            new UserPrefs(), new DailyGoal());

    @Test
    public void constructor_nullDate_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new StomachCommand(null));
    }


    @Test
    public void execute_nullModel_throwsNullPointerException() {
        LocalDate date = LocalDate.now();
        Assert.assertThrows(NullPointerException.class, () -> new StomachCommand(date).execute(null));
    }

}
