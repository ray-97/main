package seedu.address.model.day;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

public class UniqueDayMap {

    private final ObservableMap<LocalDate,Day> internalMap = FXCollections.observableHashMap();
//    private final ObservableList<Day> internalUnmodifiableList =
//            FXCollections.unmodifiableObservableList(internalList);

    // let Day be same if same local date.
    public boolean contains(Day toCheck) {
        requireNonNull(toCheck);
        return internalMap.stream().anyMatch(toCheck::equals);
    }

    public void add(Day toAdd) {
        requireNonNull(toAdd);
//        if (contains(toAdd)) {
//            throw new DuplicateDayException();
//        }
        internalMap.add(toAdd);
    }

    public void setDay(Day target, Day editedDay) {
        requireAllNonNull(target, editedDay);

        int index = internalMap.indexOf(target);
//        if (index == -1) {
//            throw new DayNotFoundException();
//        }
//
//        if (!target.equals(editedDay) && contains(editedDay)) {
//            throw new DuplicateDayException();
//        }

        internalMap.set(index, editedDay);
    }

    public void addConsumption(Day before, Day after) {
        requireAllNonNull(before, after);

        int index = internalMap.indexOf(before);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!before.equals(after) && contains(after)) {
            throw new DuplicatePersonException();
        }

        internalMap.set(index, after);
    }

    public void remove(Day toRemove) {
        requireNonNull(toRemove);
//        if (!internalList.remove(toRemove)) {
//            throw new DayNotFoundException();
//        }
    }

    public void setDays(UniqueDayMap replacement) {
        requireNonNull(replacement);
        internalMap.setAll(replacement.internalMap);
    }

    // overloaded
    public void setDays(List<Day> days) {
        requireAllNonNull(days);
//        if (!daysAreUnique(days)) {
//            throw new DuplicateDayException();
//        }

        internalMap.setAll(days);
    }

//    public ObservableList<Day> asUnmodifiableObservableList() {
//        return internalUnmodifiableList;
//    }

//    @Override
//    public Iterator<Day> iterator() {
//        return internalMap.iterator();
//    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueDayMap // instanceof handles nulls
                && internalMap.equals(((UniqueDayMap) other).internalMap));
    }

    @Override
    public int hashCode() {
        return internalMap.hashCode();
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

    public void addConsumptionToDay(Day dayConsumed) {
        // replace day in hashmap with our dayConsumed.
    }
}
