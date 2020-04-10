package life.calgo.model.food;

import static java.util.Objects.requireNonNull;

import life.calgo.commons.util.AppUtil;

/**
 * Represents a Food's carbohydrate content in grams in the Food Records.
 * Guarantees: immutable; is valid as declared in {@link #isValidCarbohydrate(String)}
 */
public class Carbohydrate {

    public static final String MESSAGE_CONSTRAINTS =
            "Carbohydrate should only contain non-negative integers within 5 digits and it should not be blank.";
    public static final String VALIDATION_REGEX = "[0-9]+(?=$|\\s)";
    public final String value;

    /**
     * Constructs a {@code Carbohydrate}.
     *
     * @param carbohydrateGrams A valid Carbohydrate amount in grams.
     */
    public Carbohydrate(String carbohydrateGrams) {
        requireNonNull(carbohydrateGrams);
        AppUtil.checkArgument(isValidCarbohydrate(carbohydrateGrams), MESSAGE_CONSTRAINTS);
        value = carbohydrateGrams;
    }

    /**
     * Returns true if a given string is a valid Carbohydrate.
     *
     * @param test the String representation of the Carbohydrate's value.
     * @return whether this can be considered a valid Carbohydrate.
     */
    public static boolean isValidCarbohydrate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the String representation of the Carbohydrate's value.
     *
     * @return the String representation of the Carbohydrate's value.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Checks if the current Carbohydrate can be considered equivalent to the other, based on identity and value.
     *
     * @param other the other Carbohydrate to compare with.
     * @return whether the current Carbohydrate and the other can be considered equivalent.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Carbohydrate // instanceof handles nulls
                && value.equals(((Carbohydrate) other).value)); // state check
    }

    /**
     * Provides hashcode for the current Carbohydrate object.
     *
     * @return hashcode for the current Carbohydrate object.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
