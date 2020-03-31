package life.calgo.model.day;

import static life.calgo.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import life.calgo.logic.commands.exceptions.CommandException;
import life.calgo.model.food.ConsumedFood;
import life.calgo.model.food.Food;

/**
 * A data structure to hold date keys which have corresponding food and portions consumed as their values
 */
public class UniqueDateToLogMap {

    private final HashMap<LocalDate, DailyFoodLog> internalMap = new HashMap<>();
    private final ObservableList<ConsumedFood> internalList = FXCollections.observableArrayList();
    private final ObservableList<ConsumedFood> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public DailyFoodLog getLogByDate(LocalDate date) {
        return internalMap.get(date); // returns null if not present
    }

    public boolean hasLogWithSameDate(DailyFoodLog foodLog) {
        return internalMap.containsKey(foodLog.getLocalDate());
    }

    public void addLog(DailyFoodLog foodLog) {
        internalMap.put(foodLog.getLocalDate(), foodLog);
    }

    public String toString() {
        String sb = "";
        for (DailyFoodLog dfl: internalMap.values()) {
            sb += dfl.toString();
        }
        return sb;
    }

    public void setDateToLogMap(HashMap<LocalDate, DailyFoodLog> dateToLogMap) {
        internalMap.clear();
        for (DailyFoodLog dailyFoodLog : dateToLogMap.values()) {
            internalMap.put(dailyFoodLog.getLocalDate() , dailyFoodLog.copy());
        }
    }

    public HashMap<LocalDate, DailyFoodLog> getDateToLogMap() {
        HashMap<LocalDate, DailyFoodLog> copy = new HashMap<>();
        for (DailyFoodLog dailyFoodLog : internalMap.values()) {
            copy.put(dailyFoodLog.getLocalDate(), dailyFoodLog.copy());
        }
        return copy;
    }

    /**
     * updates internal key-value pair by updating the value of the given date key
     * @param logAfterConsumption the updated day object after consuming a certain food
     */
    public void updateLog(DailyFoodLog logAfterConsumption) {
        requireAllNonNull(logAfterConsumption);
        internalMap.put(logAfterConsumption.getLocalDate(), logAfterConsumption);
    }

    public void updateMapWithFood(Food food) {
        for (DailyFoodLog log: internalMap.values()) {
            log.updateFoodWithSameName(food);
        }
    }

    public void setDailyListDate(LocalDate date) throws CommandException {
        internalList.clear();
        if (!internalMap.containsKey(date)) {
            throw new CommandException(String.format("You have not consumed anything on %s yet", date));
        }
        Set<Food> foods = internalMap.get(date).getFoods(); // null internalMap? or internalMap.get(date) null
        if (!foods.isEmpty()) {
            for (Food food : foods) {
                DailyFoodLog foodLog = internalMap.get(date);
                double portion = foodLog.getPortion(food);
                double rating = foodLog.getRating(food);
                internalList.add(new ConsumedFood(food, portion, rating, date));
            }
        }
    }

    public List<DailyFoodLog> getDailyFoodLogs() {
        return new ArrayList<>(internalMap.values());
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ConsumedFood> asUnmodifiableDailyList() {
        return internalUnmodifiableList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueDateToLogMap // instanceof handles nulls
                && internalMap.equals(((UniqueDateToLogMap) other).internalMap));
    }

    @Override
    public int hashCode() {
        return internalMap.hashCode();
    }
}
