package f11_1.calgo.model.day;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

public class UniqueDayMap {

    private final ObservableMap<LocalDate,Day> internalMap = FXCollections.observableHashMap();

    public Day getDayByDate(LocalDate date) {
        return internalMap.get(date);
    }

    public void addConsumption(Day dayToAdd) {
        requireAllNonNull(dayToAdd);
        internalMap.put(dayToAdd.getLocalDate(), dayToAdd);
    }

    public void remove(Day toRemove) {
        requireNonNull(toRemove);
//        if (!internalList.remove(toRemove)) {
//            throw new DayNotFoundException();
//        }
    }

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

}
