package life.calgo.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static life.calgo.testutil.Assert.assertThrows;

import life.calgo.testutil.Assert;
import org.junit.jupiter.api.Test;

public class CalorieTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Calorie(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Calorie(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Calorie.isValidCalorie(null));

        // invalid phone numbers
        assertFalse(Calorie.isValidCalorie("")); // empty string
        assertFalse(Calorie.isValidCalorie(" ")); // spaces only
        assertFalse(Calorie.isValidCalorie("91")); // less than 3 numbers
        assertFalse(Calorie.isValidCalorie("phone")); // non-numeric
        assertFalse(Calorie.isValidCalorie("9011p041")); // alphabets within digits
        assertFalse(Calorie.isValidCalorie("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Calorie.isValidCalorie("911")); // exactly 3 numbers
        assertTrue(Calorie.isValidCalorie("93121534"));
        assertTrue(Calorie.isValidCalorie("124293842033123")); // long phone numbers
    }
}
