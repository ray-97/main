package life.calgo.model;

import java.time.LocalDate;
import java.util.List;

import javafx.collections.ObservableList;
import life.calgo.logic.commands.exceptions.CommandException;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.food.ConsumedFood;

public interface ReadOnlyConsumptionRecord {

    ObservableList<ConsumedFood> getDailyList();

    void setDailyListDate(LocalDate date) throws CommandException;

    List<DailyFoodLog> getDailyFoodLogs();
}
