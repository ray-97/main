package life.calgo.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.logging.Logger;

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
 * Responsible for generating a user-friendly copy of the current FoodRecord.
 * All Food entries will have all their details written into the file.
 */
public class ExportGenerator {
    private static final Logger logger = LogsCenter.getLogger(ExportGenerator.class);
    private File file;
    private PrintWriter printWriter;
    private ReadOnlyFoodRecord foodRecord;

    public ExportGenerator(ReadOnlyFoodRecord foodRecord) {
        this.foodRecord = foodRecord;
        this.file = new File("exports/FoodRecord.txt");
        try {
            this.file.getParentFile().mkdirs();
            this.file.createNewFile();
            this.printWriter = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            // happens when there is an error in opening or creating the file
            logger.warning("Not able to generate Food Record export because file was unable to be created.");
        } catch (Exception e) {
            logger.warning("Check your system security settings and enable rights to create a new file.");
        }
    }

    /**
     * Driver method for generation of the Food Record.
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
        printWriter.close();
        return file.exists() && (file.length() != 0); // success check
    }

    /**
     * Writes the header of the document.
     */
    public void printHeader() {
        String title = "Your Food Record: A Collection of Your Past Entries";
        printWriter.println(title);
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
     * Writes a line for neatness in formatting.
     */
    private void printSeparator() {
        printWriter.println("--------------------------------------------------------------------------------"
                + "------------------------------------------------------------------");
    }

    /**
     * Writes the categories of details of each Food in the Food Record.
     */
    private void printCategories() {
        printWriter.println(String.format("%-45s %-20s %-20s %-20s %-20s %-20s", "Name", "Calories",
                "Protein(g)", "Carbohydrates(g)", "Fat(g)", "Tags: "));
    }

}
