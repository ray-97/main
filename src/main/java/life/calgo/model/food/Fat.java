package life.calgo.model.food;

import static java.util.Objects.requireNonNull;

import life.calgo.commons.util.AppUtil;

/**
 * Represents a Food's fat content in grams in the Food Records.
 * Guarantees: immutable; is valid as declared in {@link #isValidFat(String)}
 */
public class Fat {


    public static final String MESSAGE_CONSTRAINTS =
            "Fat should only contain numbers and it should not be blank";
    public static final String VALIDATION_REGEX = "[0-9]+(?=$|\\s)";
    public final String value;

    /**
     * Constructs a {@code Fat}.
     *
     * @param fatGrams A valid fat amount in grams.
     */
    public Fat(String fatGrams) {
        requireNonNull(fatGrams);
        AppUtil.checkArgument(isValidFat(fatGrams), MESSAGE_CONSTRAINTS);
        value = fatGrams;
    }

    /**
     * Returns true if a given string is a valid Fat.
     */
    public static boolean isValidFat(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Fat // instanceof handles nulls
                && value.equals(((Fat) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
