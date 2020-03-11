package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Food's protein content in grams in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidProtein(String)}
 */
public class Protein {

    public static final String MESSAGE_CONSTRAINTS = "Proteins can take any integer values, and it should not be blank";

    /*
     * The first character of the Protein must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[0-9]+(?=$|\\s)";

    public final String value;

    /**
     * Constructs an {@code Protein}.
     *
     * @param proteinGrams A valid protein amount in grams.
     */
    public Protein(String proteinGrams) {
        requireNonNull(proteinGrams);
        checkArgument(isValidProtein(proteinGrams), MESSAGE_CONSTRAINTS);
        value = proteinGrams;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidProtein(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Protein // instanceof handles nulls
                && value.equals(((Protein) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
