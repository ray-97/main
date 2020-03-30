package life.calgo.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import life.calgo.testutil.Assert;

public class CarbohydrateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Carbohydrate(null));
    }

    @Test
    public void constructor_invalidCarbohydrate_throwsIllegalArgumentException() {
        String invalidCarbohydrate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Carbohydrate(invalidCarbohydrate));
    }

    @Test
    public void isValidCarbohydrate() {
        // null carbohydrate
        Assert.assertThrows(NullPointerException.class, () -> Carbohydrate.isValidCarbohydrate(null));

        // invalid carbohydrate
        assertFalse(Carbohydrate.isValidCarbohydrate("")); // empty string
        assertFalse(Carbohydrate.isValidCarbohydrate(" ")); // spaces only
        assertFalse(Carbohydrate.isValidCarbohydrate("phone")); // non-numeric
        assertFalse(Carbohydrate.isValidCarbohydrate("9011p041")); // alphabets within digits
        assertFalse(Carbohydrate.isValidCarbohydrate("9312 1534")); // spaces within digits

        // valid carbohydrate
        assertTrue(Carbohydrate.isValidCarbohydrate("122")); // normal carbohydrate number
        assertTrue(Carbohydrate.isValidCarbohydrate("124293842033123")); // long carbohydrate numbers
    }
}
