package life.calgo.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import life.calgo.testutil.Assert;

public class FatTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Fat(null));
    }

    @Test
    public void constructor_invalidFat_throwsIllegalArgumentException() {
        String invalidFat = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Fat(invalidFat));
    }

    @Test
    public void isValidFat() {
        // null fat
        Assert.assertThrows(NullPointerException.class, () -> Fat.isValidFat(null));

        // invalid fat
        assertFalse(Fat.isValidFat("")); // empty string
        assertFalse(Fat.isValidFat(" ")); // spaces only
        assertFalse(Fat.isValidFat("phone")); // non-numeric
        assertFalse(Fat.isValidFat("9011p041")); // alphabets within digits
        assertFalse(Fat.isValidFat("9312 1534")); // spaces within digits

        // valid fat
        assertTrue(Fat.isValidFat("122")); // normal fat number
        assertTrue(Fat.isValidFat("124293842033123")); // long fat numbers
    }
}
