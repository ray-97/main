package life.calgo.storage;

import java.util.Set;

import javafx.collections.ObservableList;
import life.calgo.commons.core.LogsCenter;
import life.calgo.model.ReadOnlyFoodRecord;
import life.calgo.model.food.Calorie;
import life.calgo.model.food.Carbohydrate;
import life.calgo.model.food.Fat;
import life.calgo.model.food.Food;
import life.calgo.model.food.Name;
import life.calgo.model.food.Protein;
import life.calgo.model.tag.Tag;

/**
 * Generating a user-friendly and editable copy of the current FoodRecord.
 * All Food entries will have all their details written into the file.
 * Names that are too long will be truncated onto the next line, with nutritional details on the first line.
 * Tags are outside of the dashed lines as users may have a large number of them.
 * Tags are informal and hence less significant, so they should not be within the dashed lines.
 */
public class ExportGenerator extends DocumentGenerator {

    private static final String PATH_NAME = "data/exports/FoodRecord.txt";

    // Formatting

    private static final int NAME_COLUMN_SIZE = 45;
    private static final int VALUE_COLUMN_SIZE = 20;
    private static final String STRING_FORMAT = "%-" + NAME_COLUMN_SIZE + "s "
            + "%-" + VALUE_COLUMN_SIZE + "s "
            + "%-" + VALUE_COLUMN_SIZE + "s "
            + "%-" + VALUE_COLUMN_SIZE + "s "
            + "%-" + VALUE_COLUMN_SIZE + "s "
            + "%-" + VALUE_COLUMN_SIZE + "s";

    private ReadOnlyFoodRecord foodRecord;

    public ExportGenerator(ReadOnlyFoodRecord foodRecord) {
        super(PATH_NAME, LogsCenter.getLogger(ExportGenerator.class));
        this.foodRecord = foodRecord;
    }

    /**
     * Formats and details the current Food Record into a txt file, then returns true if successful.
     *
     * @return a boolean value that is true only if FoodRecord.txt is successfully generated.
     */
    public boolean generateExport() {

        printHeader();
        printBody();
        printFooter();

        printWriter.close();

        return file.exists() && (file.length() != 0); // success check

    }

    // Printing Methods

    /**
     * Writes the header of the document.
     */
    @Override
    protected void printHeader() {

        printSeparator();
        printSeparator();

        printHeaderComponent();

        printSeparator();
        printSeparator();

    }

    /**
     * Writes the entire current Food Record into the FoodRecord.txt.
     */
    @Override
    protected void printBody() {

        printCategoriesComponent(); // categories are part of the body to indicate the table format visually to user

        printSeparator();

        printBodyComponent();

    }

    /**
     * Writes the concluding statement of the document.
     */
    @Override
    protected void printFooter() {

        printSeparator();

        printFooterComponent();

        printSeparator();

    }

    /**
     * Writes the main part of the header.
     */
    private void printHeaderComponent() {
        String title = centraliseText("Your Food Record: A Collection of Your Past Entries", DOCUMENT_WIDTH);
        printWriter.println(title);
    }

    /**
     * Writes the main part of the body.
     */
    private void printBodyComponent() {
        ObservableList<Food> sourceFoodRecord = foodRecord.getFoodList();
        for (Food food : sourceFoodRecord) {
            String processedString = generateFinalisedEntryString(food);
            printWriter.println(processedString);
        }
    }

    /**
     * Writes the main part of the footer.
     */
    private void printFooterComponent() {
        String footer = centraliseText("Eat Good, Live Well!", DOCUMENT_WIDTH);
        printWriter.println(footer);
    }

    /**
     * Writes the categories of the nutritional information of each Food in the Food Record.
     */
    private void printCategoriesComponent() {
        String categories = String.format(
                STRING_FORMAT, "Name", "Calories", "Protein(g)", "Carbohydrates(g)", "Fat(g)", "Tags: ");
        printWriter.println(categories);
    }

    // String Manipulation Methods

