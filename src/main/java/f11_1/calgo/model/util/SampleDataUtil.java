package f11_1.calgo.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import f11_1.calgo.model.food.Fat;
import f11_1.calgo.model.food.Protein;
import f11_1.calgo.model.tag.Tag;
import f11_1.calgo.model.FoodRecord;
import f11_1.calgo.model.ReadOnlyFoodRecord;
import f11_1.calgo.model.food.Carbohydrate;
import f11_1.calgo.model.food.Food;
import f11_1.calgo.model.food.Name;
import f11_1.calgo.model.food.Calorie;

/**
 * Contains utility methods for populating {@code FoodRecord} with sample data.
 */
public class SampleDataUtil {
    public static Food[] getSampleFoods() {
        return new Food[] {
            new Food(new Name("Apple"), new Calorie("125"),
                new Protein("0"), new Carbohydrate("20"), new Fat("2"),
                getTagSet("Fruit")),
            new Food(new Name("Grilled Chicken Chop"), new Calorie("400"),
                    new Protein("27"), new Carbohydrate("5"), new Fat("10"),
                    getTagSet("Western")),
            new Food(new Name("Sunny Side Up Egg"), new Calorie("100"),
                    new Protein("9"), new Carbohydrate("1"), new Fat("6"),
                    getTagSet("Breakfast")),
            new Food(new Name("Banana"), new Calorie("125"),
                    new Protein("0"), new Carbohydrate("18"), new Fat("5"),
                    getTagSet("Fruit")),
            new Food(new Name("White Rice"), new Calorie("250"),
                    new Protein("3"), new Carbohydrate("30"), new Fat("2"),
                    getTagSet("Staple")),
            new Food(new Name("Brocolli"), new Calorie("150"),
                    new Protein("3"), new Carbohydrate("16"), new Fat("1"),
                    getTagSet("Vegetable"))
        };
    }

    public static ReadOnlyFoodRecord getSampleFoodRecord() {
        FoodRecord sampleAb = new FoodRecord();
        for (Food sampleFood : getSampleFoods()) {
            sampleAb.addFood(sampleFood);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
