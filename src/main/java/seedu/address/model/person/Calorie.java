package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Foods's caloric content in the Food Records.
 * Guarantees: immutable; is valid as declared in {@link #isValidCalorie(String)}
 */
public class Calorie {


    public static final String MESSAGE_CONSTRAINTS =
            "Calorie should only contain numbers and it should not be blank";
    public static final String VALIDATION_REGEX = "[0-9]+(?=$|\\s)";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param caloricValue A valid calorie amount.
     */
    public Calorie(String caloricValue) {
        requireNonNull(caloricValue);
        checkArgument(isValidCalorie(caloricValue), MESSAGE_CONSTRAINTS);
        value = caloricValue;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidCalorie(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Calorie // instanceof handles nulls
                && value.equals(((Calorie) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
