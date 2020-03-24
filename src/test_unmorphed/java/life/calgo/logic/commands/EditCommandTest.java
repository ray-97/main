package f11_1.calgo.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static f11_1.calgo.logic.commands.CommandTestUtil.DESC_AMY;
import static f11_1.calgo.logic.commands.CommandTestUtil.DESC_BOB;
import static f11_1.calgo.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static f11_1.calgo.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static f11_1.calgo.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static f11_1.calgo.logic.commands.CommandTestUtil.assertCommandFailure;
import static f11_1.calgo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static f11_1.calgo.logic.commands.CommandTestUtil.showPersonAtIndex;

import f11_1.calgo.testutil.EditPersonDescriptorBuilder;
import f11_1.calgo.testutil.FoodBuilder;
import f11_1.calgo.testutil.TypicalIndexes;
import f11_1.calgo.testutil.TypicalPersons;
import org.junit.jupiter.api.Test;

import f11_1.calgo.commons.core.Messages;
import f11_1.calgo.commons.core.index.Index;
import f11_1.calgo.model.FoodRecord;
import f11_1.calgo.model.Model;
import f11_1.calgo.model.ModelManager;
import f11_1.calgo.model.UserPrefs;
import f11_1.calgo.model.food.Food;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Food editedFood = new FoodBuilder().build();
        EditFoodDescriptor descriptor = new EditPersonDescriptorBuilder(editedFood).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FOOD_SUCCESS, editedFood);

        Model expectedModel = new ModelManager(new FoodRecord(model.getFoodRecord()), new UserPrefs());
        expectedModel.setFood(model.getFilteredFoodRecord().get(0), editedFood);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredFoodRecord().size());
        Food lastFood = model.getFilteredFoodRecord().get(indexLastPerson.getZeroBased());

        FoodBuilder personInList = new FoodBuilder(lastFood);
        Food editedFood = personInList.withName(VALID_NAME_BOB).withCalorie(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditFoodDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FOOD_SUCCESS, editedFood);

        Model expectedModel = new ModelManager(new FoodRecord(model.getFoodRecord()), new UserPrefs());
        expectedModel.setFood(lastFood, editedFood);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON, new EditFoodDescriptor());
        Food editedFood = model.getFilteredFoodRecord().get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FOOD_SUCCESS, editedFood);

        Model expectedModel = new ModelManager(new FoodRecord(model.getFoodRecord()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);

        Food foodInFilteredList = model.getFilteredFoodRecord().get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        Food editedFood = new FoodBuilder(foodInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FOOD_SUCCESS, editedFood);

        Model expectedModel = new ModelManager(new FoodRecord(model.getFoodRecord()), new UserPrefs());
        expectedModel.setFood(model.getFilteredFoodRecord().get(0), editedFood);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Food firstFood = model.getFilteredFoodRecord().get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        EditFoodDescriptor descriptor = new EditPersonDescriptorBuilder(firstFood).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_FOOD);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Food foodInList = model.getFoodRecord().getFoodList().get(TypicalIndexes.INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(foodInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_FOOD);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFoodRecord().size() + 1);
        EditFoodDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFoodRecord().getFoodList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditFoodDescriptor copyDescriptor = new EditFoodDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(TypicalIndexes.INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON, DESC_BOB)));
    }

}
