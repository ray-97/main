package life.calgo.storage;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import life.calgo.commons.core.LogsCenter;
import life.calgo.model.ReadOnlyConsumptionRecord;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.day.DailyGoal;
import life.calgo.model.food.Food;

/**
 * Responsible for generating statistics of the user's consumption patterns on a given day.
 */
public class ReportGenerator extends DocumentGenerator {
    public static final int VALUE_COLUMN_WIDTH = 25;
    public static final int NAME_COLUMN_WIDTH = 50;

    // for Header
    private static final String HEADER_MESSAGE = "Report of Consumption Pattern on %tF";
    // for Goal Information section
    private static final String GOAL_HEADER_MESSAGE = "Your Goal Information";
    private static final String NO_GOAL_MESSAGE = "You did not set any goal for daily caloric intake yet."
            + " If you want to generate personalised insights, please set one!";
    private static final String GOAL_MESSAGE = "You have set a goal to consume at most %d calories in a day.";
    // for Foodwise Statistics
    private static final String FOODWISE_HEADER_MESSAGE = "Food-wise Statistics";
    // for Aggregrate Statistics
    private static final String AGGREGRATE_HEADER_MESSAGE = "Aggregrate Statistics";
    // for Insights
    private static final String INSIGHTS_HEADER_MESSAGE = "Insights for You";
    private static final String GOAL_ACHIEVED_MESSAGE = "You have achieved your goal! Congratulations. "
            + "Keep up the great work and you will definitely make tremendous \n"
            + "improvements in your health and fitness.";
    private static final String GOAL_FAILED_MESSAGE = "You did not manage to achieve your goal today. "
            + "You may want to re-design your diet plan so that\n"
            + "you can make improvements in your health and fitness!";
    private static final String GOAL_SURPLUS_MESSAGE = "You have consumed %.0f fewer calories than your target. "
            + "Great job!";
    private static final String GOAL_DEFICIT_MESSAGE = "You have consumed %.0f more calories than your target. "
            + "Don't lose heart. You can do better!";
    // for Footer
    private static final String FOOTER_MESSAGE = "This marks the end of your report. Personalised insights coming up"
            + " in v1.4.";

    private LocalDate queryDate;
    private DailyFoodLog queryLog;
    private HashMap<LocalDate, DailyFoodLog> dateToLogMap;
    private double totalCalories = 0.0;
    private double totalProteins = 0.0;
    private double totalCarbs = 0.0;
    private double totalFats = 0.0;
    private DailyGoal userGoal;

    public ReportGenerator(LocalDate queryDate, DailyGoal userGoal, ReadOnlyConsumptionRecord consumptionRecord) {
        super("data/reports/" + queryDate.toString() + "_report.txt",
                LogsCenter.getLogger(ReportGenerator.class));
        this.dateToLogMap = consumptionRecord.getDateToLogMap();
        this.queryLog = this.dateToLogMap.get(queryDate);
        this.queryDate = queryDate;
        this.userGoal = userGoal;
    }

    /**
     * Driver method for generation of comprehensive report of consumption patterns
     * @return a boolean value that is true only if report has been successfully generated
     */
    public boolean generateReport() {
        printHeader();
        printGoalInformation();
        printFoodwiseStatistics();
        printAggregateStatistics();
        printInsights();
        // printSuggestions();
        printFooter();
        printWriter.close();
        return file.exists() && (file.length() != 0); // success check
    }

    /**
     * Writes the meta-information of the report.
     */
    @Override
    public void printHeader() {
        printWriter.println(centraliseText(String.format(HEADER_MESSAGE, this.queryLog.getLocalDate()),
                WIDTH_OF_DOCUMENT));
        printSeparator();
    }

    /**
     * Prints information on what the goal is.
     */
    public void printGoalInformation() {
        printWriter.println(centraliseText(GOAL_HEADER_MESSAGE, WIDTH_OF_DOCUMENT));
        printEmptyLine();
        String goalInformation;
        if (userGoal.getTargetDailyCalories() == DailyGoal.DUMMY_VALUE) {
            goalInformation = NO_GOAL_MESSAGE;
        } else {
            goalInformation = String.format(GOAL_MESSAGE, this.userGoal.getTargetDailyCalories());
        }
        printWriter.println(goalInformation);
        printSeparator();
    }

