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
    private ReadOnlyFoodRecord foodRecord;

    public ExportGenerator(ReadOnlyFoodRecord foodRecord) {
        super(PATH_NAME, LogsCenter.getLogger(ExportGenerator.class));
        this.foodRecord = foodRecord;
    }

    /**
     * Formats and details the current Food Record into a txt file.
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

    /**
     * Writes the header of the document.
     */
    @Override
    public void printHeader() {
        String title = centraliseText("Your Food Record: A Collection of Your Past Entries");
        printWriter.println(title);
    }

    /**
     * Writes the concluding statement of the document.
     */
    @Override
    public void printFooter() {
        printWriter.println(centraliseText("Eat Good, Live Well!"));
    }

    /**
     * Writes the entire current Food Record into the FoodRecord.txt.
     */
    public void printFoodRecordEntirely() {
        printCategories();
        printSeparator();

        ObservableList<Food> sourceFoodRecord = foodRecord.getFoodList();
        for (Food food : sourceFoodRecord) {

            Name name = food.getName();
            Calorie calories = food.getCalorie();
            Protein protein = food.getProtein();
            Carbohydrate carbohydrate = food.getCarbohydrate();
            Fat fat = food.getFat();
            Set<Tag> tags = food.getTags();

            printWriter.println(String.format("%-45s %-20s %-20s %-20s %-20s %-20s",
                    name, calories, protein, carbohydrate, fat, accumulatedTagsString(tags)));

        }
    }

    /**
     * Accumulates all the Tags into a space-separated String and returns this String.
     *
     * @param tags the tags to be converted into String representation.
     * @return the space-separated String of all the tags given.
     */
    private String accumulatedTagsString(Set<Tag> tags) {
        String result = "";
        for (Tag tag: tags) {
            result += tag + " ";
        }
        return result;
    }

    /**
     * Writes the categories of the nutritional information of each Food in the Food Record.
     */
    private void printCategories() {
        printWriter.println(String.format("%-45s %-20s %-20s %-20s %-20s %-20s", "Name", "Calories",
                "Protein(g)", "Carbohydrates(g)", "Fat(g)", "Tags: "));
    }
}
