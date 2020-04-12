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
     * Checks if internalMap has an existing DailyFoodLog with same date.
     *
     * @param foodLog DailyFoodLog containing the date you want to search with.
     * @return True if internalMap contains a DailyFoodLog with same date as foodLog.
     */
    public boolean hasLogWithSameDate(DailyFoodLog foodLog) {
        return internalMap.containsKey(foodLog.getLocalDate());
    }

    /**
     * Populates internalMap with LocalDate and DailyFoodLog key value pairs.
     *
     * @param dateToLogMap HashMap containing data to populate internalMap.
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
     * Updates every food with same name in each DailyFoodLog in internalMap.
     *
     * @param food Food that is most up-to-date with Food Record.
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
     * Updates internal key-value pair by updating the value of the given date key.
     *
     * @param logAfterConsumption The updated day object after consuming a certain food
     */
    public void updateLog(DailyFoodLog logAfterConsumption) {
        requireAllNonNull(logAfterConsumption);
        internalMap.put(logAfterConsumption.getLocalDate(), logAfterConsumption);
    }

    /**
     * Sets internalList to reflect a DailyFoodLog with DisplayFood items.
     *
     * @param date Date of DailyFoodLog be reflected.
     * @throws CommandException If user requests to display an empty DailyFoodLog.
     */
    public void setDailyListDate(LocalDate date) throws CommandException {
        internalList.clear();
        if (!internalMap.containsKey(date)) {
            throw new CommandException(
                    String.format("Your consumption record is empty because you have not consumed food "
                    + "on %s before", date));
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
