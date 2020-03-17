package f11_1.calgo.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static f11_1.calgo.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import f11_1.calgo.model.day.Day;
import f11_1.calgo.model.food.Name;
import f11_1.calgo.testutil.Assert;
import f11_1.calgo.testutil.FoodBuilder;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import f11_1.calgo.commons.core.GuiSettings;
import f11_1.calgo.logic.commands.exceptions.CommandException;
import f11_1.calgo.model.FoodRecord;
import f11_1.calgo.model.Model;
import f11_1.calgo.model.ReadOnlyFoodRecord;
import f11_1.calgo.model.ReadOnlyUserPrefs;
import f11_1.calgo.model.food.Food;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Food validFood = new FoodBuilder().build();

        CommandResult commandResult = new AddCommand(validFood).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validFood), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validFood), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Food validFood = new FoodBuilder().build();
        AddCommand addCommand = new AddCommand(validFood);
        ModelStub modelStub = new ModelStubWithPerson(validFood);

        Assert.assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_FOOD, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Food alice = new FoodBuilder().withName("Alice").build();
        Food bob = new FoodBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getFoodRecordFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFoodRecordFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addFood(Food food) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFoodRecord(ReadOnlyFoodRecord newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyFoodRecord getFoodRecord() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasFood(Food food) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteFood(Food target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFood(Food target, Food editedFood) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Food> getFilteredFoodRecord() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredFoodRecord(Predicate<Food> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Food> getFoodByName(Name parseName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDay(Day dayConsumed) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDay(Day dayConsumed) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addConsumption(Day dayConsumed) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Day getDayByDate(LocalDate localDate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Food food;

        ModelStubWithPerson(Food food) {
            requireNonNull(food);
            this.food = food;
        }

        @Override
        public boolean hasFood(Food food) {
            requireNonNull(food);
            return this.food.isSameFood(food);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Food> personsAdded = new ArrayList<>();

        @Override
        public boolean hasFood(Food food) {
            requireNonNull(food);
            return personsAdded.stream().anyMatch(food::isSameFood);
        }

        @Override
        public void addFood(Food food) {
            requireNonNull(food);
            personsAdded.add(food);
        }

        @Override
        public ReadOnlyFoodRecord getFoodRecord() {
            return new FoodRecord();
        }
    }

}
