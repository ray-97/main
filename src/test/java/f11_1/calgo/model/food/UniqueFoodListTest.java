package f11_1.calgo.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static f11_1.calgo.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import f11_1.calgo.logic.commands.CommandTestUtil;
import f11_1.calgo.testutil.Assert;
import f11_1.calgo.testutil.FoodBuilder;
import f11_1.calgo.testutil.TypicalPersons;
import org.junit.jupiter.api.Test;

import f11_1.calgo.model.food.exceptions.DuplicateFoodException;
import f11_1.calgo.model.food.exceptions.FoodNotFoundException;

public class UniqueFoodListTest {

    private final UniqueFoodList uniqueFoodList = new UniqueFoodList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueFoodList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueFoodList.contains(TypicalPersons.ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueFoodList.add(TypicalPersons.ALICE);
        assertTrue(uniqueFoodList.contains(TypicalPersons.ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueFoodList.add(TypicalPersons.ALICE);
        Food editedAlice = new FoodBuilder(TypicalPersons.ALICE).withProtein(CommandTestUtil.VALID_ADDRESS_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueFoodList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueFoodList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueFoodList.add(TypicalPersons.ALICE);
        Assert.assertThrows(DuplicateFoodException.class, () -> uniqueFoodList.add(TypicalPersons.ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueFoodList.setFood(null, TypicalPersons.ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueFoodList.setFood(TypicalPersons.ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        Assert.assertThrows(FoodNotFoundException.class, () -> uniqueFoodList.setFood(TypicalPersons.ALICE, TypicalPersons.ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueFoodList.add(TypicalPersons.ALICE);
        uniqueFoodList.setFood(TypicalPersons.ALICE, TypicalPersons.ALICE);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(TypicalPersons.ALICE);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueFoodList.add(TypicalPersons.ALICE);
        Food editedAlice = new FoodBuilder(TypicalPersons.ALICE).withProtein(CommandTestUtil.VALID_ADDRESS_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        uniqueFoodList.setFood(TypicalPersons.ALICE, editedAlice);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(editedAlice);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueFoodList.add(TypicalPersons.ALICE);
        uniqueFoodList.setFood(TypicalPersons.ALICE, TypicalPersons.BOB);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(TypicalPersons.BOB);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueFoodList.add(TypicalPersons.ALICE);
        uniqueFoodList.add(TypicalPersons.BOB);
        Assert.assertThrows(DuplicateFoodException.class, () -> uniqueFoodList.setFood(TypicalPersons.ALICE, TypicalPersons.BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueFoodList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        Assert.assertThrows(FoodNotFoundException.class, () -> uniqueFoodList.remove(TypicalPersons.ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueFoodList.add(TypicalPersons.ALICE);
        uniqueFoodList.remove(TypicalPersons.ALICE);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueFoodList.setFoods((UniqueFoodList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueFoodList.add(TypicalPersons.ALICE);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(TypicalPersons.BOB);
        uniqueFoodList.setFoods(expectedUniqueFoodList);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueFoodList.setFoods((List<Food>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueFoodList.add(TypicalPersons.ALICE);
        List<Food> foodList = Collections.singletonList(TypicalPersons.BOB);
        uniqueFoodList.setFoods(foodList);
        UniqueFoodList expectedUniqueFoodList = new UniqueFoodList();
        expectedUniqueFoodList.add(TypicalPersons.BOB);
        assertEquals(expectedUniqueFoodList, uniqueFoodList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Food> listWithDuplicateFoods = Arrays.asList(TypicalPersons.ALICE, TypicalPersons.ALICE);
        Assert.assertThrows(DuplicateFoodException.class, () -> uniqueFoodList.setFoods(listWithDuplicateFoods));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, ()
            -> uniqueFoodList.asUnmodifiableObservableList().remove(0));
    }
}
