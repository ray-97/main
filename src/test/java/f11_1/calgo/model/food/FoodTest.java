package f11_1.calgo.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static f11_1.calgo.testutil.Assert.assertThrows;

import f11_1.calgo.logic.commands.CommandTestUtil;
import f11_1.calgo.testutil.Assert;
import f11_1.calgo.testutil.FoodBuilder;
import f11_1.calgo.testutil.TypicalPersons;
import org.junit.jupiter.api.Test;

public class FoodTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Food food = new FoodBuilder().build();
        Assert.assertThrows(UnsupportedOperationException.class, () -> food.getTags().remove(0));
    }

    @Test
    public void isSameFood() {
        // same object -> returns true
        assertTrue(TypicalPersons.ALICE.isSameFood(TypicalPersons.ALICE));

        // null -> returns false
        assertFalse(TypicalPersons.ALICE.isSameFood(null));

        // different phone and email -> returns false
        Food editedAlice = new FoodBuilder(TypicalPersons.ALICE).withCalorie(CommandTestUtil.VALID_PHONE_BOB).withFat(CommandTestUtil.VALID_EMAIL_BOB).build();
        assertFalse(TypicalPersons.ALICE.isSameFood(editedAlice));

        // different name -> returns false
        editedAlice = new FoodBuilder(TypicalPersons.ALICE).withName(CommandTestUtil.VALID_NAME_BOB).build();
        assertFalse(TypicalPersons.ALICE.isSameFood(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new FoodBuilder(TypicalPersons.ALICE).withFat(CommandTestUtil.VALID_EMAIL_BOB).withProtein(CommandTestUtil.VALID_ADDRESS_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        assertTrue(TypicalPersons.ALICE.isSameFood(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new FoodBuilder(TypicalPersons.ALICE).withCalorie(CommandTestUtil.VALID_PHONE_BOB).withProtein(CommandTestUtil.VALID_ADDRESS_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        assertTrue(TypicalPersons.ALICE.isSameFood(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new FoodBuilder(TypicalPersons.ALICE).withProtein(CommandTestUtil.VALID_ADDRESS_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        assertTrue(TypicalPersons.ALICE.isSameFood(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Food aliceCopy = new FoodBuilder(TypicalPersons.ALICE).build();
        assertTrue(TypicalPersons.ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(TypicalPersons.ALICE.equals(TypicalPersons.ALICE));

        // null -> returns false
        assertFalse(TypicalPersons.ALICE.equals(null));

        // different type -> returns false
        assertFalse(TypicalPersons.ALICE.equals(5));

        // different person -> returns false
        assertFalse(TypicalPersons.ALICE.equals(TypicalPersons.BOB));

        // different name -> returns false
        Food editedAlice = new FoodBuilder(TypicalPersons.ALICE).withName(CommandTestUtil.VALID_NAME_BOB).build();
        assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new FoodBuilder(TypicalPersons.ALICE).withCalorie(CommandTestUtil.VALID_PHONE_BOB).build();
        assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new FoodBuilder(TypicalPersons.ALICE).withFat(CommandTestUtil.VALID_EMAIL_BOB).build();
        assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new FoodBuilder(TypicalPersons.ALICE).withProtein(CommandTestUtil.VALID_ADDRESS_BOB).build();
        assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new FoodBuilder(TypicalPersons.ALICE).withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        assertFalse(TypicalPersons.ALICE.equals(editedAlice));
    }
}
