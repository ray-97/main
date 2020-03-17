package f11_1.calgo.model;

import static java.util.Objects.requireNonNull;
import static f11_1.calgo.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import f11_1.calgo.commons.core.GuiSettings;
import f11_1.calgo.commons.core.LogsCenter;
import f11_1.calgo.model.food.Food;

/**
 * Represents the in-memory model of the food record data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final FoodRecord foodRecord;
    private final UserPrefs userPrefs;
    private final FilteredList<Food> filteredFoods;

    /**
     * Initializes a ModelManager with the given foodRecord and userPrefs.
     */
    public ModelManager(ReadOnlyFoodRecord readOnlyFoodRecord, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(readOnlyFoodRecord, userPrefs);

        logger.fine("Initializing with food record: " + readOnlyFoodRecord + " and user prefs " + userPrefs);

        this.foodRecord = new FoodRecord(readOnlyFoodRecord);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredFoods = new FilteredList<>(this.foodRecord.getFoodList());
    }

    public ModelManager() {
        this(new FoodRecord(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getFoodRecordFilePath() {
        return userPrefs.getFoodRecordFilePath();
    }

    @Override
    public void setFoodRecordFilePath(Path foodRecordFilePath) {
        requireNonNull(foodRecordFilePath);
        userPrefs.setFoodRecordFilePath(foodRecordFilePath);
    }

    //=========== FoodRecord ================================================================================

    @Override
    public ReadOnlyFoodRecord getFoodRecord() {
        return foodRecord;
    }

    @Override
    public void setFoodRecord(ReadOnlyFoodRecord foodRecord) {
        this.foodRecord.resetData(foodRecord);
    }

    @Override
    public boolean hasFood(Food food) {
        requireNonNull(food);
        return foodRecord.hasFood(food);
    }

    public Food getExistingFood(Food toGet) {
        requireNonNull(toGet);
        return foodRecord.getExistingFood(toGet);
    }

    @Override
    public void deleteFood(Food target) {
        foodRecord.removeFood(target);
    }

    @Override
    public void addFood(Food food) {
        foodRecord.addFood(food);
        updateFilteredFoodRecord(PREDICATE_SHOW_ALL_FOODS);
    }

    @Override
    public void setFood(Food target, Food editedFood) {
        requireAllNonNull(target, editedFood);

        foodRecord.setFood(target, editedFood);
    }

    //=========== Filtered Food Record Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Food} backed by the internal list of
     * {@code versionedFoodRecord}
     */
    @Override
    public ObservableList<Food> getFilteredFoodRecord() {
        return filteredFoods;
    }

    @Override
    public void updateFilteredFoodRecord(Predicate<Food> predicate) {
        requireNonNull(predicate);
        filteredFoods.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return foodRecord.equals(other.foodRecord)
                && userPrefs.equals(other.userPrefs)
                && filteredFoods.equals(other.filteredFoods);
    }

}
