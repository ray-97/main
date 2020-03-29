package life.calgo.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import life.calgo.testutil.Assert;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("apple*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("pineapple black")); // alphabets only
        assertTrue(Name.isValidName("100")); // numbers only
        assertTrue(Name.isValidName("apple the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Banana Milk")); // with capital letters
        assertTrue(Name.isValidName("Banana Pineapple Strawberry Grass Mud Juice")); // long names
    }
}
