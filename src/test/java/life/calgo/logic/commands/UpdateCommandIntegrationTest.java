package life.calgo.logic.commands;

import static life.calgo.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import life.calgo.model.ConsumptionRecord;
import life.calgo.model.Model;
import life.calgo.model.ModelManager;
import life.calgo.model.UserPrefs;
import life.calgo.model.day.DailyGoal;
import life.calgo.model.food.Food;
import life.calgo.testutil.FoodBuilder;
import life.calgo.testutil.TypicalFoodItems;

/**
 * Contains integration tests (interaction with the Model) for {@code UpdateCommand}.
 */
public class UpdateCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalFoodItems.getTypicalFoodRecord(), new ConsumptionRecord(),
                new UserPrefs(), new DailyGoal());
    }

    @Test
    public void execute_newFood_success() {
        Food validFood = new FoodBuilder().build();

        Model expectedModel = new ModelManager(model.getFoodRecord(), new ConsumptionRecord(),
                new UserPrefs(), new DailyGoal());
        expectedModel.addFood(validFood);

        assertCommandSuccess(new UpdateCommand(validFood), model,
                String.format(UpdateCommand.MESSAGE_SUCCESS, validFood), expectedModel);
    }

    @Test
    public void execute_existingFood_success() {
        Food validFood = new FoodBuilder().withName("Almond").build();
        Food existingFood = model.getFoodRecord().getFoodList().get(0);

        Model expectedModel = new ModelManager(model.getFoodRecord(), model.getConsumptionRecord(),
                new UserPrefs(), new DailyGoal());
        expectedModel.setFood(existingFood, validFood);

        assertCommandSuccess(new UpdateCommand(validFood), model,
                String.format(UpdateCommand.MESSAGE_UPDATE_EXISTING_FOOD_SUCCESS, validFood), expectedModel);
    }

}
