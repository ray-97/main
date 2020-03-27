package life.calgo.logic.commands;

import static life.calgo.logic.commands.CommandTestUtil.assertCommandFailure;
import static life.calgo.logic.commands.CommandTestUtil.assertCommandSuccess;

import life.calgo.model.day.DailyGoal;
import life.calgo.testutil.FoodBuilder;
import life.calgo.testutil.TypicalFoodItems;
import life.calgo.testutil.TypicalPersons;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import life.calgo.model.Model;
import life.calgo.model.ModelManager;
import life.calgo.model.UserPrefs;
import life.calgo.model.food.Food;

/**
 * Contains integration tests (interaction with the Model) for {@code UpdateCommand}.
 */
public class UpdateCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalFoodItems.getTypicalFoodRecord(), new UserPrefs(), new DailyGoal());
    }

    @Test
    public void execute_newFood_success() {
        Food validFood = new FoodBuilder().build();

        Model expectedModel = new ModelManager(model.getFoodRecord(), new UserPrefs(), new DailyGoal());
        expectedModel.addFood(validFood);

        assertCommandSuccess(new UpdateCommand(validFood), model,
                String.format(UpdateCommand.MESSAGE_SUCCESS, validFood), expectedModel);
    }

    @Test
    public void execute_existingFood_success() {
        Food validFood = new FoodBuilder().build();
        Food existingFood = model.getFoodRecord().getFoodList().get(0);
        Model expectedModel = new ModelManager(model.getFoodRecord(), new UserPrefs(), new DailyGoal());
        expectedModel.setFood(existingFood, validFood);
        assertCommandSuccess(new UpdateCommand(validFood), model,
                String.format(UpdateCommand.MESSAGE_SUCCESS, validFood), expectedModel);
    }

}
