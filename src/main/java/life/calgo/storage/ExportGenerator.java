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
 */
public class ExportGenerator extends DocumentGenerator {
    private static final String PATH_NAME = "data/exports/FoodRecord.txt";
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
     * Formats and details the current Food Record into a txt file.
     *
     * @return a boolean value that is true only if FoodRecord.txt is successfully generated.
     */
    public boolean generateExport() {
        printSeparator();
        printSeparator();
        printHeader();
        printSeparator();
        printSeparator();

        printFoodRecordEntirely();

        printSeparator();
        printFooter();
        printSeparator();

        printWriter.close();

        return file.exists() && (file.length() != 0); // success check
    }

    // Printing Methods

    /**
     * Writes the header of the document.
     */
    @Override
    public void printHeader() {
        String title = centraliseText("Your Food Record: A Collection of Your Past Entries", WIDTH_OF_DOCUMENT);
        printWriter.println(title);
    }

    /**
     * Writes the categories of the nutritional information of each Food in the Food Record.
     */
    private void printCategories() {
        printWriter.println(String.format(STRING_FORMAT, "Name", "Calories",
                "Protein(g)", "Carbohydrates(g)", "Fat(g)", "Tags: "));
    }

    /**
     * Writes the entire current Food Record into the FoodRecord.txt.
     */
    public void printFoodRecordEntirely() {
        printCategories();
        printSeparator();

        ObservableList<Food> sourceFoodRecord = foodRecord.getFoodList();
        for (Food food : sourceFoodRecord) {
            String processedString = generateFinalisedEntryString(food);
            printWriter.println(processedString);
        }

    }

    /**
     * Writes the concluding statement of the document.
     */
    @Override
    public void printFooter() {
        printWriter.println(centraliseText("Eat Good, Live Well!", WIDTH_OF_DOCUMENT));
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

        if (name.toString().length() <= NAME_COLUMN_SIZE) {
            return generateFirstLine(name, calorie, protein, carbohydrate, fat, tags);
        } else {
            // to generate first line preview to suit column format of size NAME_COLUMN_SIZE
            String nameString = name.toString();
            String truncatedNameString = nameString.substring(0, NAME_COLUMN_SIZE);

            String firstLine = generateFirstLine(new Name(truncatedNameString), calorie, protein,
                    carbohydrate, fat, tags);

            // keep truncating until the end with newline generated each time
            String remainderLines = generateRemainderPartName(nameString);

            return firstLine + remainderLines;
        }
    }

    /**
     * Generates the first line of the String representing the Food with all its nutritional details.
     * Names too long should be truncated onto the next line and uses {@link #generateRemainderPartName(String)}.
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
     * Obtains the remainder part of the Name that does not appear in the same line as the nutritional details.
     *
     * @param fullName the String representing the full name of the Food.
     * @return the remainder part of the Name not previously shown.
     */
    private String generateRemainderPartName(String fullName) {
        // first 45 already taken
        String result = "\n";
        String workablePart = fullName.substring(NAME_COLUMN_SIZE);
        while (workablePart.length() >= NAME_COLUMN_SIZE) {
            result += workablePart.substring(0, NAME_COLUMN_SIZE) + "\n";
            workablePart = workablePart.substring(NAME_COLUMN_SIZE);
            System.out.println(workablePart);
        }
        result += workablePart;

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


}
