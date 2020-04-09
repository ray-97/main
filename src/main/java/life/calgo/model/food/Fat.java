package life.calgo.model.food;

import static java.util.Objects.requireNonNull;

import life.calgo.commons.util.AppUtil;

/**
 * Represents a Food's fat content in grams in the Food Records.
 * Guarantees: immutable; is valid as declared in {@link #isValidFat(String)}
 */
public class Fat {


    public static final String MESSAGE_CONSTRAINTS =
            "Fat should only contain non-negative integers within 5 digits and it should not be blank.";
    public static final String VALIDATION_REGEX = "[0-9]+(?=$|\\s)";
    public final String value;

    /**
     * Constructs a {@code Fat}.
     *
     * @param fatGrams A valid Fat amount in grams.
     */
    public Fat(String fatGrams) {
        requireNonNull(fatGrams);
        AppUtil.checkArgument(isValidFat(fatGrams), MESSAGE_CONSTRAINTS);
        value = fatGrams;
    }

    /**
     * Returns true if a given string is a valid Fat.
     *
     * @param test the String representation of the Fat's value.
     * @return whether this can be considered a valid Fat.
     */
    public static boolean isValidFat(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the String representation of the Fat's value.
     *
     * @return the String representation of the Fat's value.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Checks if the current Fat can be considered equivalent to the other, based on identity and value.
     *
     * @param other the other Fat to compare with.
     * @return whether the current Fat and the other can be considered equivalent.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Fat
                && value.equals(((Fat) other).value));
    }

    /**
     * Provides hashcode for the current Fat object.
     *
     * @return hashcode for the current Fat object.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
