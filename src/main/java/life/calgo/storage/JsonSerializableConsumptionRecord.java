package life.calgo.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import life.calgo.commons.exceptions.IllegalValueException;
import life.calgo.model.ConsumptionRecord;
import life.calgo.model.ReadOnlyConsumptionRecord;
import life.calgo.model.day.DailyFoodLog;

@JsonRootName(value = "consumptionrecord")
public class JsonSerializableConsumptionRecord {

    public static final String MESSAGE_DUPLICATE_DAILYFOODLOG = "Consumption Record contains duplicate log(s).";

    private final List<JsonAdaptedDailyFoodLog> logs = new ArrayList<>();

    @JsonCreator
    public JsonSerializableConsumptionRecord(@JsonProperty("logs") List<JsonAdaptedDailyFoodLog> logs) {
        this.logs.addAll(logs);
    }

    public JsonSerializableConsumptionRecord(ReadOnlyConsumptionRecord source) {
        logs.addAll(source.getDailyFoodLogs().stream().map(JsonAdaptedDailyFoodLog::new).collect(Collectors.toList()));
    }

    public ConsumptionRecord toModelType() throws IllegalValueException {
        ConsumptionRecord consumptionRecord = new ConsumptionRecord();
        for (JsonAdaptedDailyFoodLog jsonAdaptedDailyFoodLog : logs) {
            DailyFoodLog dailyFoodLog = jsonAdaptedDailyFoodLog.toModelType();
            if (consumptionRecord.hasLogWithSameDate(dailyFoodLog)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DAILYFOODLOG); // unlikely to have, but just throw.
            }
            consumptionRecord.addLog(dailyFoodLog);
        }
        return consumptionRecord;
    }
}
