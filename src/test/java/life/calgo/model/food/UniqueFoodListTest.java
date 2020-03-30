package life.calgo.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import life.calgo.logic.commands.CommandTestUtil;
import life.calgo.model.food.exceptions.DuplicateFoodException;
import life.calgo.model.food.exceptions.FoodNotFoundException;
import life.calgo.testutil.Assert;
import life.calgo.testutil.FoodBuilder;
import life.calgo.testutil.TypicalFoodItems;

public class UniqueFoodListTest {

    private final UniqueFoodList uniqueFoodList = new UniqueFoodList();

    @Test
    public void contains_nullFood_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueFoodList.contains(null));
    }

    @Test
    public void contains_foodNotInList_returnsFalse() {
        assertFalse(uniqueFoodList.contains(TypicalFoodItems.APPLE));
    }

    @Test
    public void contains_foodInList_returnsTrue() {
        uniqueFoodList.add(TypicalFoodItems.APPLE);
        assertTrue(uniqueFoodList.contains(TypicalFoodItems.APPLE));
    }

    @Test
    public void contains_foodWithSameIdentityFieldsInList_returnsTrue() {
        uniqueFoodList.add(TypicalFoodItems.APPLE);
        Food editedApple = new FoodBuilder(TypicalFoodItems.APPLE)
                .withProtein(CommandTestUtil.VALID_PROTEIN_APPLE).withTags(CommandTestUtil.VALID_TAG_HARD)
                .build();
        assertTrue(uniqueFoodList.contains(editedApple));
    }

    @Test
    public void add_nullFood_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueFoodList.add(null));
    }

    @Test
    public void add_duplicateFood_throwsDuplicateFoodException() {
        uniqueFoodList.add(TypicalFoodItems.APPLE);
        Assert.assertThrows(DuplicateFoodException.class, () -> uniqueFoodList.add(TypicalFoodItems.APPLE));
    }

    @Test
    public void setFood_nullTargetFood_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueFoodList.setFood(null, TypicalFoodItems.APPLE));
    }

    @Test
    public void setFood_nullEditedFood_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueFoodList.setFood(TypicalFoodItems.APPLE, null));
    }

    @Test
    public void setFood_targetFoodNotInList_throwsFoodNotFoundException() {
        Assert.assertThrows(FoodNotFoundException.class, () ->
                uniqueFoodList.setFood(TypicalFoodItems.APPLE, TypicalFoodItems.APPLE));
    }

    @Test
    public void setFood_editedFoodIsSameFood_success() {
        uniqueFoodList.add(TypicalFoodItems.APPLE);
        uniqueFoodList.setFood(TypicalFoodItems.APPLE, TypicalFoodItems.APPLE);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(TypicalFoodItems.APPLE);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFood_editedFoodHasSameIdentity_success() {
        uniqueFoodList.add(TypicalFoodItems.APPLE);
        Food editedApple = new FoodBuilder(TypicalFoodItems.APPLE)
                .withProtein(CommandTestUtil.VALID_PROTEIN_APPLE).withTags(CommandTestUtil.VALID_TAG_HARD)
                .build();
        uniqueFoodList.setFood(TypicalFoodItems.APPLE, editedApple);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(editedApple);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFood_editedFoodHasDifferentIdentity_success() {
        uniqueFoodList.add(TypicalFoodItems.APPLE);
        uniqueFoodList.setFood(TypicalFoodItems.APPLE, TypicalFoodItems.BANANA_MILKSHAKE);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(TypicalFoodItems.BANANA_MILKSHAKE);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFood_editedFoodHasNonUniqueIdentity_throwsDuplicateFoodException() {
        uniqueFoodList.add(TypicalFoodItems.APPLE);
        uniqueFoodList.add(TypicalFoodItems.BANANA_MILKSHAKE);
        Assert.assertThrows(DuplicateFoodException.class, () ->
                uniqueFoodList.setFood(TypicalFoodItems.APPLE, TypicalFoodItems.BANANA_MILKSHAKE));
    }

    @Test
    public void remove_nullFood_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueFoodList.remove(null));
    }

    @Test
    public void remove_foodDoesNotExist_throwsFoodNotFoundException() {
        Assert.assertThrows(FoodNotFoundException.class, () -> uniqueFoodList.remove(TypicalFoodItems.APPLE));
    }

    @Test
    public void remove_existingFood_removesFood() {
        uniqueFoodList.add(TypicalFoodItems.APPLE);
        uniqueFoodList.remove(TypicalFoodItems.APPLE);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFoodItems_nullUniqueFoodList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueFoodList.setFoods((UniqueFoodList) null));
    }

    @Test
    public void setFoodItems_uniqueFoodList_replacesOwnListWithProvidedUniqueFoodList() {
        uniqueFoodList.add(TypicalFoodItems.APPLE);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(TypicalFoodItems.BANANA_MILKSHAKE);
        uniqueFoodList.setFoods(expectedUniqueFoodList);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFoodItems_nullList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueFoodList.setFoods((List<Food>) null));
    }

    @Test
    public void setFoodItems_list_replacesOwnListWithProvidedList() {
        uniqueFoodList.add(TypicalFoodItems.APPLE);
        List<Food> foodList = Collections.singletonList(TypicalFoodItems.BANANA_MILKSHAKE);
        uniqueFoodList.setFoods(foodList);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(TypicalFoodItems.BANANA_MILKSHAKE);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setFoodItems_listWithDuplicateFoodItems_throwsDuplicateFoodException() {
        List<Food> listWithDuplicateFoods = Arrays.asList(TypicalFoodItems.APPLE, TypicalFoodItems.APPLE);
        Assert.assertThrows(DuplicateFoodException.class, () -> uniqueFoodList.setFoods(listWithDuplicateFoods));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, ()
            -> uniqueFoodList.asUnmodifiableObservableList().remove(0));
    }
}
