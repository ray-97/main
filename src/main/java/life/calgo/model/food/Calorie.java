package life.calgo.model.food;

import static java.util.Objects.requireNonNull;

import life.calgo.commons.util.AppUtil;

/**
 * Represents a Foods's caloric content in the Food Records.
 * Guarantees: immutable; is valid as declared in {@link #isValidCalorie(String)}
 */
public class Calorie {

    public static final String MESSAGE_CONSTRAINTS =
            "Calorie should only contain non-negative integers within 5 digits and it should not be blank.";
    public static final String VALIDATION_REGEX = "[0-9]+(?=$|\\s)";
    public final String value;

    /**
     * Constructs a {@code Calorie}.
     *
     * @param caloricValue A valid calorie amount.
     */
    public Calorie(String caloricValue) {
        requireNonNull(caloricValue);
        AppUtil.checkArgument(isValidCalorie(caloricValue), MESSAGE_CONSTRAINTS);
        value = caloricValue;
    }

    /**
     * Returns true if a given string is a valid Calorie.
     *
     * @param test the String representation of the Calorie's value.
     * @return whether this can be considered a valid Calorie.
     */
    public static boolean isValidCalorie(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the String representation of the Calorie's value.
     *
     * @return the String representation of the Calorie's value.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Checks if the current Calorie can be considered equivalent to the other, based on identity and value.
     *
     * @param other the other Calorie to compare with.
     * @return whether the current Calorie and the other can be considered equivalent.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Calorie
                && value.equals(((Calorie) other).value));
    }

    /**
     * Provides hashcode for the current Calorie object.
     *
     * @return hashcode for the current Calorie object.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
