package life.calgo.model;

import static life.calgo.model.Model.PREDICATE_SHOW_ALL_FOODS;
import static life.calgo.testutil.Assert.assertThrows;
import static life.calgo.testutil.TypicalFoodItems.APPLE;
import static life.calgo.testutil.TypicalFoodItems.BANANA_MILKSHAKE;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import life.calgo.commons.core.GuiSettings;
import life.calgo.model.day.DailyGoal;
import life.calgo.model.food.Name;
import life.calgo.model.food.predicates.NameContainsKeywordsPredicate;
import life.calgo.testutil.FoodRecordBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new FoodRecord(), new FoodRecord(modelManager.getFoodRecord()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setFoodRecordFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setFoodRecordFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setFoodRecordFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setFoodRecordFilePath(null));
    }

    @Test
    public void setFoodRecordFilePath_validPath_setsFoodRecordFilePath() {
        Path path = Paths.get("food/record/file/path");
        modelManager.setFoodRecordFilePath(path);
        assertEquals(path, modelManager.getFoodRecordFilePath());
    }

    @Test
    public void hasFood_nullFood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasFood(null));
    }

    @Test
    public void hasFood_foodNotInFoodRecord_returnsFalse() {
        assertFalse(modelManager.hasFood(APPLE));
    }

    @Test
    public void hasFood_personInFoodRecord_returnsTrue() {
        modelManager.addFood(APPLE);
        assertTrue(modelManager.hasFood(APPLE));
    }

    @Test
    public void getFilteredFoodList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredFoodRecord().remove(0));
    }

    @Test
    public void equals() {
        FoodRecord foodRecord = new FoodRecordBuilder().withFood(APPLE).withFood(BANANA_MILKSHAKE).build();
        FoodRecord differentFoodRecord = new FoodRecord();
        ConsumptionRecord consumptionRecord = new ConsumptionRecord();
        UserPrefs userPrefs = new UserPrefs();
        DailyGoal dailyGoal = new DailyGoal();

        // same values -> returns true
        modelManager = new ModelManager(foodRecord, consumptionRecord, userPrefs, dailyGoal);
        ModelManager modelManagerCopy = new ModelManager(foodRecord, consumptionRecord, userPrefs, dailyGoal);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentFoodRecord, consumptionRecord,
                userPrefs, dailyGoal)));

        // different filteredList -> returns false
        Name keywords = APPLE.getName();
        modelManager.updateFilteredFoodRecord(new NameContainsKeywordsPredicate(keywords));
        assertFalse(modelManager.equals(new ModelManager(foodRecord, consumptionRecord, userPrefs, dailyGoal)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredFoodRecord(PREDICATE_SHOW_ALL_FOODS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setFoodRecordFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(foodRecord, consumptionRecord,
                differentUserPrefs, dailyGoal)));
    }
}
