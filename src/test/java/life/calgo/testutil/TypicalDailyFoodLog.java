package life.calgo.testutil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.food.Food;

/**
 * Utility class for DailyFoodLog related tests.
 */
public class TypicalDailyFoodLog {

    private static final LocalDate CURRENT_DATE = LocalDate.now();

    public static final DailyFoodLog DAILY_FOOD_LOG_TODAY =
            new DailyFoodLog(buildFoods(), buildRatings(), CURRENT_DATE);

    public static DailyFoodLog getAppleOnlyLog() {
        DailyFoodLog appleOnlyFoodLog = new DailyFoodLog().consume(TypicalFoodItems.APPLE, 1);
        return appleOnlyFoodLog;
    }

    /**
     * Helper method to create food to portion map needed to build DailyFoodLog.
     */
    public static LinkedHashMap<Food, Double> buildFoods() {
        LinkedHashMap<Food, Double> foods = new LinkedHashMap<>();
        foods.put(TypicalFoodItems.ALMOND, 3.0);
        foods.put(TypicalFoodItems.BANANA, 6.9);
        foods.put(TypicalFoodItems.BANANA_MILKSHAKE, 1.0);
        foods.put(TypicalFoodItems.DUCK_RICE, 1.0);
        foods.put(TypicalFoodItems.YELLOW_SAUCE, 2.4);
        return foods;
    }

    /**
     * Helper method to create food to ratings map needed to build DailyFoodLog.
     */
    public static LinkedHashMap<Food, ArrayList<Integer>> buildRatings() {
        LinkedHashMap<Food, ArrayList<Integer>> ratings = new LinkedHashMap<>();
        ArrayList<Integer> ratingsArrayList = new ArrayList<>();

        ratingsArrayList.add(3);
        ratingsArrayList.add(4);
        ratings.put(TypicalFoodItems.ALMOND, new ArrayList<>(ratingsArrayList));

        ratingsArrayList.clear();
        ratingsArrayList.add(6);
        ratingsArrayList.add(9);
        ratings.put(TypicalFoodItems.BANANA, new ArrayList<>(ratingsArrayList));

        ratingsArrayList.clear();
        ratingsArrayList.add(8);
        ratings.put(TypicalFoodItems.BANANA_MILKSHAKE, new ArrayList<>(ratingsArrayList));

        ratingsArrayList.add(0, 8);
        ratings.put(TypicalFoodItems.DUCK_RICE, new ArrayList<>(ratingsArrayList));

        ratingsArrayList.set(1, 10);
        ratings.put(TypicalFoodItems.YELLOW_SAUCE, new ArrayList<>(ratingsArrayList));

        return ratings;
    }

}
