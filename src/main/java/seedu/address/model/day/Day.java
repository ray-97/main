package seedu.address.model.day;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Objects;


public class Day {
    // Identity fields
    private final LocalDate localDate;

    // Data fields
    private final DailyFoodLog dailyFoodLog;
    // food list does not have to be unique, so we can follow rules of immutability.

    public Day() {
        localDate = LocalDate.now();
        dailyFoodLog = new DailyFoodLog();
    }

    public Day(LocalDate localDate) {
        requireAllNonNull(localDate);
        this.localDate = localDate;
        dailyFoodLog = new DailyFoodLog();
    }

    public Day(LocalDate localDate, DailyFoodLog dailyFoodLog) {
        requireAllNonNull(localDate, dailyFoodLog);
        this.localDate = localDate;
        this.dailyFoodLog = dailyFoodLog;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public DailyFoodLog getDailyFoodLog() {
        return dailyFoodLog;
    }

    public static boolean isValidDate(String test) {
        return true;
        // else throw exceptions
    }

    public boolean isSameDay(Day otherDay) {
        if (otherDay == this) {
            return true;
        }

        return otherDay != null
                && otherDay.getLocalDate().equals(getLocalDate());
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
        return otherDay.getLocalDate().equals(getLocalDate())
                && otherDay.getDailyFoodLog().equals(getDailyFoodLog());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(localDate, dailyFoodLog);
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
