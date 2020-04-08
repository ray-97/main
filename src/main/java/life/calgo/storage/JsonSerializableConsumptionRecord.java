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

/**
 * An Immutable ConsumptionRecord that is serializable to JSON format.
 */
@JsonRootName(value = "consumptionrecord")
public class JsonSerializableConsumptionRecord {

    public static final String MESSAGE_DUPLICATE_DAILYFOODLOG = "Consumption Record contains duplicate log(s).";

    private final List<JsonAdaptedDailyFoodLog> logs = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableConsumptionRecord} with the given logs.
     */
    @JsonCreator
    public JsonSerializableConsumptionRecord(@JsonProperty("logs") List<JsonAdaptedDailyFoodLog> logs) {
        this.logs.addAll(logs);
    }

    /**
     * Converts a given {@code ReadOnlyConsumptionRecord} into this class for Jackson use.
     *
     * @param source Future changes to this will not affect the created {@code JsonSerializableConsumptionRecord}.
     */
    public JsonSerializableConsumptionRecord(ReadOnlyConsumptionRecord source) {
        logs.addAll(source.getDailyFoodLogs().stream().map(JsonAdaptedDailyFoodLog::new).collect(Collectors.toList()));
    }

    /**
     * Converts this consumption record into the model's {@code ConsumptionRecord} object.
     *
     * @throws IllegalValueException If there were any data constraints violated.
     */
    public ConsumptionRecord toModelType() throws IllegalValueException {
        ConsumptionRecord consumptionRecord = new ConsumptionRecord();
        for (JsonAdaptedDailyFoodLog jsonAdaptedDailyFoodLog : logs) {
            DailyFoodLog dailyFoodLog = jsonAdaptedDailyFoodLog.toModelType();
            if (consumptionRecord.hasLogWithSameDate(dailyFoodLog)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DAILYFOODLOG);
            }
            consumptionRecord.addLog(dailyFoodLog);
        }
        return consumptionRecord;
    }
}
