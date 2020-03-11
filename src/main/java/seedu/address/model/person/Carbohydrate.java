package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Food's carbohydrate content in grams in the Food Records.
 * Guarantees: immutable; is valid as declared in {@link #isValidCarbohydrate(String)}
 */
public class Carbohydrate {


    public static final String MESSAGE_CONSTRAINTS =
            "Carbohydrate should only contain numbers and it should not be blank";
    public static final String VALIDATION_REGEX = "[0-9]+(?=$|\\s)";
    public final String value;

    /**
     * Constructs a {@code Carbohydrate}.
     *
     * @param carbohydrateGrams A valid fat amount in grams.
     */
    public Carbohydrate(String carbohydrateGrams) {
        requireNonNull(carbohydrateGrams);
        checkArgument(isValidCarbohydrate(carbohydrateGrams), MESSAGE_CONSTRAINTS);
        value = carbohydrateGrams;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidCarbohydrate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Carbohydrate // instanceof handles nulls
                && value.equals(((Carbohydrate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
