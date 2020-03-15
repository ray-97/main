package f11_1.calgo.model.day;

import static f11_1.calgo.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class UniqueDayMap {

    private final ObservableMap<LocalDate,Day> internalMap = FXCollections.observableHashMap();

    public Day getDayByDate(LocalDate date) {
        return internalMap.get(date);
    }

    public boolean hasDay(Day day) {
        return internalMap.containsKey(day.getLocalDate());
    }

    public void addDay(Day day) {
        internalMap.put(day.getLocalDate(), day);
    }

    public void addConsumption(Day dayAfterConsumption) {
        requireAllNonNull(dayAfterConsumption);
        internalMap.put(dayAfterConsumption.getLocalDate(), dayAfterConsumption);
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
