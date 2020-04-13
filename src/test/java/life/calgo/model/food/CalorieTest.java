package life.calgo.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import life.calgo.testutil.Assert;

public class CalorieTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Calorie(null));
    }

    @Test
    public void constructor_invalidCalorie_throwsIllegalArgumentException() {
        String invalidCalorie = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Calorie(invalidCalorie));
    }

    @Test
    public void isValidCalorie() {
        // null calorie
        Assert.assertThrows(NullPointerException.class, () -> Calorie.isValidCalorie(null));

        // invalid calorie
        assertFalse(Calorie.isValidCalorie("")); // empty string
        assertFalse(Calorie.isValidCalorie(" ")); // spaces only
        assertFalse(Calorie.isValidCalorie("phone")); // non-numeric
        assertFalse(Calorie.isValidCalorie("9011p041")); // alphabets within digits
        assertFalse(Calorie.isValidCalorie("9312 1534")); // spaces within digits

        // valid calorie
        assertTrue(Calorie.isValidCalorie("122")); // normal calorie number
        assertTrue(Calorie.isValidCalorie("124293842033123")); // long calorie numbers
    }
}
