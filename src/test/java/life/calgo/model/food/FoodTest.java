package life.calgo.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import life.calgo.logic.commands.CommandTestUtil;
import life.calgo.testutil.Assert;
import life.calgo.testutil.FoodBuilder;
import life.calgo.testutil.TypicalFoodItems;

public class FoodTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Food food = new FoodBuilder().build();
        Assert.assertThrows(UnsupportedOperationException.class, () -> food.getTags().remove(0));
    }

    @Test
    public void isSameFood() {
        // same object -> returns true
        assertTrue(TypicalFoodItems.APPLE.isSameFood(TypicalFoodItems.APPLE));

        // null -> returns false
        assertFalse(TypicalFoodItems.APPLE.isSameFood(null));

        // same name, different attributes -> returns true
        Food editedApple = new FoodBuilder(TypicalFoodItems.APPLE)
                .withCalorie(CommandTestUtil.VALID_CALORIE_BANANA)
                .withFat(CommandTestUtil.VALID_PROTEIN_BANANA).build();
        assertTrue(TypicalFoodItems.APPLE.isSameFood(editedApple));

        // different name -> returns false
        editedApple = new FoodBuilder(TypicalFoodItems.APPLE).withName(CommandTestUtil.VALID_NAME_BANANA).build();
        assertFalse(TypicalFoodItems.APPLE.isSameFood(editedApple));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Food aliceCopy = new FoodBuilder(TypicalFoodItems.APPLE).build();
        assertTrue(TypicalFoodItems.APPLE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(TypicalFoodItems.APPLE.equals(TypicalFoodItems.APPLE));

        // null -> returns false
        assertFalse(TypicalFoodItems.APPLE.equals(null));

        // different type -> returns false
        assertFalse(TypicalFoodItems.APPLE.equals(5));

        // different food -> returns false
        assertFalse(TypicalFoodItems.APPLE.equals(TypicalFoodItems.BANANA_MILKSHAKE));

        // different name -> returns false
        Food editedApple = new FoodBuilder(TypicalFoodItems.APPLE).withName(CommandTestUtil.VALID_NAME_BANANA).build();
        assertFalse(TypicalFoodItems.APPLE.equals(editedApple));

        // same name, different calorie -> returns false
        editedApple = new FoodBuilder(TypicalFoodItems.APPLE).withCalorie(CommandTestUtil.VALID_CALORIE_BANANA).build();
        assertFalse(TypicalFoodItems.APPLE.equals(editedApple));

        // same name, different fat -> returns false
        editedApple = new FoodBuilder(TypicalFoodItems.APPLE).withFat(CommandTestUtil.VALID_FAT_BANANA).build();
        assertFalse(TypicalFoodItems.APPLE.equals(editedApple));

        // same name, different protein -> returns false
        editedApple = new FoodBuilder(TypicalFoodItems.APPLE).withProtein(CommandTestUtil.VALID_PROTEIN_BANANA).build();
        assertFalse(TypicalFoodItems.APPLE.equals(editedApple));

        // same name, different carbohydrate -> returns false
        editedApple = new FoodBuilder(TypicalFoodItems.APPLE)
                .withCarbohydrate(CommandTestUtil.VALID_CARBOHYDRATE_BANANA).build();
        assertFalse(TypicalFoodItems.APPLE.equals(editedApple));

        // same name, different tags -> returns false
        editedApple = new FoodBuilder(TypicalFoodItems.APPLE).withTags(CommandTestUtil.VALID_TAG_HARD).build();
        assertFalse(TypicalFoodItems.APPLE.equals(editedApple));
    }
}
