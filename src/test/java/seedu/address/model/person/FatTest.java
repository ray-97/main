package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FatTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Fat(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new Fat(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null email
        assertThrows(NullPointerException.class, () -> Fat.isValidEmail(null));

        // blank email
        assertFalse(Fat.isValidEmail("")); // empty string
        assertFalse(Fat.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(Fat.isValidEmail("@example.com")); // missing local part
        assertFalse(Fat.isValidEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(Fat.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Fat.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(Fat.isValidEmail("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Fat.isValidEmail("peter jack@example.com")); // spaces in local part
        assertFalse(Fat.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Fat.isValidEmail(" peterjack@example.com")); // leading space
        assertFalse(Fat.isValidEmail("peterjack@example.com ")); // trailing space
        assertFalse(Fat.isValidEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(Fat.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Fat.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Fat.isValidEmail("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Fat.isValidEmail("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Fat.isValidEmail("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Fat.isValidEmail("peterjack@example.com-")); // domain name ends with a hyphen

        // valid email
        assertTrue(Fat.isValidEmail("PeterJack_1190@example.com"));
        assertTrue(Fat.isValidEmail("a@bc")); // minimal
        assertTrue(Fat.isValidEmail("test@localhost")); // alphabets only
        assertTrue(Fat.isValidEmail("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(Fat.isValidEmail("123@145")); // numeric local part and domain name
        assertTrue(Fat.isValidEmail("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Fat.isValidEmail("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Fat.isValidEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
