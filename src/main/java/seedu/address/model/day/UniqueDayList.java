package seedu.address.model.day;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

public class UniqueDayList implements Iterable<Day> {

    private final ObservableList<Day> internalList = FXCollections.observableArrayList();
    private final ObservableList<Day> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public boolean contains(Day toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    public void add(Day toAdd) {
        requireNonNull(toAdd);
//        if (contains(toAdd)) {
//            throw new DuplicateDayException();
//        }
        internalList.add(toAdd);
    }

    public void setDay(Day target, Day editedDay) {
        requireAllNonNull(target, editedDay);

        int index = internalList.indexOf(target);
//        if (index == -1) {
//            throw new DayNotFoundException();
//        }
//
//        if (!target.equals(editedDay) && contains(editedDay)) {
//            throw new DuplicateDayException();
//        }

        internalList.set(index, editedDay);
    }

    public void addConsumption(Day before, Day after) {
        requireAllNonNull(before, after);

        int index = internalList.indexOf(before);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!before.equals(after) && contains(after)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, after);
    }

    public void remove(Day toRemove) {
        requireNonNull(toRemove);
//        if (!internalList.remove(toRemove)) {
//            throw new DayNotFoundException();
//        }
    }

    public void setDays(UniqueDayList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    // overloaded
    public void setDays(List<Day> days) {
        requireAllNonNull(days);
//        if (!daysAreUnique(days)) {
//            throw new DuplicateDayException();
//        }

        internalList.setAll(days);
    }

    public ObservableList<Day> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Day> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueDayList // instanceof handles nulls
                && internalList.equals(((UniqueDayList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    private boolean daysAreUnique(List<Day> days) {
        for (int i = 0; i < days.size() - 1; i++) {
            for (int j = i + 1; j < days.size(); j++) {
                if (days.get(i).equals(days.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
