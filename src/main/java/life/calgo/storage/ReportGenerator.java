package life.calgo.storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import life.calgo.commons.core.LogsCenter;
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
    private static final int FOOD_COLUMN_NUMBER = 0;
    private static final String COLUMN_INTERVAL = "|";
    private static final String AGGREGATE_STATISTICS_FORMAT = "\t\t %-" + VALUE_COLUMN_WIDTH + ".0f "
            + "%-" + VALUE_COLUMN_WIDTH + ".0f "
            + "%-" + VALUE_COLUMN_WIDTH + ".0f "
            + "%-" + VALUE_COLUMN_WIDTH + ".0f ";

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
            + "Keep up the great work and you will definitely make tremendous\n"
            + "improvements in your health and fitness.";

    private static final String GOAL_FAILED_MESSAGE = "You did not manage to achieve your goal today. "
            + "You may want to re-design your diet plan so that you can make\n"
            + "improvements in your health and fitness! Check out Calgo's suggestions in the next section.";

    private static final String GOAL_SURPLUS_MESSAGE = "You have consumed %.0f fewer calories than your target. "
            + "Great job!";

    private static final String GOAL_DEFICIT_MESSAGE = "You have consumed %.0f more calories than your target. "
            + "Don't lose heart. You can do better!";

    // for Suggestions
    private static final String NO_GOAL_SUGGESTIONS_MESSAGE = "Since you did not set a daily calorie goal yet, Calgo"
            + " cannot advice you on whether\n" + "%s is suitable for your diet.";

    private static final String SUGGESTIONS_HEADER_MESSAGE = "Suggestions for You";

    private static final String FAVOURITE_FOOD_MESSAGE = "Your favourite food in the past week "
            + "(based on a mix of ratings and portions consumed) has been:\n"
            + "%s.";

    private static final String ADVICE_TO_ABSTAIN = "Unfortunately, after evaluating your daily goal, "
            + "Calgo advices you not to eat %s at all because it has \n way too many calories relative to your goal.";

    private static final String ADVICE_TO_CONTINUE = "Based on your goal, Calgo has verified that your favourite food,"
            + " %s,\n is sufficiently healthy! This means that it can continue to be a part of your diet.";

    private static final String ADVICE_TO_EXERCISE = "Calgo has verified that your favourite food is preventing you "
            + "from reaching your daily goal.\n"
            + "If you do eat %s, you may want to exercise to burn off those excess calories!\n"
            + "A jog around your neighbourhood sounds like a good idea! Don't you agree?";

    // for Footer

    private static final String FOOTER_MESSAGE = "This marks the end of your report";

    // Attributes

    private DailyGoal userGoal;
    private DailyFoodLog queryLog;
    private ArrayList<DailyFoodLog> pastWeekLogs;


    // Statistics

    private double totalCalories = 0.0;
    private double totalProteins = 0.0;
    private double totalCarbs = 0.0;
    private double totalFats = 0.0;

    public ReportGenerator(LocalDate queryDate, DailyGoal userGoal, DailyFoodLog queryLog,
                           ArrayList<DailyFoodLog> pastWeekLogs) {
        super("data/reports/" + queryDate.toString() + "_report.txt",
                LogsCenter.getLogger(ReportGenerator.class));

        this.pastWeekLogs = pastWeekLogs;
        this.queryLog = queryLog;
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
        printSuggestions();
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
     * Writes information on what the goal is.
     */
    private void printGoalInformation() {
        printGoalInformationHeader();
        printGoalInformationBody();
        printSeparator();
    }

    /**
     * Writes goal information section header.
     */
    private void printGoalInformationHeader() {
        printWriter.println(centraliseText(GOAL_HEADER_MESSAGE, DOCUMENT_WIDTH));
        printEmptyLine();
    }

    /**
     * Writes goal information section body.
     */
    private void printGoalInformationBody() {
        String goalInformation;
        if (userGoal.getGoal() == DailyGoal.DUMMY_VALUE) {
            goalInformation = NO_GOAL_MESSAGE;
        } else {
            goalInformation = String.format(GOAL_MESSAGE, this.userGoal.getGoal());
        }
        printWriter.println(goalInformation);
    }

    /**
     * Writes relevant statistics related to each food quantity consumed in the given day.
     */
    private void printFoodwiseStatistics() {
        printFoodwiseStatisticsHeader();
        printFoodwiseStatisticsTable();
        printSeparator();
    }

    /**
     * Writes the header of the Foodwise Statistics section.
     */
    private void printFoodwiseStatisticsHeader() {
        printWriter.println(centraliseText(FOODWISE_HEADER_MESSAGE, DOCUMENT_WIDTH));
        printEmptyLine();
    }

    /**
     * Writes the table of the Foodwise Statistics section.
     */
    private void printFoodwiseStatisticsTable() {
        printFoodwiseStatisticsTableHeaders();
        printFoodwiseStatisticsTableData();
    }

    /**
     * Writes the headers of the table in the Foodwise Statistics section.
     */
    private void printFoodwiseStatisticsTableHeaders() {
        String foodHeader = centraliseText("Food", NAME_COLUMN_WIDTH) + COLUMN_INTERVAL;
        String portionHeader = centraliseText("Total Quantity", VALUE_COLUMN_WIDTH) + COLUMN_INTERVAL;
        String caloriesHeader = centraliseText("Total Calories", VALUE_COLUMN_WIDTH);

        printWriter.println(foodHeader + portionHeader + caloriesHeader);
        printEmptyLine();
    }

    /**
     * Writes the main information in the table of the Foodwise Statistics section.
     */
    private void printFoodwiseStatisticsTableData() {
        for (Food food : queryLog.getFoods()) {
            double portion = queryLog.getPortion(food);
            double currCalories = portion * (double) Integer.parseInt(food.getCalorie().value);

            // wrap name in the case it is too long
            String foodColumn = generateWrappedNameString(food.getName(), NAME_COLUMN_WIDTH);
            // portion and calories do not need to be wrapped
            String portionColumn = centraliseText(String.format("%.1f", portion), VALUE_COLUMN_WIDTH);
            String currCaloriesColumn = centraliseText(String.format("%.0f", currCalories), VALUE_COLUMN_WIDTH);

            // create a list of columns
            ArrayList<String> columns = new ArrayList<>();
            columns.add(foodColumn);
            columns.add(portionColumn);
            columns.add(currCaloriesColumn);

            // horizontally stitch the columns to form a table
            String table = combineColumns(columns);

            printWriter.println(table);
        }
    }

    /**
     * Writes the Aggregated Statistics section.
     */
    private void printAggregateStatistics() {
        printAggregateStatisticsHeader();
        printAggregateStatisticsTable();
        printSeparator();
    }

    /**
     * Writes the header of the Aggregate Statistics section.
     */
    private void printAggregateStatisticsHeader() {
        printWriter.println(centraliseText(AGGREGATE_HEADER_MESSAGE, DOCUMENT_WIDTH));
        printEmptyLine();
    }

    /**
     * Writes the table of the Aggregate Statistics section.
     */
    private void printAggregateStatisticsTable() {
        printAggregateStatisticsTableHeaders();
        printAggregateStatisticsTableData();
    }

    /**
     * Writes the headers of the table in the Aggregate Statistics section.
     */
    private void printAggregateStatisticsTableHeaders() {
        String caloriesHeader = centraliseText("Total Calories in kcal", VALUE_COLUMN_WIDTH) + COLUMN_INTERVAL;
        String proteinHeader = centraliseText("Total Protein in grams", VALUE_COLUMN_WIDTH) + COLUMN_INTERVAL;
        String carbsHeader = centraliseText("Total Carbohydrates in grams", VALUE_COLUMN_WIDTH) + COLUMN_INTERVAL;
        String fatsHeader = centraliseText("Total Fats in grams", VALUE_COLUMN_WIDTH);

        printWriter.println(caloriesHeader + proteinHeader + carbsHeader + fatsHeader);
        printEmptyLine();
    }

    /**
     * Writes the main information in the table of the Aggregate Statistics section.
     */
    private void printAggregateStatisticsTableData() {
        printWriter.println(String.format(AGGREGATE_STATISTICS_FORMAT, totalCalories, totalProteins,
                totalCarbs, totalFats));
    }
    /**
     * Writes the actionable insights a user can take based on user consumption patterns for the given day.
     */
    private void printInsights() {
        // Guard clause: No goal provided -> No insights to print
        if (!isGoalSet()) {
            return;
        }

        // Happy path:
        printInsightsHeader();

        double remainingCalories = calculateRemainingCalories();
        boolean isGoalAchieved = Double.compare(remainingCalories, 0.0) >= 0;
        printInsightsBody(remainingCalories, isGoalAchieved);

        printSeparator();
    }

    /**
     * Writes the header of the Insights section.
     */
    private void printInsightsHeader() {
        printWriter.println(centraliseText(INSIGHTS_HEADER_MESSAGE, DOCUMENT_WIDTH));
        printEmptyLine();
    }

    /**
     * Writes the main information of the Insights section.
     */
    private void printInsightsBody(double remainingCalories, boolean isGoalAchieved) {
        if (isGoalAchieved) {
            printWriter.println(GOAL_ACHIEVED_MESSAGE);
            printEmptyLine();
            printWriter.println(String.format(GOAL_SURPLUS_MESSAGE, remainingCalories));
        } else {
            printWriter.println(GOAL_FAILED_MESSAGE);
            printEmptyLine();
            printWriter.println(String.format(GOAL_DEFICIT_MESSAGE, Math.abs(remainingCalories)));
        }
    }

    /**
     * Creates a list of recommended food items to eat that will match goal of user.
     */
    private void printSuggestions() {
        printSuggestionsHeader();
        printSuggestionsBody();
        printSeparator();
    }

    /**
     * Writes the header of the Suggestions section.
     */
    private void printSuggestionsHeader() {
        printWriter.println(centraliseText(SUGGESTIONS_HEADER_MESSAGE, DOCUMENT_WIDTH));
        printEmptyLine();
    }

    /**
     * Writes the main information of the Suggestions section.
     */
    private void printSuggestionsBody() {

        HashMap<Food, double[]> foodInPastWeek = getPortionAndRatings(pastWeekLogs);

        Food favouriteFood = getHighestCalorieFavouriteFood(foodInPastWeek);
        String favouriteFoodName = favouriteFood.getFoodNameString();

        printWriter.println(String.format(FAVOURITE_FOOD_MESSAGE, favouriteFoodName));
        printEmptyLine();

        int favouriteFoodCalories = Integer.parseInt(favouriteFood.getCalorie().value);
        int difference = userGoal.getGoal() - favouriteFoodCalories;

        // if Goal is not set, cannot form personalised suggestions
        if (!isGoalSet()) {
            printWriter.println(String.format(NO_GOAL_SUGGESTIONS_MESSAGE, favouriteFood.getFoodNameString()));
            return;
        }

        if (difference < 0) {
            printWriter.println(String.format(ADVICE_TO_ABSTAIN, favouriteFoodName));
        } else if (isSufficientlyHealthy(favouriteFoodCalories)) {
            printWriter.println(String.format(ADVICE_TO_CONTINUE, favouriteFoodName));
        } else {
            printWriter.println(String.format(ADVICE_TO_EXERCISE, favouriteFoodName));
        }
    }


    // String Manipulation Methods

    /**
     * Combines columns to form a table. Goes line by line.
     *
     * @param columns An ArrayList of Strings, where each element is a column.
     * @return A stitched String with all columns combined together.
     */
    private String combineColumns(ArrayList<String> columns) {

        StringBuilder result = new StringBuilder();
        ArrayList<String[]> splitArrays = splitNewLines(columns); // split each column into lines
        int maxNumLines = getMaxLines(splitArrays); // get the number of lines of the longest column

        // line by line, stitch each column together
        for (int currLine = 0; currLine < maxNumLines; currLine++) {
            iterateColumn(result, splitArrays, currLine);
        }

        return result.toString();
    }

    /**
     * A method that splits each column String into lines, represented in a String.
     * @param columns An ArrayList of strings.
     * @return An ArrayList of String arrays, which contain resulting substrings after split by "\n".
     */
    private ArrayList<String[]> splitNewLines(ArrayList<String> columns) {
        ArrayList<String[]> result = new ArrayList<>();
        for (String string : columns) {
            String[] curr = string.split("\n");
            result.add(curr);
        }
        return result;
    }

    /**
     * @param sb A StringBuilder object.
     * @param columns An ArrayList of String Arrays (columns) that contain respective lines of data.
     * @param currLine The current line number that is being iterated through.
     */
    private void iterateColumn(StringBuilder sb, ArrayList<String[]> columns, int currLine) {
        int numColumns = columns.size();
        for (int column = 0; column < numColumns; column++) {
            String[] currColumn = columns.get(column);
            int columnWidth = VALUE_COLUMN_WIDTH;

            if (column == FOOD_COLUMN_NUMBER) {
                columnWidth = NAME_COLUMN_WIDTH;
            }

            if (currLine < currColumn.length) {
                String currText = currColumn[currLine];
                sb.append(addNTrailingWhitespace(currText, columnWidth - currText.length() + 1));
            } else {
                sb.append(addNTrailingWhitespace("", columnWidth));
            }

            // if last column, go to next line
            if (column == numColumns - 1) {
                sb.append("\n");
            }
        }
    }

    // Utility Methods

    /**
     * Takes in a list of String arrays {@code columnLines} and returns the maximum length of the String
     * array elements.
     */
    private int getMaxLines(ArrayList<String[]> columnLines) {
        int maxLines = 0;
        for (String[] stringArray : columnLines) {
            maxLines = Math.max(maxLines, stringArray.length);
        }
        return maxLines;
    }

    /**
     * Calculates number of calories remaining for user to meet goal.
     *
     * @return the number of calories remaining for user to meet goal
     */
    private double calculateRemainingCalories() {
        return ((double) userGoal.getGoal()) - totalCalories;
    }

    /**
     * Checks if goal is provided by user.
     */
    private boolean isGoalSet() {
        return userGoal.getGoal() != DailyGoal.DUMMY_VALUE;
    }

    /**
     * Updates Map object with number of portions and ratings of every Food object consumed in past 7 days.
     */
    private HashMap<Food, double[]> getPortionAndRatings(ArrayList<DailyFoodLog> weeklyLogs) {
        HashMap<Food, double[]> result = new HashMap<>();
        for (DailyFoodLog foodLog : weeklyLogs) {
            updateAllPortionAndRatings(result, foodLog);
        }
        return result;
    }

    /**
     * Updates all portions and all ratings of all food items from a specific DailyFoodLog.
     */
    private void updateAllPortionAndRatings(HashMap<Food, double[]> foodHashMap, DailyFoodLog foodLog) {
        for (Food food : foodLog.getFoods()) {
            updateFoodPortionsAndRatings(foodHashMap, foodLog, food);
        }
    }

    /**
     * Updates portions and ratings of a specific Food item with the values from a specific DailyFoodLog.
     */
    private void updateFoodPortionsAndRatings(HashMap<Food, double[]> foodHashMap, DailyFoodLog foodLog, Food food) {
        double[] data = new double[2];
        if (foodHashMap.containsKey(food)) {
            data = foodHashMap.get(food);
            double oldPortion = data[0];
            double newPortion = oldPortion + foodLog.getPortion(food);
            double rating = foodLog.getRating(food);

            data[0] = newPortion;
            if (rating != DailyFoodLog.RATING_DUMMY_VALUE) {
                data[1] = (foodLog.getRating(food) * oldPortion + foodLog.getRating(food)) / newPortion;
            }
        } else {
            double rating = foodLog.getRating(food);
            if (rating != DailyFoodLog.RATING_DUMMY_VALUE) {
                data[1] = foodLog.getRating(food);
            }
            data[0] = foodLog.getPortion(food);
        }

        foodHashMap.put(food, data);
    }

    /**
     * Compares two Food objects based on ratings and portions consumed.
     *
     * @param f1 Food object one.
     * @param f2 Food object two.
     * @param foodInPastWeek A Map of all Food objects consumed in the past week and their [portions, ratings].
     * @return -1 if f1 has > value than f2 and 1 otherwise.
     */
    private int compare(Food f1, Food f2, HashMap<Food, double[]> foodInPastWeek) {
        // if both foods have a valid rating, compare by rating
        double ratingF1 = foodInPastWeek.get(f1)[1];
        double ratingF2 = foodInPastWeek.get(f2)[1];
        boolean haveRatings = ratingF1 != 0.0 && ratingF2 != 0.0;

        if (haveRatings) {
            return ratingF2 - ratingF1 < 0 ? -1 : 1;
        }

        // else compare by rating
        double portionF1 = foodInPastWeek.get(f1)[0];
        double portionF2 = foodInPastWeek.get(f2)[0];

        return portionF2 - portionF1 < 0 ? -1 : 1;
    }

    /**
     * Returns favourite food item of past 7 days based on portions consumed and ratings.
     */
    private Food getHighestCalorieFavouriteFood(HashMap<Food, double[]> foodHashMap) {
        ArrayList<Food> foodList = new ArrayList<>(foodHashMap.keySet());
        // in case all foods have same ratings and portion, 1st item should be the food with most calories.
        foodList.sort((Food f1, Food f2) ->
                Integer.parseInt(f2.getCalorie().value) - Integer.parseInt(f1.getCalorie().value));
        // compare with portions and ratings
        foodList.sort((Food f1, Food f2) -> compare(f1, f2, foodHashMap));
        return foodList.get(0);
    }

    /**
     * Returns whether specified Food object's calories is sufficiently healthy based on user goal.
     * A food is defined to be sufficiently healthy if it can be consumed 3 times a day without exceeding user goal.
     */
    private boolean isSufficientlyHealthy(int foodCalories) {
        return (foodCalories * 3) < userGoal.getGoal();
    }
}
