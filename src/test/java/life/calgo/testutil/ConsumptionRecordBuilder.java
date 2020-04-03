package life.calgo.testutil;

import life.calgo.model.ConsumptionRecord;
import life.calgo.model.day.DailyFoodLog;

/**
 * A utility class to help with building ConsumptionRecord objects.
 */
public class ConsumptionRecordBuilder {

    private ConsumptionRecord consumptionRecord;

    public ConsumptionRecordBuilder() {
        consumptionRecord = new ConsumptionRecord();
    }

    /**
     * Returns a {@code ConsumptionRecordBuilder} based on a {@code DailyFoodLog}.
     */
    public ConsumptionRecordBuilder withLog(DailyFoodLog dailyFoodLog) {
        consumptionRecord.addLog(dailyFoodLog);
        return this;
    }

    public ConsumptionRecord build() {
        return consumptionRecord;
    }

}