    /**
     * Writes relevant statistics related to each food quantity consumed in the given day.
     */
    public void printFoodwiseStatistics() {
        printWriter.println(centraliseText(FOODWISE_HEADER_MESSAGE, WIDTH_OF_DOCUMENT));
        printEmptyLine();

        DailyFoodLog foodLog = queryLog;
        String columnInterval = "|";
        String foodHeader = centraliseText("Food", NAME_COLUMN_WIDTH) + columnInterval;
        String portionHeader = centraliseText("Total Quantity", VALUE_COLUMN_WIDTH) + columnInterval;
        String caloriesHeader = centraliseText("Total Calories", VALUE_COLUMN_WIDTH);
        printWriter.println(foodHeader + portionHeader + caloriesHeader);
        printEmptyLine();

        for (Food food : foodLog.getFoods()) {
            double portion = foodLog.getPortion(food);
            double currCalories = portion * (double) Integer.parseInt(food.getCalorie().value);
            totalCalories += currCalories;
            totalProteins += portion * (double) Integer.parseInt(food.getProtein().value);
            totalCarbs += portion * (double) Integer.parseInt(food.getCarbohydrate().value);
            totalFats += portion * (double) Integer.parseInt(food.getFat().value);
            String foodColumn = stringWrap(food.getFoodNameString(), NAME_COLUMN_WIDTH);
            String portionColumn = stringWrap(String.format("%.1f", portion), VALUE_COLUMN_WIDTH);
            String currCaloriesColumn = stringWrap(String.format("%.0f", currCalories), VALUE_COLUMN_WIDTH);
            ArrayList<String> columns = new ArrayList<>();
            columns.add(foodColumn);
            columns.add(portionColumn);
            columns.add(currCaloriesColumn);
            String table = combineColumns(columns, 5);
            printWriter.println(table);
        }
        printSeparator();
    }

    /**
     * Writes aggregated statistics of all food items consumed in the given day.
     */
    public void printAggregateStatistics() {
        printWriter.println(centraliseText(AGGREGRATE_HEADER_MESSAGE, WIDTH_OF_DOCUMENT));
        printEmptyLine();
        printWriter.println(String.format("%s %-20s %-20s %-20s", "Total Calories in kcal", "| Total Protein in grams",
                "| Total Carbohydrates in grams", "| Total Fats in grams"));
        printWriter.println(String.format("     %-25.0f %-26.0f %-28.0f %.0f", totalCalories, totalProteins,
                totalCarbs, totalFats));
        printSeparator();
    }

    /**
     * Writes the actionable insights a user can take based on user consumption patterns for the given day.
     */
    public void printInsights() {
        // compare method returns -1 if left argument < right argument and 0 if left argument == right argument
        if (userGoal.getTargetDailyCalories() != DailyGoal.DUMMY_VALUE) {
            boolean isGoalAchieved = (int) calculateRemainingCalories() >= 0;
            printWriter.println(centraliseText(INSIGHTS_HEADER_MESSAGE, WIDTH_OF_DOCUMENT));
            printEmptyLine();
            if (isGoalAchieved) {
                printWriter.println(GOAL_ACHIEVED_MESSAGE);
                printEmptyLine();
                printWriter.println(String.format(GOAL_SURPLUS_MESSAGE, calculateRemainingCalories()));
            } else {
                printWriter.println(GOAL_FAILED_MESSAGE);
                printEmptyLine();
                printWriter.println(String.format(GOAL_DEFICIT_MESSAGE, Math.abs(calculateRemainingCalories())));
            }
            printSeparator();
        }
    }

    /**
     * Creates a list of recommended food items to eat that will match goal of user.
     */
    public void printSuggestions() {
        // instantiate a list with past 7 days of consumed food data
        ArrayList<DailyFoodLog> dailyFoodLogs = new ArrayList<>();
        LocalDate currentDate = this.queryDate;
        for (int i = 1; i <= 7; i++) {
            if (dateToLogMap.containsKey(currentDate)) {
                dailyFoodLogs.add(this.dateToLogMap.get(currentDate));
            }
            currentDate = currentDate.minus(Period.ofDays(1));
        }
        // flatten list
        ArrayList<Food> foodInPastWeek = new ArrayList<>();
        for (DailyFoodLog foodLog : dailyFoodLogs) {
            foodInPastWeek.addAll(foodLog.getFoods());
        }

        HashMap<Food, Integer> frequencyMap = new HashMap<>();
        for (Food food : foodInPastWeek) {
            if (frequencyMap.containsKey(food)) {
                frequencyMap.put(food, frequencyMap.get(food) + 1);
            } else {
                frequencyMap.put(food, 1);
            }
        }

        Collections.sort(foodInPastWeek, Comparator.comparingInt(frequencyMap::get));

        // ensure sum up to goal calories
        printWriter.println(centraliseText("Suggestions", WIDTH_OF_DOCUMENT));
        double goal = userGoal.getTargetDailyCalories();
        while (goal >= 0 && !foodInPastWeek.isEmpty()) {
            Food consumedFood = foodInPastWeek.remove(0);
            printWriter.println(consumedFood);
            goal -= Double.parseDouble(consumedFood.getCalorie().toString());
        }
    }

    /**
     * Writes the concluding remarks in the report.
     */
    @Override
    public void printFooter() {
        printWriter.println(centraliseText(FOOTER_MESSAGE, WIDTH_OF_DOCUMENT));
    }


    /**
     * Calculates number of calories remaining for user to meet goal.
     * @return the number of calories remaining for user to meet goal
     */
    public double calculateRemainingCalories() {
        return userGoal.getTargetDailyCalories() - totalCalories;
    }
}
