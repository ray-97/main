package life.calgo.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import javafx.collections.ObservableList;
import life.calgo.logic.commands.exceptions.CommandException;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.day.UniqueDateToLogMap;
import life.calgo.model.food.DisplayFood;
import life.calgo.model.food.Food;

/**
 * Wraps all data at the consumption record level.
 */
public class ConsumptionRecord implements ReadOnlyConsumptionRecord {
    private final UniqueDateToLogMap dateToLogMap;

    {
        dateToLogMap = new UniqueDateToLogMap();
    }

    public ConsumptionRecord() {}

    /**
     * Creates a ConsumptionRecord using the DailyFoodLog objects in the {@code toBeCopied}.
     *
     * @param toBeCopied ReadOnlyConsumptionRecord that provides data for this record to reset with.
     */
    public ConsumptionRecord(ReadOnlyConsumptionRecord toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code ConsumptionRecord} with {@code newData}.
     */
    public void resetData(ReadOnlyConsumptionRecord newData) {
        requireNonNull(newData);
        dateToLogMap.setDateToLogMap(newData.getDateToLogMap());
    }

    @Override
    public HashMap<LocalDate, DailyFoodLog> getDateToLogMap() {
        return dateToLogMap.getDateToLogMap();
    }

    public void addLog(DailyFoodLog foodLog) {
        dateToLogMap.addLog(foodLog);
    }

    public boolean hasLogWithSameDate(DailyFoodLog foodLog) {
        return dateToLogMap.hasLogWithSameDate(foodLog);
    }

    public DailyFoodLog getLogByDate(LocalDate date) {
        return dateToLogMap.getLogByDate(date);
    }

    /**
     * Updates a log in {@code dateToLogMap}, replacing it with {@code logAfterConsumption}.
     *
     * @param logAfterConsumption DailyFoodLog object reflecting the food recently consumed.
     */
    public void updateLog(DailyFoodLog logAfterConsumption) {
        dateToLogMap.updateLog(logAfterConsumption);
    }

    public void updateConsumedLists(Food food) {
        dateToLogMap.updateMapWithFood(food);
    }

    public void setDailyListDate(LocalDate date) throws CommandException {
        dateToLogMap.setDailyListDate(date);
    }

    @Override
    public ObservableList<DisplayFood> getDailyList() {
        return dateToLogMap.asUnmodifiableDailyList();
    }

    @Override
    public List<DailyFoodLog> getDailyFoodLogs() {
        return dateToLogMap.getDailyFoodLogs();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ConsumptionRecord // instanceof handles nulls
                && dateToLogMap.equals(((ConsumptionRecord) other).dateToLogMap));
    }

    @Override
    public int hashCode() {
        return dateToLogMap.hashCode();
    }

}
