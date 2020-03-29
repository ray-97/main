package life.calgo.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import life.calgo.testutil.Assert;

public class ProteinTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Protein(null));
    }

    @Test
    public void constructor_invalidProtein_throwsIllegalArgumentException() {
        String invalidProtein = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Protein(invalidProtein));
    }

    @Test
    public void isValidProtein() {
        // null fat
        Assert.assertThrows(NullPointerException.class, () -> Protein.isValidProtein(null));

        // invalid fat
        assertFalse(Protein.isValidProtein("")); // empty string
        assertFalse(Protein.isValidProtein(" ")); // spaces only
        assertFalse(Protein.isValidProtein("phone")); // non-numeric
        assertFalse(Protein.isValidProtein("9011p041")); // alphabets within digits
        assertFalse(Protein.isValidProtein("9312 1534")); // spaces within digits

        // valid fat
        assertTrue(Protein.isValidProtein("122")); // normal fat number
        assertTrue(Protein.isValidProtein("124293842033123")); // long fat numbers
    }
}
