package life.calgo.logic.commands;

import org.junit.jupiter.api.BeforeEach;

import life.calgo.model.ConsumptionRecord;
import life.calgo.model.Model;
import life.calgo.model.ModelManager;
import life.calgo.model.UserPrefs;
import life.calgo.model.day.DailyGoal;
import life.calgo.testutil.TypicalFoodItems;

/**
 * Contains integration tests (interaction with the Model) and unit tests for StomachCommand.
 */
public class StomachCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalFoodItems.getTypicalFoodRecord(), new ConsumptionRecord(),
                new UserPrefs(), new DailyGoal());
        expectedModel = new ModelManager(model.getFoodRecord(), model.getConsumptionRecord(),
                new UserPrefs(), new DailyGoal());
    }

}
