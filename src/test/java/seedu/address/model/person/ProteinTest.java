package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ProteinTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Protein(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Protein(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Protein.isValidProtein(null));

        // invalid addresses
        assertFalse(Protein.isValidProtein("")); // empty string
        assertFalse(Protein.isValidProtein(" ")); // spaces only

        // valid addresses
        assertTrue(Protein.isValidProtein("Blk 456, Den Road, #01-355"));
        assertTrue(Protein.isValidProtein("-")); // one character
        assertTrue(Protein.isValidProtein("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
