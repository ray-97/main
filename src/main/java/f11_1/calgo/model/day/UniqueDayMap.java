package f11_1.calgo.model.day;

import static f11_1.calgo.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

import f11_1.calgo.model.food.ConsumedFood;
import f11_1.calgo.model.food.Food;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class UniqueDayMap {

    private final ObservableMap<LocalDate, Day> internalMap = FXCollections.observableHashMap();

    // these methods have to change internal list as well
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

    public ObservableList<ConsumedFood> getDailyListByDate(LocalDate date) {
        ObservableList<ConsumedFood> internalList = FXCollections.observableArrayList();
        LinkedHashMap<Food, Double> foods= internalMap.get(date).getDailyFoodLog().getFoods();
        // assuming it is only a wrapper and still refers to same internalList
        for (Food food : foods.keySet()) {
            internalList.add(new ConsumedFood(food, foods.get(food), date));
        }
        return internalList;
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
