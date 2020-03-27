package life.calgo.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static life.calgo.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import life.calgo.logic.commands.CommandTestUtil;
import life.calgo.testutil.Assert;
import life.calgo.testutil.FoodBuilder;
import life.calgo.testutil.TypicalFoodItems;
import life.calgo.testutil.TypicalPersons;
import org.junit.jupiter.api.Test;

import life.calgo.model.food.exceptions.DuplicateFoodException;
import life.calgo.model.food.exceptions.FoodNotFoundException;

public class UniqueFoodListTest {

    private final UniqueFoodList uniqueFoodList = new UniqueFoodList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueFoodList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueFoodList.contains(TypicalFoodItems.APPLE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueFoodList.add(TypicalFoodItems.APPLE);
        assertTrue(uniqueFoodList.contains(TypicalFoodItems.APPLE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueFoodList.add(TypicalFoodItems.APPLE);
        Food editedAlice = new FoodBuilder(TypicalFoodItems.APPLE).withProtein(CommandTestUtil.VALID_ADDRESS_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueFoodList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueFoodList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueFoodList.add(TypicalFoodItems.APPLE);
        Assert.assertThrows(DuplicateFoodException.class, () -> uniqueFoodList.add(TypicalFoodItems.APPLE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueFoodList.setFood(null, TypicalFoodItems.APPLE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueFoodList.setFood(TypicalFoodItems.APPLE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        Assert.assertThrows(FoodNotFoundException.class, () -> uniqueFoodList.setFood(TypicalFoodItems.APPLE, TypicalFoodItems.APPLE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueFoodList.add(TypicalFoodItems.APPLE);
        uniqueFoodList.setFood(TypicalFoodItems.APPLE, TypicalFoodItems.APPLE);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(TypicalFoodItems.APPLE);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueFoodList.add(TypicalFoodItems.APPLE);
        Food editedAlice = new FoodBuilder(TypicalFoodItems.APPLE).withProtein(CommandTestUtil.VALID_ADDRESS_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        uniqueFoodList.setFood(TypicalFoodItems.APPLE, editedAlice);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(editedAlice);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueFoodList.add(TypicalFoodItems.APPLE);
        uniqueFoodList.setFood(TypicalFoodItems.APPLE, TypicalFoodItems.BANANA_MILKSHAKE);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(TypicalFoodItems.BANANA_MILKSHAKE);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueFoodList.add(TypicalFoodItems.APPLE);
        uniqueFoodList.add(TypicalFoodItems.BANANA_MILKSHAKE);
        Assert.assertThrows(DuplicateFoodException.class, () -> uniqueFoodList.setFood(TypicalFoodItems.APPLE, TypicalFoodItems.BANANA_MILKSHAKE));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueFoodList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        Assert.assertThrows(FoodNotFoundException.class, () -> uniqueFoodList.remove(TypicalFoodItems.APPLE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueFoodList.add(TypicalFoodItems.APPLE);
        uniqueFoodList.remove(TypicalFoodItems.APPLE);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueFoodList.setFoods((UniqueFoodList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueFoodList.add(TypicalFoodItems.APPLE);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(TypicalFoodItems.BANANA_MILKSHAKE);
        uniqueFoodList.setFoods(expectedUniqueFoodList);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueFoodList.setFoods((List<Food>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueFoodList.add(TypicalFoodItems.APPLE);
        List<Food> foodList = Collections.singletonList(TypicalFoodItems.BANANA_MILKSHAKE);
        uniqueFoodList.setFoods(foodList);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(TypicalFoodItems.BANANA_MILKSHAKE);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Food> listWithDuplicateFoods = Arrays.asList(TypicalFoodItems.APPLE, TypicalFoodItems.APPLE);
        Assert.assertThrows(DuplicateFoodException.class, () -> uniqueFoodList.setFoods(listWithDuplicateFoods));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, ()
            -> uniqueFoodList.asUnmodifiableObservableList().remove(0));
    }
}
