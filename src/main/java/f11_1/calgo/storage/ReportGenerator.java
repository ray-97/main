package f11_1.calgo.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import f11_1.calgo.commons.core.LogsCenter;
import f11_1.calgo.model.day.DailyFoodLog;
import f11_1.calgo.model.day.Day;
import f11_1.calgo.model.food.Food;

/**
 * Responsible for generating aggregated statistics of the user's consumption patterns on a given day.
 */
public class ReportGenerator {
    private static final Logger logger = LogsCenter.getLogger(ReportGenerator.class);
    private Day queryDay;
    private File file;
    private PrintWriter printWriter;
    private double totalCalories = 0.0;
    private double totalProteins = 0.0;
    private double totalCarbs = 0.0;
    private double totalFats = 0.0;

    public ReportGenerator(Day queryDay) {
        this.queryDay = queryDay;
        this.file = new File(queryDay.toString() + "_report.txt");
        try {
            this.printWriter = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            // happens when there is an error in opening or creating the file
            logger.warning("Not able to generate report because file was unable to be created.");
        } catch (Exception e) {
            logger.warning("Check your system security settings and enable rights to create a new file.");
        }
    }

    public boolean generateReport() {
        printHeader();
        printSeparator();
        printFoodwiseStatistics();
        printSeparator();
        printAggregateStatistics();
        printSeparator();
        printFooter();
        printWriter.close();
        return file.exists() && (file.length() != 0); // success check
    }


    public void printHeader() {
        String title = "Report of Consumption Pattern on " + this.queryDay.getLocalDate().toString();
        printWriter.println(title);
    }

    public void printFoodwiseStatistics() {
        printWriter.println("Food-wise Statistics:");
        printWriter.println(String.format("%-20s %-20s %-20s", "    Food", "   Quantity", "   Calories"));
        DailyFoodLog foodLog = queryDay.getDailyFoodLog();
        for (Food food : foodLog.getFoods().keySet()) {
            double portion = foodLog.getPortion(food);
            double currCalories = portion * (double) Integer.parseInt(food.getCalorie().value);
            totalCalories += currCalories;
            totalProteins += portion * (double) Integer.parseInt(food.getProtein().value);
            totalCarbs += portion * (double) Integer.parseInt(food.getCarbohydrate().value);
            totalFats += portion * (double) Integer.parseInt(food.getFat().value);
            printWriter.println(String.format("%-20s %-20f %-20f", food.toString(), portion, currCalories));
        }
    }

    public void printAggregateStatistics() {
        printWriter.println("Aggregate Statistics:");
        printWriter.println(String.format("%s %-20s %-20s %-20s", "Total Calories in kcal", "| Total Protein in grams"
                , "| Total Carbohydrates in grams", "| Total Fats in grams"));
        printWriter.println(String.format("     %-25f %-26f %-28f %f", totalCalories, totalProteins,
                totalCarbs, totalFats));
    }

    public void printFooter() {
        printWriter.println("This marks the end of your report. More insights coming up in v1.3.");
    }

    public void printSeparator() {
        printWriter.println("--------------------------------------------------------------------------------"
                + "-----------------------------");
    }
}
