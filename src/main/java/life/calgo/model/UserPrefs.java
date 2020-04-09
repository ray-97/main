package life.calgo.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import life.calgo.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path foodRecordFilePath = Paths.get("data" , "foodrecord.json");
    private Path consumptionRecordFilePath = Paths.get("data", "consumptionrecord.json");
    private Path goalFilePath = Paths.get("data", "goal.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     *
     * @param userPrefs The user preferences we wish to set.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     *
     * @param newUserPrefs The user preferences we wish to set.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setFoodRecordFilePath(newUserPrefs.getFoodRecordFilePath());
    }

    /**
     * Obtain current GUI settings.
     * This includes settings like Window size.
     *
     * @return The current GUI settings for the user.
     */
    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    /**
     * Sets the GUI settings to be that of the specified.
     *
     * @param guiSettings The specified GUI settings we wish to change to.
     */
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    /**
     * Obtains the FoodRecord's json file path.
     * This path stores all relevant Food data in a certain format.
     *
     * @return The FoodRecord's json file path.
     */
    public Path getFoodRecordFilePath() {
        return foodRecordFilePath;
    }

    /**
     * Sets the file path for the json file of the FoodRecord.
     * It is best to keep this in a convenient and appropriate location.
     *
     * @param foodRecordFilePath The new file path we wish to set to.
     */
    public void setFoodRecordFilePath(Path foodRecordFilePath) {
        requireNonNull(foodRecordFilePath);
        this.foodRecordFilePath = foodRecordFilePath;
    }

    /**
     * Obtains the ConsumptionRecord's json file path.
     * This path stores all relevant Food data in a certain format.
     *
     * @return The ConsumptionRecord's json file path.
     */
    public Path getConsumptionRecordFilePath() {
        return consumptionRecordFilePath;
    }

    /**
     * Obtains the json file path for the file representing the goal set by the user.
     * This path stores all relevant goal data in a certain format.
     *
     * @return The goal's json file path.
     */
    public Path getGoalFilePath() {
        return goalFilePath;
    }

    /**
     * Checks for equivalence between UserPrefs.
     *
     * @param other The other user preferences we wish to compare with.
     * @return Whether the two UserPrefs are equivalent.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) {
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && foodRecordFilePath.equals(o.foodRecordFilePath);
    }

    /**
     * Obtains hashcode for the UserPrefs object.
     *
     * @return The supposedly unique hashcode for the UserPrefs object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, foodRecordFilePath);
    }

    /**
     * Obtains String representation of the UserPrefs object, specifying the appropriate file paths and data.
     * @return The String representation of the UserPrefs object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + foodRecordFilePath);
        return sb.toString();
    }

}
