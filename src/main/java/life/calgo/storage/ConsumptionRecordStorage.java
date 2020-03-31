package life.calgo.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import life.calgo.commons.exceptions.DataConversionException;
import life.calgo.model.ReadOnlyConsumptionRecord;

public interface ConsumptionRecordStorage {

    public Path getConsumptionRecordFilePath();

    Optional<ReadOnlyConsumptionRecord> readConsumptionRecord() throws DataConversionException, IOException;

    Optional<ReadOnlyConsumptionRecord> readConsumptionRecord(Path filePath) throws DataConversionException, IOException;

    void saveConsumptionRecord(ReadOnlyConsumptionRecord consumptionRecord) throws IOException;

    void saveConsumptionRecord(ReadOnlyConsumptionRecord consumptionRecord, Path filePath) throws IOException;

}
