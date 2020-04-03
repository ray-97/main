package life.calgo.logic.commands;

import static life.calgo.logic.commands.CommandTestUtil.assertCommandFailure;
import static life.calgo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static life.calgo.logic.commands.CommandTestUtil.showFoodAtIndex;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import life.calgo.commons.core.Messages;
import life.calgo.model.ConsumptionRecord;
import life.calgo.model.Model;
import life.calgo.model.ModelManager;
import life.calgo.model.UserPrefs;
import life.calgo.model.day.DailyGoal;
import life.calgo.model.food.Food;
import life.calgo.testutil.FoodBuilder;
import life.calgo.testutil.TypicalFoodItems;
import life.calgo.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(TypicalFoodItems.getTypicalFoodRecord(), new ConsumptionRecord(),
            new UserPrefs(), new DailyGoal());

    @Test
    public void execute_existingFoodDelete_success() {
        Food foodToDelete = model.getFilteredFoodRecord().get(TypicalIndexes.INDEX_FIRST_FOOD.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(foodToDelete);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_FOOD_SUCCESS, foodToDelete);

        ModelManager expectedModel = new ModelManager(model.getFoodRecord(), model.getConsumptionRecord(),
                new UserPrefs(), new DailyGoal());
        expectedModel.deleteFood(foodToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistingFoodDelete_throwsCommandException() {
        Food food = new FoodBuilder().withName("Definitely does not exists").build();
        DeleteCommand deleteCommand = new DeleteCommand(food);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_FOOD_DOES_NOT_EXISTS_IN_RECORD);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFoodAtIndex(model, TypicalIndexes.INDEX_FIRST_FOOD);

        Food foodToDelete = model.getFilteredFoodRecord().get(TypicalIndexes.INDEX_FIRST_FOOD.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(foodToDelete);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_FOOD_SUCCESS, foodToDelete);

        Model expectedModel = new ModelManager(model.getFoodRecord(), new ConsumptionRecord(),
                new UserPrefs(), new DailyGoal());
        expectedModel.deleteFood(foodToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void equals() {
        Food firstFood = model.getFilteredFoodRecord().get(TypicalIndexes.INDEX_FIRST_FOOD.getZeroBased());
        Food secondFood = model.getFilteredFoodRecord().get(TypicalIndexes.INDEX_SECOND_FOOD.getZeroBased());
        DeleteCommand deleteFirstCommand = new DeleteCommand(firstFood);
        DeleteCommand deleteSecondCommand = new DeleteCommand(secondFood);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(firstFood);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredFoodRecord(p -> false);

        assertTrue(model.getFilteredFoodRecord().isEmpty());
    }
}
