package life.calgo.model.day;

import static life.calgo.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import life.calgo.model.food.ConsumedFood;
import life.calgo.model.food.Food;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UniqueDayMap {

    private final HashMap<LocalDate, Day> internalMap = new HashMap<>();


    // these methods have to change internal list as well
    public Day getDayByDate(LocalDate date) {
        return internalMap.get(date);
    } // return with empty food list if day not present?

    public boolean hasDay(Day day) {
        return internalMap.containsKey(day.getLocalDate());
    }

    public boolean hasDate(LocalDate date) {
        return internalMap.containsKey(date);
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
        try {
            LinkedHashMap<Food, Double> foods= internalMap.get(date).getDailyFoodLog().getFoods();
            // assuming it is only a wrapper and still refers to same internalList
            for (Food food : foods.keySet()) {
                internalList.add(new ConsumedFood(food, foods.get(food), date));
            }
            System.out.println(internalList.size());
        } catch (NullPointerException e) {
            return internalList;
        }
        // internalList.add(new ConsumedFood());
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
