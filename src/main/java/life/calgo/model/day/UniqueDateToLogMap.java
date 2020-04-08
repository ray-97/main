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
import life.calgo.model.food.DisplayFood;
import life.calgo.model.food.Food;

/**
 * A data structure to hold date keys which have corresponding food consumed as their values.
 */
public class UniqueDateToLogMap {

    private final HashMap<LocalDate, DailyFoodLog> internalMap = new HashMap<>();
    private final ObservableList<DisplayFood> internalList = FXCollections.observableArrayList();
    private final ObservableList<DisplayFood> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public DailyFoodLog getLogByDate(LocalDate date) {
        return internalMap.get(date);
    }

    public List<DailyFoodLog> getDailyFoodLogs() {
        return new ArrayList<>(internalMap.values());
    }

    /**
     * Returns true if internalMap contains a DailyFoodLog with same date as foodLog.
     */
    public boolean hasLogWithSameDate(DailyFoodLog foodLog) {
        return internalMap.containsKey(foodLog.getLocalDate());
    }

    /**
     * Populates internalMap with LocalDate and DailyFoodLog key value pairs.
     * @param dateToLogMap the HashMap containing data to populate internalMap.
     */
    public void setDateToLogMap(HashMap<LocalDate, DailyFoodLog> dateToLogMap) {
        internalMap.clear();
        for (DailyFoodLog dailyFoodLog : dateToLogMap.values()) {
            internalMap.put(dailyFoodLog.getLocalDate() , dailyFoodLog.copy());
        }
    }

    /**
     * Returns key value pairs inside internalMap as a HashMap.
     */
    public HashMap<LocalDate, DailyFoodLog> getDateToLogMap() {
        HashMap<LocalDate, DailyFoodLog> copy = new HashMap<>();
        for (DailyFoodLog dailyFoodLog : internalMap.values()) {
            copy.put(dailyFoodLog.getLocalDate(), dailyFoodLog.copy());
        }
        return copy;
    }

    /**
     * Updates every DailyFoodLog in internalMap with respective food item that is updated.
     */
    public void updateMapWithFood(Food food) {
        for (DailyFoodLog log: internalMap.values()) {
            internalMap.put(log.getLocalDate(), log.updateFoodWithSameName(food));
        }
    }

    public void addLog(DailyFoodLog foodLog) {
        internalMap.put(foodLog.getLocalDate(), foodLog);
    }

    /**
     * updates internal key-value pair by updating the value of the given date key
     * @param logAfterConsumption the updated day object after consuming a certain food
     */
    public void updateLog(DailyFoodLog logAfterConsumption) {
        requireAllNonNull(logAfterConsumption);
        internalMap.put(logAfterConsumption.getLocalDate(), logAfterConsumption);
    }

    /**
     * Sets internalList to reflect a DailyFoodLog with DisplayFood items.
     * @param date date of DailyFoodLog be reflected.
     * @throws CommandException if user requests to display an empty DailyFoodLog.
     */
    public void setDailyListDate(LocalDate date) throws CommandException {
        internalList.clear();
        if (!internalMap.containsKey(date)) {
            throw new CommandException(String.format("You have not consumed anything on %s yet", date));
        }
        Set<Food> foods = internalMap.get(date).getFoods();
        if (!foods.isEmpty()) {
            for (Food food : foods) {
                DailyFoodLog foodLog = internalMap.get(date);
                double portion = foodLog.getPortion(food);
                double rating = foodLog.getRating(food);
                internalList.add(new DisplayFood(food, portion, rating, date));
            }
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<DisplayFood> asUnmodifiableDailyList() {
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
