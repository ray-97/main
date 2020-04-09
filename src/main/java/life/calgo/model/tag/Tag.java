package life.calgo.model.tag;

import static java.util.Objects.requireNonNull;

import life.calgo.commons.util.AppUtil;

/**
 * Represents a Tag in the food record.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be single-worded and alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String tagName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        AppUtil.checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
    }

    /**
     * Returns true if a given String is a valid Tag name.
     *
     * @param test The String to check for validity.
     * @return Whether the given String can be used for a valid Tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Checks if the current Tag can be considered equivalent to the other, based on identity and tagName.
     *
     * @param other The other Tag to compare with.
     * @return Whether the current Tag and the other can be considered equivalent.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Tag
                && tagName.equals(((Tag) other).tagName));
    }

    /**
     * Provides hashcode for the current Tag object.
     *
     * @return hashcode for the current Tag object.
     */
    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
