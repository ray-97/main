package life.calgo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import life.calgo.logic.commands.CommandTestUtil;
import life.calgo.model.food.Food;
import life.calgo.model.food.exceptions.DuplicateFoodException;
import life.calgo.testutil.Assert;
import life.calgo.testutil.FoodBuilder;
import life.calgo.testutil.TypicalFoodItems;

public class FoodRecordTest {

    private final FoodRecord foodRecord = new FoodRecord();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), foodRecord.getFoodList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> foodRecord.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyFoodRecord_replacesData() {
        FoodRecord newData = TypicalFoodItems.getTypicalFoodRecord();
        foodRecord.resetData(newData);
        assertEquals(newData, foodRecord);
    }

    @Test
    public void resetData_withDuplicateFoodItems_throwsDuplicateFoodException() {
        // Two persons with the same identity fields
        Food editedApple = new FoodBuilder(TypicalFoodItems.APPLE)
                .withProtein(CommandTestUtil.VALID_PROTEIN_APPLE).withTags(CommandTestUtil.VALID_TAG_HARD)
                .build();
        List<Food> newFoods = Arrays.asList(TypicalFoodItems.APPLE, editedApple);
        FoodRecordStub newData = new FoodRecordStub(newFoods);

        Assert.assertThrows(DuplicateFoodException.class, () -> foodRecord.resetData(newData));
    }

    @Test
    public void hasFood_nullFoodItem_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> foodRecord.hasFood(null));
    }

    @Test
    public void hasFood_foodNoteInFoodRecord_returnsFalse() {
        assertFalse(foodRecord.hasFood(TypicalFoodItems.APPLE));
    }

    @Test
    public void hasFood_foodInFoodRecord_returnsTrue() {
        foodRecord.addFood(TypicalFoodItems.APPLE);
        assertTrue(foodRecord.hasFood(TypicalFoodItems.APPLE));
    }

    @Test
    public void hasFood_foodWithSameIdentityFieldsInFoodRecord_returnsTrue() {
        foodRecord.addFood(TypicalFoodItems.APPLE);
        Food editedApple = new FoodBuilder(TypicalFoodItems.APPLE)
                .withProtein(CommandTestUtil.VALID_PROTEIN_APPLE).withTags(CommandTestUtil.VALID_TAG_HARD)
                .build();
        assertTrue(foodRecord.hasFood(editedApple));
    }

    @Test
    public void getFoodList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> foodRecord.getFoodList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class FoodRecordStub implements ReadOnlyFoodRecord {
        private final ObservableList<Food> foods = FXCollections.observableArrayList();

        FoodRecordStub(Collection<Food> foods) {
            this.foods.setAll(foods);
        }

        @Override
        public ObservableList<Food> getFoodList() {
            return foods;
        }
    }

}
