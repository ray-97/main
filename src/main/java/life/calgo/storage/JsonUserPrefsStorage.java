package life.calgo.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import life.calgo.commons.exceptions.DataConversionException;
import life.calgo.commons.util.JsonUtil;
import life.calgo.model.ReadOnlyUserPrefs;
import life.calgo.model.UserPrefs;

/**
 * A class to access UserPrefs stored in the hard disk as a json file.
 */
public class JsonUserPrefsStorage implements UserPrefsStorage {

    private Path filePath;

    public JsonUserPrefsStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getUserPrefsFilePath() {
        return filePath;
    }

    /**
     * Reads the user preferences from the pre-specified file path.
     *
     * @return the UserPrefs object wrapped in an Optional.
     * @throws DataConversionException if the data in the file is not in the expected format.
     */
    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException {
        return readUserPrefs(filePath);
    }

    /**
     * Similar to {@link #readUserPrefs()}, but reads from the specified file path.
     *
     * @param prefsFilePath the explicitly specified location of the source data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<UserPrefs> readUserPrefs(Path prefsFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(prefsFilePath, UserPrefs.class);
    }

    /**
     * Saves the user preferences into the pre-specified file path.
     *
     * @param userPrefs the user preferences to save, which cannot be null.
     * @throws IOException if there was any problem when writing to the file.
     */
    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        JsonUtil.saveJsonFile(userPrefs, filePath);
    }

}
