package life.calgo.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import life.calgo.model.FoodRecord;
import life.calgo.model.food.Food;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalFoodItems {

    public static final Food ALMOND = new FoodBuilder().withName("Almond")
            .withCalorie("95")
            .withProtein("20")
            .withCarbohydrate("20")
            .withFat("10")
            .withTags("fruit").build();
    public static final Food BANANA_MILKSHAKE = new FoodBuilder().withName("Banana Milkshake")
            .withCalorie("200")
            .withProtein("10")
            .withCarbohydrate("40")
            .withFat("15")
            .withTags("dairy").build();
    public static final Food CHOCOLATE_BAR = new FoodBuilder().withName("Chocolate Bar")
            .withCalorie("300")
            .withProtein("12")
            .withCarbohydrate("90")
            .withFat("30")
            .withTags("snack").build();
    public static final Food DUCK_RICE = new FoodBuilder().withName("Duck Rice")
            .withCalorie("238")
            .withProtein("20")
            .withCarbohydrate("120")
            .withFat("20")
            .withTags("favourite").build();
    public static final Food EGG_OMELETTE = new FoodBuilder().withName("Egg Omelette")
            .withCalorie("233")
            .withProtein("25")
            .withCarbohydrate("10")
            .withFat("14").build();
    public static final Food FISH_AND_CHIPS = new FoodBuilder().withName("Fish and Chips")
            .withCalorie("400")
            .withProtein("30")
            .withCarbohydrate("20")
            .withFat("40").build();
    public static final Food GRANOLA = new FoodBuilder().withName("Granola")
            .withCalorie("300")
            .withProtein("10")
            .withCarbohydrate("40")
            .withFat("3").build();

    // Manually added - Food's details found in {@code CommandTestUtil}
    public static final Food APPLE = new FoodBuilder().withName("Apple")
            .withCalorie("100")
            .withProtein("3")
            .withCarbohydrate("40")
            .withFat("1").build();
    public static final Food BANANA = new FoodBuilder().withName("Banana")
            .withCalorie("200")
            .withProtein("1")
            .withCarbohydrate("50")
            .withFat("12").build();

    // Manually added for other testing purposes
    public static final Food ZINGER_BURGER = new FoodBuilder().withName("Zinger Burger")
            .withCalorie("1200")
            .withProtein("33")
            .withCarbohydrate("400")
            .withFat("12").build();
    public static final Food YELLOW_SAUCE = new FoodBuilder().withName("Yellow Sauce")
            .withCalorie("20000")
            .withProtein("111")
            .withCarbohydrate("520")
            .withFat("132").build();
    public static final Food RAINBOW_SAUCE = new FoodBuilder().withName("Rainbow Sauce")
            .withCalorie("30000")
            .withProtein("1234")
            .withCarbohydrate("6969")
            .withFat("20").build();

    private TypicalFoodItems() {} // prevents instantiation

    /**
     * Returns an {@code FoodRecord} with all the typical persons.
     */
    public static FoodRecord getTypicalFoodRecord() {
        FoodRecord ab = new FoodRecord();
        for (Food food : getTypicalFoodItems()) {
            ab.addFood(food);
        }
        return ab;
    }

    /**
     * Returns a list with all the typical persons.
     */
    public static List<Food> getTypicalFoodItems() {
        return new ArrayList<>(Arrays.asList(
                ALMOND, BANANA_MILKSHAKE, CHOCOLATE_BAR,
                DUCK_RICE, EGG_OMELETTE, FISH_AND_CHIPS, GRANOLA));
    }

}
