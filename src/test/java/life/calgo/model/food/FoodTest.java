package life.calgo.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static life.calgo.testutil.Assert.assertThrows;

import life.calgo.logic.commands.CommandTestUtil;
import life.calgo.testutil.Assert;
import life.calgo.testutil.FoodBuilder;
import life.calgo.testutil.TypicalFoodItems;
import life.calgo.testutil.TypicalPersons;
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
        assertTrue(TypicalFoodItems.APPLE.isSameFood(TypicalFoodItems.APPLE)));

        // null -> returns false
        assertFalse(TypicalFoodItems.APPLE.isSameFood(null));

        // different phone and email -> returns false
        Food editedAlice = new FoodBuilder(TypicalFoodItems.APPLE).withCalorie(CommandTestUtil.VALID_PHONE_BOB).withFat(CommandTestUtil.VALID_EMAIL_BOB).build();
        assertFalse(TypicalFoodItems.APPLE.isSameFood(editedAlice));

        // different name -> returns false
        editedAlice = new FoodBuilder(TypicalFoodItems.APPLE).withName(CommandTestUtil.VALID_NAME_BOB).build();
        assertFalse(TypicalFoodItems.APPLE.isSameFood(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new FoodBuilder(TypicalFoodItems.APPLE).withFat(CommandTestUtil.VALID_EMAIL_BOB).withProtein(CommandTestUtil.VALID_ADDRESS_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        assertTrue(TypicalFoodItems.APPLE.isSameFood(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new FoodBuilder(TypicalFoodItems.APPLE).withCalorie(CommandTestUtil.VALID_PHONE_BOB).withProtein(CommandTestUtil.VALID_ADDRESS_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        assertTrue(TypicalFoodItems.APPLE.isSameFood(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new FoodBuilder(TypicalFoodItems.APPLE).withProtein(CommandTestUtil.VALID_ADDRESS_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        assertTrue(TypicalFoodItems.APPLE.isSameFood(editedAlice));
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

        // different person -> returns false
        assertFalse(TypicalFoodItems.APPLE.equals(TypicalFoodItems.BANANA_MILKSHAKE));

        // different name -> returns false
        Food editedAlice = new FoodBuilder(TypicalFoodItems.APPLE).withName(CommandTestUtil.VALID_NAME_BOB).build();
        assertFalse(TypicalFoodItems.APPLE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new FoodBuilder(TypicalFoodItems.APPLE).withCalorie(CommandTestUtil.VALID_PHONE_BOB).build();
        assertFalse(TypicalFoodItems.APPLE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new FoodBuilder(TypicalFoodItems.APPLE).withFat(CommandTestUtil.VALID_EMAIL_BOB).build();
        assertFalse(TypicalFoodItems.APPLE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new FoodBuilder(TypicalFoodItems.APPLE).withProtein(CommandTestUtil.VALID_ADDRESS_BOB).build();
        assertFalse(TypicalFoodItems.APPLE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new FoodBuilder(TypicalFoodItems.APPLE).withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        assertFalse(TypicalFoodItems.APPLE.equals(editedAlice));
    }
}
