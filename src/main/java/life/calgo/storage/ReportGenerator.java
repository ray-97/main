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

    // Formatting

    private static final int NAME_COLUMN_WIDTH = DOCUMENT_WIDTH / 2;
    private static final int VALUE_COLUMN_WIDTH = DOCUMENT_WIDTH / 4; // there are at most 4 value columns in report.

    // Messages

    // for Header
    private static final String HEADER_MESSAGE = "Report of Consumption Pattern on %tF";

    // for Goal Information section
    private static final String GOAL_HEADER_MESSAGE = "Your Goal Information";

    private static final String NO_GOAL_MESSAGE = "You did not set any goal for daily caloric intake yet."
            + " If you want to generate personalised insights, please set one!";

    private static final String GOAL_MESSAGE = "You have set a goal to consume at most %d calories in a day.";

    // for Foodwise Statistics
    private static final String FOODWISE_HEADER_MESSAGE = "Food-wise Statistics";

    // for Aggregate Statistics
    private static final String AGGREGATE_HEADER_MESSAGE = "Aggregate Statistics";

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

    // Attributes

    private HashMap<LocalDate, DailyFoodLog> dateToLogMap;
    private DailyFoodLog queryLog;
    private LocalDate queryDate;
    private DailyGoal userGoal;

    // Statistics

    private double totalCalories = 0.0;
    private double totalProteins = 0.0;
    private double totalCarbs = 0.0;
    private double totalFats = 0.0;

    public ReportGenerator(LocalDate queryDate, DailyGoal userGoal, ReadOnlyConsumptionRecord consumptionRecord) {
        super("data/reports/" + queryDate.toString() + "_report.txt",
                LogsCenter.getLogger(ReportGenerator.class));

        this.dateToLogMap = consumptionRecord.getDateToLogMap();
        this.queryLog = this.dateToLogMap.get(queryDate);
        this.queryDate = queryDate;
        this.userGoal = userGoal;
    }

    /**
     * Driver method for generation of comprehensive report of consumption patterns.
     *
     * @return a boolean value that is true only if report has been successfully generated.
     */
    public boolean generateReport() {
        updateStatistics();
        printHeader();
        printBody();
        printFooter();
        printWriter.close();
        return file.exists() && (file.length() != 0); // success check
    }


    // Update Statistics Methods

    /**
     * Updates aggregate statistics based on DailyFoodLog attribute.
     */
    private void updateStatistics() {
        for (Food food : queryLog.getFoods()) {
            double portion = queryLog.getPortion(food);
            totalCalories += portion * (double) Integer.parseInt(food.getCalorie().value);
            totalProteins += portion * (double) Integer.parseInt(food.getProtein().value);
            totalCarbs += portion * (double) Integer.parseInt(food.getCarbohydrate().value);
            totalFats += portion * (double) Integer.parseInt(food.getFat().value);
        }
    }

    // Overriding Printing Methods

    /**
     * Writes the meta-information of the report.
     */
    @Override
    protected void printHeader() {
        printWriter.println(centraliseText(String.format(HEADER_MESSAGE, this.queryLog.getLocalDate()),
                DOCUMENT_WIDTH));
        printSeparator();
    }

    /**
     * Writes the body of the report document.
     */
    @Override
    protected void printBody() {
        printGoalInformation();
        printFoodwiseStatistics();
        printAggregateStatistics();
        printInsights();
        // printSuggestions();
    }

    /**
     * Writes the concluding remarks in the report.
     */
    @Override
    protected void printFooter() {
        printWriter.println(centraliseText(FOOTER_MESSAGE, DOCUMENT_WIDTH));
    }

    // ReportGenerator-Specific Printing Methods

    /**
     * Prints information on what the goal is.
     */
    private void printGoalInformation() {
        printGoalInformationHeader();
        printGoalInformationBody();
        printSeparator();
    }

    /**
     * Prints goal information section header.
     */
    private void printGoalInformationHeader() {
        printWriter.println(centraliseText(GOAL_HEADER_MESSAGE, DOCUMENT_WIDTH));
        printEmptyLine();
    }

    /**
     * Prints goal information section body.
     */
    private void printGoalInformationBody() {
        String goalInformation;
        if (userGoal.getTargetDailyCalories() == DailyGoal.DUMMY_VALUE) {
            goalInformation = NO_GOAL_MESSAGE;
        } else {
            goalInformation = String.format(GOAL_MESSAGE, this.userGoal.getTargetDailyCalories());
        }
        printWriter.println(goalInformation);
    }

    /**
     * Writes relevant statistics related to each food quantity consumed in the given day.
     */
    private void printFoodwiseStatistics() {
        printFoodwiseStatisticsHeader();
        printFoodwiseStatisticsTableHeader();
        printFoodwiseStatisticsTable();
        printSeparator();
    }

    /**
     * Prints the header of the Foodwise Statistics section.
     */
    private void printFoodwiseStatisticsHeader() {
        printWriter.println(centraliseText(FOODWISE_HEADER_MESSAGE, DOCUMENT_WIDTH));
        printEmptyLine();
    }

    /**
     * Prints the headers of the table in the Foodwise Statistics section.
     */
    private void printFoodwiseStatisticsTableHeader() {
        String columnInterval = "|";
        String foodHeader = centraliseText("Food", NAME_COLUMN_WIDTH) + columnInterval;
        String portionHeader = centraliseText("Total Quantity", VALUE_COLUMN_WIDTH) + columnInterval;
        String caloriesHeader = centraliseText("Total Calories", VALUE_COLUMN_WIDTH);
        printWriter.println(foodHeader + portionHeader + caloriesHeader);
        printEmptyLine();
    }

    /**
     * Prints the main information in a table of the Foodwise Statistics section.
     */
    private void printFoodwiseStatisticsTable() {
        for (Food food : queryLog.getFoods()) {
            double portion = queryLog.getPortion(food);
            double currCalories = portion * (double) Integer.parseInt(food.getCalorie().value);
            String foodColumn = stringWrap(food.getFoodNameString(), NAME_COLUMN_WIDTH);
            String portionColumn = stringWrap(String.format("%.1f", portion), VALUE_COLUMN_WIDTH);
            String currCaloriesColumn = stringWrap(String.format("%.0f", currCalories), VALUE_COLUMN_WIDTH);
            ArrayList<String> columns = new ArrayList<>();
            columns.add(foodColumn);
            columns.add(portionColumn);
            columns.add(currCaloriesColumn);
            String table = combineColumns(columns, VALUE_COLUMN_WIDTH, NAME_COLUMN_WIDTH);
            printWriter.println(table);
        }
    }

    /**
     * Writes aggregated statistics of all food items consumed in the given day.
     */
    private void printAggregateStatistics() {
        printWriter.println(centraliseText(AGGREGATE_HEADER_MESSAGE, DOCUMENT_WIDTH));
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
    private void printInsights() {
        // compare method returns -1 if left argument < right argument and 0 if left argument == right argument
        if (userGoal.getTargetDailyCalories() != DailyGoal.DUMMY_VALUE) {
            boolean isGoalAchieved = (int) calculateRemainingCalories() >= 0;
            printWriter.println(centraliseText(INSIGHTS_HEADER_MESSAGE, DOCUMENT_WIDTH));
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
    private void printSuggestions() {
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
        printWriter.println(centraliseText("Suggestions", DOCUMENT_WIDTH));
        double goal = userGoal.getTargetDailyCalories();
        while (goal >= 0 && !foodInPastWeek.isEmpty()) {
            Food consumedFood = foodInPastWeek.remove(0);
            printWriter.println(consumedFood);
            goal -= Double.parseDouble(consumedFood.getCalorie().toString());
        }
    }

    /**
     * Calculates number of calories remaining for user to meet goal.
     *
     * @return the number of calories remaining for user to meet goal
     */
    private double calculateRemainingCalories() {
        return userGoal.getTargetDailyCalories() - totalCalories;
    }


    // String Manipulation Methods

    /**
     * Wraps a string s into lines of n characters.
     *
     * @param s The string to be wrapped about.
     * @param n The number of characters allowed in a line.
     * @return The processed String after wrapping.
     */
    private String stringWrap(String s, int n) {
        StringBuilder result = new StringBuilder();


        for (int i = 0; i < s.length(); i++) {
            if (i != 0 && i % (n - 1) == 0) {
                result.append("\n");
            }
            result.append(s.charAt(i));
        }

        return result.toString();
    }




    /**
     * @param sb A client StringBuilder object.
     * @param columns An ArrayList of String Arrays (columns) that contain respective lines of data.
     * @param currLine The current line number that is being iterated through.
     * @param smallColumnWidth An Integer for the number of characters a small column should be.(For Numerical columns).
     * @param largeColumnWidth An Integer for the number of characters a large column should be. (For Name columns).
     */
    private void iterateColumn(StringBuilder sb, ArrayList<String[]> columns, int currLine, int smallColumnWidth,
                               int largeColumnWidth) {
        int numArrays = columns.size();
        for (int column = 0; column < numArrays; column++) {
            String[] currArray = columns.get(column);
            int columnWidth = smallColumnWidth;
            // first column is Food Name column
            if (column == 0) {
                columnWidth = largeColumnWidth;
            }

            // only first line of that column is centralised.
            // if column contains data that is spread across multiple lines, the other lines are not centralised.
            if (currLine == 0) {
                sb.append(centraliseText(currArray[currLine], columnWidth + 1));
            } else if (currLine < currArray.length) {
                String currString = currArray[currLine];
                sb.append(addNTrailingWhitespace(currString,
                        columnWidth - currString.length() + 1));
            } else {
                sb.append(addNTrailingWhitespace(" ", columnWidth + 1));
            }

            if (column == numArrays - 1) {
                sb.append("\n");
            }
        }
    }
    /**
     * Combines columns to form a table. Goes line by line.
     *
     * @param strings An ArrayList of Strings, where each element is a column.
     * @param smallColumnWidth An Integer for the number of characters a small column should be.(For Numerical columns).
     * @param largeColumnWidth An Integer for the number of characters a large column should be. (For Name columns).
     * @return A stitched String with all columns combined together.
     */
    private String combineColumns(ArrayList<String> strings, int smallColumnWidth, int largeColumnWidth) {
        StringBuilder result = new StringBuilder();
        ArrayList<String[]> splitArrays = splitNewLines(strings);
        int maxNumLines = getMaxLines(splitArrays);

        for (int currLine = 0; currLine < maxNumLines; currLine++) {
            iterateColumn(result, splitArrays, currLine, smallColumnWidth, largeColumnWidth);
        }

        return result.toString();
    }




    /**
     * A method that converts each String into a String array containing substrings that are split by "\n".
     * @param strings An ArrayList of strings.
     * @return An ArrayList of String arrays, which contain resulting substrings after split by "\n".
     */
    private ArrayList<String[]> splitNewLines(ArrayList<String> strings) {
        ArrayList<String[]> result = new ArrayList<>();
        for (String string : strings) {
            String[] curr = string.split("\n");
            result.add(curr);
        }
        return result;
    }

    /**
     * Takes in a list of String arrays {@code splitStrings} and returns the maximum length of the String
     * array elements.
     */
    private int getMaxLines(ArrayList<String[]> splitStrings) {
        int maxLines = 0;
        for (String[] stringArray : splitStrings) {
            maxLines = Math.max(maxLines, stringArray.length);
        }
        return maxLines;
    }

}
