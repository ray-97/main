package f11_1.calgo.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static f11_1.calgo.logic.commands.CommandTestUtil.assertCommandFailure;
import static f11_1.calgo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static f11_1.calgo.logic.commands.CommandTestUtil.showPersonAtIndex;

import f11_1.calgo.testutil.TypicalIndexes;
import f11_1.calgo.testutil.TypicalPersons;
import org.junit.jupiter.api.Test;

import f11_1.calgo.commons.core.Messages;
import f11_1.calgo.commons.core.index.Index;
import f11_1.calgo.model.Model;
import f11_1.calgo.model.ModelManager;
import f11_1.calgo.model.UserPrefs;
import f11_1.calgo.model.food.Food;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Food foodToDelete = model.getFilteredFoodRecord().get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_FOOD_SUCCESS, foodToDelete);

        ModelManager expectedModel = new ModelManager(model.getFoodRecord(), new UserPrefs());
        expectedModel.deleteFood(foodToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFoodRecord().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);

        Food foodToDelete = model.getFilteredFoodRecord().get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_FOOD_SUCCESS, foodToDelete);

        Model expectedModel = new ModelManager(model.getFoodRecord(), new UserPrefs());
        expectedModel.deleteFood(foodToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFoodRecord().getFoodList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(TypicalIndexes.INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(TypicalIndexes.INDEX_FIRST_PERSON);
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
