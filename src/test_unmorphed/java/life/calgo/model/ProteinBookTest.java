package f11_1.calgo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static f11_1.calgo.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import f11_1.calgo.logic.commands.CommandTestUtil;
import f11_1.calgo.model.food.Food;
import f11_1.calgo.model.food.exceptions.DuplicateFoodException;
import f11_1.calgo.testutil.Assert;
import f11_1.calgo.testutil.FoodBuilder;
import f11_1.calgo.testutil.TypicalPersons;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProteinBookTest {

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
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        FoodRecord newData = TypicalPersons.getTypicalAddressBook();
        foodRecord.resetData(newData);
        assertEquals(newData, foodRecord);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Food editedAlice = new FoodBuilder(TypicalPersons.ALICE).withProtein(CommandTestUtil.VALID_ADDRESS_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        List<Food> newFoods = Arrays.asList(TypicalPersons.ALICE, editedAlice);
        FoodRecordStub newData = new FoodRecordStub(newFoods);

        Assert.assertThrows(DuplicateFoodException.class, () -> foodRecord.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> foodRecord.hasFood(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(foodRecord.hasFood(TypicalPersons.ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        foodRecord.addFood(TypicalPersons.ALICE);
        assertTrue(foodRecord.hasFood(TypicalPersons.ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        foodRecord.addFood(TypicalPersons.ALICE);
        Food editedAlice = new FoodBuilder(TypicalPersons.ALICE).withProtein(CommandTestUtil.VALID_ADDRESS_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        assertTrue(foodRecord.hasFood(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
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
