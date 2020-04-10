package life.calgo.model.food;

import static java.util.Objects.requireNonNull;

import life.calgo.commons.util.AppUtil;

/**
 * Represents a Food's protein content in grams in the food record.
 * Guarantees: immutable; is valid as declared in {@link #isValidProtein(String)}
 */
public class Protein {

    public static final String MESSAGE_CONSTRAINTS =
            "Protein should only contain non-negative integers within 5 digits and it should not be blank.";

    public static final String VALIDATION_REGEX = "[0-9]+(?=$|\\s)";

    public final String value;

    /**
     * Constructs an {@code Protein}.
     *
     * @param proteinGrams A valid Protein amount in grams.
     */
    public Protein(String proteinGrams) {
        requireNonNull(proteinGrams);
        AppUtil.checkArgument(isValidProtein(proteinGrams), MESSAGE_CONSTRAINTS);
        value = proteinGrams;
    }

    /**
     * Returns true if a given string is a valid Protein.
     *
     * @param test the String representation of the Protein's value.
     * @return whether this can be considered a valid Protein.
     */
    public static boolean isValidProtein(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the String representation of the Protein's value.
     *
     * @return the String representation of the Protein's value.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Checks if the current Protein can be considered equivalent to the other, based on identity and value.
     *
     * @param other the other Protein to compare with.
     * @return whether the current Protein and the other can be considered equivalent.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Protein // instanceof handles nulls
                && value.equals(((Protein) other).value)); // state check
    }

    /**
     * Provides hashcode for the current Protein object.
     *
     * @return hashcode for the current Protein object.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
