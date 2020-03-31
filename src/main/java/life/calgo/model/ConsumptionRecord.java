package life.calgo.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import javafx.collections.ObservableList;
import life.calgo.logic.commands.exceptions.CommandException;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.day.UniqueDateToLogMap;
import life.calgo.model.food.ConsumedFood;
import life.calgo.model.food.Food;

public class ConsumptionRecord implements ReadOnlyConsumptionRecord {
    private final UniqueDateToLogMap dateToLogMap;

    {
        dateToLogMap = new UniqueDateToLogMap();
    }

    public ConsumptionRecord() {}

    public ConsumptionRecord(ReadOnlyConsumptionRecord toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public void resetData(ReadOnlyConsumptionRecord newData) {
        requireNonNull(newData);
        dateToLogMap.setDateToLogMap(newData.getDateToLogMap());
    }

    @Override
    public HashMap<LocalDate, DailyFoodLog> getDateToLogMap() {
        return dateToLogMap.getDateToLogMap();
    }

    public String toString() {
        return dateToLogMap.toString();
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

    public void updateLog(DailyFoodLog logAfterConsumption) {
        dateToLogMap.updateLog(logAfterConsumption);
    }

    public void updateConsumedLists(Food food) {
        dateToLogMap.updateMapWithFood(food);
    }

    @Override
    public void setDailyListDate(LocalDate date) throws CommandException {
        dateToLogMap.setDailyListDate(date);
    }

    @Override
    public ObservableList<ConsumedFood> getDailyList() {
        return dateToLogMap.asUnmodifiableDailyList();
    }

    @Override
    public List<DailyFoodLog> getDailyFoodLogs() {
        return dateToLogMap.getDailyFoodLogs();
    }
//    @Override
//    public boolean equals(Object other) {
//        return other == this // short circuit if same object
//                || (other instanceof FoodRecord // instanceof handles nulls
//                && foodList.equals(((FoodRecord) other).foodList));
//    }
//
//    @Override
//    public int hashCode() {
//        return foodList.hashCode();
//    }
}
