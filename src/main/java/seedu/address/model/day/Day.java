package seedu.address.model.day;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Objects;


public class Day {
    // Identity fields
    private final LocalDate localDate;

    // Data fields
    // food list to be added once food is implemented. then use those methods.

    /**
     * Default constructor that creates a Day object reflecting the present day.
     */
    public Day() {
        localDate = LocalDate.now();
    }

    /**
     * Every field must be present and not null.
     */
    public Day(LocalDate localDate) {
        requireAllNonNull(localDate);
        this.localDate = localDate;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Day)) {
            return false;
        }

        Day otherDay = (Day) other;
        return otherDay.getLocalDate().equals(getLocalDate());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(localDate);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(localDate);
        // can append food in list once implemented
        // or other stats
        return builder.toString();
    }
}
