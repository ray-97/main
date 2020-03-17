package f11_1.calgo.testutil;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import f11_1.calgo.model.FoodRecord;
import f11_1.calgo.model.food.Food;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalFoodItems {

    public static final Food APPLE = new FoodBuilder().withName("Apple")
            .withProtein("2").withFat("1")
            .withCalorie("100")
            .withTags("healthy").build();
    public static final Food BANANA = new FoodBuilder().withName("Banana")
            .withProtein("10")
            .withFat("2").withCalorie("200")
            .build();
    public static final Food CHOCOLATE = new FoodBuilder().withName("Chocolate")
            .withProtein("20")
            .withCalorie("300")
            .withFat("7")
            .build();

    private TypicalFoodItems() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
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
        return new ArrayList<>(Arrays.asList(APPLE, BANANA, CHOCOLATE));
    }

}
