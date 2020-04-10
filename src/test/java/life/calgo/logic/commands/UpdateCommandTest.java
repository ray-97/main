package life.calgo.logic.commands;

import static java.util.Objects.requireNonNull;
import static life.calgo.logic.commands.CommandTestUtil.assertCommandFailure;
import static life.calgo.testutil.TypicalFoodItems.getTypicalFoodRecord;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import life.calgo.commons.core.GuiSettings;
import life.calgo.model.ConsumptionRecord;
import life.calgo.model.FoodRecord;
import life.calgo.model.Model;
import life.calgo.model.ModelManager;
import life.calgo.model.ReadOnlyConsumptionRecord;
import life.calgo.model.ReadOnlyFoodRecord;
import life.calgo.model.ReadOnlyUserPrefs;
import life.calgo.model.UserPrefs;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.day.DailyGoal;
import life.calgo.model.food.DisplayFood;
import life.calgo.model.food.Food;
import life.calgo.model.food.Name;
import life.calgo.testutil.Assert;
import life.calgo.testutil.FoodBuilder;

public class UpdateCommandTest {

    private Model model = new ModelManager(getTypicalFoodRecord(), new ConsumptionRecord(),
            new UserPrefs(), new DailyGoal());

    @Test
    public void constructor_nullFood_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new UpdateCommand(null));
    }

    @Test
    public void execute_foodAcceptedByModel_updateSuccessful() throws Exception {
        Food validFood = new FoodBuilder().build();

        CommandResult commandResult = new UpdateCommand(validFood).execute(model);

        assertEquals(String.format(UpdateCommand.MESSAGE_SUCCESS, validFood), commandResult.getFeedbackToUser());
        assertTrue(model.hasFood(validFood));
    }

    @Test
    public void execute_existingFood_updateSuccessful() throws Exception {
        Food validFood = new FoodBuilder().build();
        Food editedFood = new FoodBuilder().withProtein("222222").build();
        UpdateCommand updateCommand = new UpdateCommand(editedFood);

        model.addFood(validFood);

        CommandResult commandResult = updateCommand.execute(model);

        assertEquals(String.format(UpdateCommand.MESSAGE_UPDATE_EXISTING_FOOD_SUCCESS, editedFood),
                commandResult.getFeedbackToUser());
        assertTrue(model.hasFood(editedFood));
    }

    @Test
    public void execute_existingFoodSameValues_throwsCommandException() throws Exception {
        Food validFood = new FoodBuilder().build();
        Food existingFoodSameValues = new FoodBuilder().build();
        UpdateCommand updateCommand = new UpdateCommand(existingFoodSameValues);

        model.addFood(validFood);

        assertCommandFailure(updateCommand, model,
                String.format(UpdateCommand.MESSAGE_UPDATE_EXISTING_FOOD_SAME_VALUES_FAILED,
                existingFoodSameValues.getName().fullName));
    }


    @Test
    public void equals() {
        Food apple = new FoodBuilder().withName("Apple").build();
        Food banana = new FoodBuilder().withName("Banana").build();
        UpdateCommand addAppleCommand = new UpdateCommand(apple);
        UpdateCommand addBananaCommand = new UpdateCommand(banana);

        // same object -> returns true
        assertTrue(addAppleCommand.equals(addAppleCommand));

        // same values -> returns true
        UpdateCommand addAppleCommandCopy = new UpdateCommand(apple);
        assertTrue(addAppleCommand.equals(addAppleCommandCopy));

        // different types -> returns false
        assertFalse(addAppleCommand.equals(1));

        // null -> returns false
        assertFalse(addAppleCommand.equals(null));

        // different person -> returns false
        assertFalse(addAppleCommand.equals(addBananaCommand));
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
        public void setFoodRecordFilePath(Path foodRecordFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyConsumptionRecord getConsumptionRecord() {
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
        public Food getExistingFood(Food toAdd) {
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
        public ObservableList<DisplayFood> getCurrentFilteredDailyList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateCurrentFilteredDailyList(Predicate<DisplayFood> predicate, LocalDate date) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateConsumedLists(Food food) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Food> getFoodByName(Name parseName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasLogWithSameDate(DailyFoodLog foodLog) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasLogWithSameDate(LocalDate date) {
            return false;
        }

        @Override
        public void addLog(DailyFoodLog foodLog) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateLog(DailyFoodLog logAfterConsumption) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public DailyFoodLog getLogByDate(LocalDate localDate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateDailyGoal(int targetDailyCalories) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isGoalMade() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public DailyGoal getDailyGoal() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public double getRemainingCalories() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public LocalDate getDate() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateDate(LocalDate date) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ArrayList<DailyFoodLog> getPastWeekLogs() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithFood extends ModelStub {
        private final Food food;

        ModelStubWithFood(Food food) {
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
    private class ModelStubAcceptingFoodAdded extends ModelStub {
        final ArrayList<Food> foodItemsAdded = new ArrayList<>();

        @Override
        public boolean hasFood(Food food) {
            requireNonNull(food);
            return foodItemsAdded.stream().anyMatch(food::isSameFood);
        }

        @Override
        public void addFood(Food food) {
            requireNonNull(food);
            foodItemsAdded.add(food);
        }

        @Override
        public ReadOnlyFoodRecord getFoodRecord() {
            return new FoodRecord();
        }
    }

}
