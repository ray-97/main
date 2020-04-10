package life.calgo.model.food;

import static java.util.Objects.requireNonNull;

import life.calgo.commons.util.AppUtil;

/**
 * Represents a Food's name in the Food Records.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank.";

    /*
     * The first character of the food must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        AppUtil.checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     *
     * @param test the candidate Name for the Food.
     * @return whether this String is suitable to create a Name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Obtains the String representation of the Name.
     *
     * @return the String representation of the Name.
     */
    @Override
    public String toString() {
        return fullName;
    }

    /**
     * Checks if the current Name can be considered equivalent to the other, based on identity and value.
     *
     * @param other the other Name to compare with.
     * @return whether the current Name and the other can be considered equivalent.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Name
                && fullName.toLowerCase().equals(((Name) other).fullName.toLowerCase()));
    }

    /**
     * Provides hashcode for the current Name object.
     *
     * @return hashcode for the current Name object.
     */
    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
