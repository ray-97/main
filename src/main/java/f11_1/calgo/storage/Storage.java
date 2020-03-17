package f11_1.calgo.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import f11_1.calgo.model.ReadOnlyUserPrefs;
import f11_1.calgo.model.UserPrefs;
import f11_1.calgo.commons.exceptions.DataConversionException;
import f11_1.calgo.model.ReadOnlyFoodRecord;

/**
 * API of the Storage component
 */
public interface Storage extends FoodRecordStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getFoodRecordFilePath();

    @Override
    Optional<ReadOnlyFoodRecord> readFoodRecord() throws DataConversionException, IOException;

    @Override
    void saveFoodRecord(ReadOnlyFoodRecord foodRecord) throws IOException;

}