    /**
     * Generates the full String representing the Food with all its nutritional details.
     * Names too long are truncated onto the next line.
     *
     * @param food the Food of interest.
     * @return the String representation for the Food entry.
     */
    private String generateFinalisedEntryString(Food food) {

        Name name = food.getName();
        Calorie calorie = food.getCalorie();
        Protein protein = food.getProtein();
        Carbohydrate carbohydrate = food.getCarbohydrate();
        Fat fat = food.getFat();
        Set<Tag> tags = food.getTags();

        if (hasAcceptableLength(name, NAME_COLUMN_SIZE)) {

            return generateFirstLine(name, calorie, protein, carbohydrate, fat, tags);

        } else {

            Name truncatedName = getTruncatedName(name, NAME_COLUMN_SIZE);
            String firstLine = generateFirstLine(truncatedName, calorie, protein, carbohydrate, fat, tags);
            Name untruncatedName = getUntruncatedName(name, NAME_COLUMN_SIZE);
            String remainderLines = generateRemainderLines(untruncatedName, NAME_COLUMN_SIZE);

            return firstLine + remainderLines;

        }

    }

    /**
     * Generates the first line of the String representing the Food with all its nutritional details.
     * Names too long should be truncated onto the next line and
     * uses {@link #generateRemainderLines(Name, int)}.
     *
     * @param name the Name of the Food.
     * @param calorie the Calorie of the Food.
     * @param protein the Protein of the Food.
     * @param carbohydrate the Carbohydrate of the Food.
     * @param fat the Fat of the Food.
     * @param tags the Set of Tags of the Food.
     * @return the first and possibly only line of the String representing the Food with all its nutritional details.
     */
    private String generateFirstLine(Name name, Calorie calorie, Protein protein,
                                     Carbohydrate carbohydrate, Fat fat, Set<Tag> tags) {
        return String.format(STRING_FORMAT,
                name, calorie, protein, carbohydrate, fat, generateAccumulatedTagsString(tags));
    }

    /**
     * Obtains the equivalent of the remainder part of the Name that does not appear in the same line
     * as the nutritional details.
     *
     * @param remainder the Name representing the untruncated part of the name of the Food.
     * @param width the maximum allowed width of the name segment.
     * @return the remainder part of the Name not previously shown.
     */
    private String generateRemainderLines(Name remainder, int width) {

        String result = "\n"; // adding to the truncated front part, hence a newline is needed

        // Wraps the remaining part of the Name so that it suits the required formatting
        result += super.generateWrappedNameString(remainder, width);

        return result;

    }

    /**
     * Accumulates all the Tags into a space-separated String and returns this String.
     *
     * @param tags the tags to be converted into String representation.
     * @return the space-separated String of all the tags given.
     */
    private String generateAccumulatedTagsString(Set<Tag> tags) {
        String result = "";
        for (Tag tag: tags) {
            result += tag + " ";
        }
        return result;
    }

    // Utility Methods

    /**
     * Gets a new Name object containing the truncated full name of the original Name object.
     *
     * @param name the original Name object we wish to truncate from.
     */
    private Name getTruncatedName(Name name, int truncateLength) {
        String nameString = getNameString(name);
        String truncatedNameString = nameString.substring(0, truncateLength);
        return new Name(truncatedNameString);
    }

    /**
     * Gets a new Name object containing the untruncated part of the name of the original Name object.
     * This is complementary to the {@link #getTruncatedName(Name, int)} method.
     *
     * @param name the original Name object we wish to truncate from.
     */
    private Name getUntruncatedName(Name name, int truncateLength) {
        String nameString = getNameString(name);
        String truncatedNameString = nameString.substring(truncateLength);
        return new Name(truncatedNameString);
    }

    /**
     * Checks whether the current Name contains a String within the acceptable length for the visual format.
     * Similar to {@link #hasAcceptableLength(String, int)}, but now takes in a Name rather than a String.
     *
     * @param name the current Name to check.
     * @param length the acceptable length.
     * @return whether the current Name is within acceptable limits.
     */
    private boolean hasAcceptableLength(Name name, int length) {
        return (getNameString(name).length() <= length);
    }

}
