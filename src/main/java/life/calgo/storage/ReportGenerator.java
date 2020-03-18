package life.calgo.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import life.calgo.commons.core.LogsCenter;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.day.DailyGoal;
import life.calgo.model.food.Food;

/**
 * Responsible for generating statistics of the user's consumption patterns on a given day.
 */
public class ReportGenerator {
    private static final Logger logger = LogsCenter.getLogger(ReportGenerator.class);
    private DailyFoodLog queryLog; //changes here
    private File file;
    private PrintWriter printWriter;
    private double totalCalories = 0.0;
    private double totalProteins = 0.0;
    private double totalCarbs = 0.0;
    private double totalFats = 0.0;
    private DailyGoal userGoal;

    public ReportGenerator(DailyFoodLog queryLog, DailyGoal userGoal) {
        this.queryLog = queryLog;
        this.userGoal = userGoal;
        this.file = new File("reports/" + queryLog.getLocalDate().toString() + "_report.txt");
        try {
            this.file.getParentFile().mkdirs();
            this.file.createNewFile();
            this.printWriter = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            // happens when there is an error in opening or creating the file
            logger.warning("Not able to generate report because file was unable to be created.");
        } catch (Exception e) {
            logger.warning("Check your system security settings and enable rights to create a new file.");
        }
    }

    /**
     * Driver method for generation of comprehensive report of consumption patterns
     * @return a boolean value that is true only if report has been successfully generated
     */
    public boolean generateReport() {
        printHeader();
        printSeparator();
        printFoodwiseStatistics();
        printSeparator();
        printAggregateStatistics();
        printSeparator();
        // if goal exists
        if (this.userGoal != null) {
            printInsights();
            printSeparator();
        }
        printFooter();
        printWriter.close();
        return file.exists() && (file.length() != 0); // success check
    }

    /**
     * Writes the meta-information of the report
     */
    public void printHeader() {
        String title = "Report of Consumption Pattern on " + this.queryLog.getLocalDate().toString();
        // assert title != null : "Title is null"; or System.out.println(printWriter == null);
        printWriter.println(title);
        if (userGoal == null) {
            printWriter.println("You did not set any goal for daily caloric intake yet.");
        } else {
            String userGoal = "You have set a goal to consume at most " + this.userGoal.getTargetDailyCalories()
                    + " calories in a day.";
            printWriter.println(userGoal);
        }
    }

    /**
     * Writes relevant statistics related to each food quantity consumed in the given day
     */
    public void printFoodwiseStatistics() {
        printWriter.println("Food-wise Statistics:");
        printWriter.println(String.format("%-20s %-20s %-20s", "    Food", "   Quantity", "   Calories"));
        DailyFoodLog foodLog = queryLog; //changes here
        for (Food food : foodLog.getFoods()) {
            double portion = foodLog.getPortion(food);
            double currCalories = portion * (double) Integer.parseInt(food.getCalorie().value);
            totalCalories += currCalories;
            totalProteins += portion * (double) Integer.parseInt(food.getProtein().value);
            totalCarbs += portion * (double) Integer.parseInt(food.getCarbohydrate().value);
            totalFats += portion * (double) Integer.parseInt(food.getFat().value);
            printWriter.println(String.format("   %-22s %-20.0f %-20.0f", food.toString(true),
                    portion, currCalories));
        }
    }

    /**
     * Writes aggregated statistics of all food items consumed in the given day
     */
    public void printAggregateStatistics() {
        printWriter.println("Aggregate Statistics:");
        printWriter.println(String.format("%s %-20s %-20s %-20s", "Total Calories in kcal", "| Total Protein in grams",
                "| Total Carbohydrates in grams", "| Total Fats in grams"));
        printWriter.println(String.format("     %-25.0f %-26.0f %-28.0f %.0f", totalCalories, totalProteins,
                totalCarbs, totalFats));
    }

    /**
     * Writes the actionable insights a user can take based on user consumption patterns for the given day
     */
    public void printInsights() {
        // compare method returns -1 if left argument < right argument and 0 if left argument == right argument
        boolean isGoalAchieved = Double.compare(this.totalCalories, this.userGoal.getTargetDailyCalories()) <= 0;
        if (isGoalAchieved) {
            printWriter.println("You have achieved your goal! Congratulations. Keep up the great work"
                    + " and you will definitely\n"
                    + "make tremendous improvements in your health and fitness.");
        } else {
            printWriter.println("You did not manage to achieve your goal today. You may want to re-design"
                    + " your diet plan so that\n"
                    + "you can make improvements in your health and fitness!");
        }
    }

    /**
     * Writes the concluding remarks in the report
     */
    public void printFooter() {
        printWriter.println("This marks the end of your report. Actionable insights coming up in v1.3.");
    }

    /**
     * Writes a line break
     */
    public void printSeparator() {
        printWriter.println("--------------------------------------------------------------------------------"
                + "-----------------------------");
    }
}
