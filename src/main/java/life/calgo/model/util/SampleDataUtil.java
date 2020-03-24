package life.calgo.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import life.calgo.model.FoodRecord;
import life.calgo.model.ReadOnlyFoodRecord;
import life.calgo.model.food.Calorie;
import life.calgo.model.food.Carbohydrate;
import life.calgo.model.food.Fat;
import life.calgo.model.food.Food;
import life.calgo.model.food.Name;
import life.calgo.model.food.Protein;
import life.calgo.model.tag.Tag;

/**
 * Contains utility methods for populating {@code FoodRecord} with sample data.
 */
public class SampleDataUtil {
    public static Food[] getSampleFoods() {
        return new Food[] {
            new Food(new Name("Hainanese Chicken Rice"), new Calorie("389"),
                new Protein("11"), new Carbohydrate("38"), new Fat("21"),
                getTagSet("Meat")),
            new Food(new Name("Laksa"), new Calorie("499"),
                    new Protein("25"), new Carbohydrate("58"),
                    new Fat("18"), getTagSet("Spicy")),
            new Food(new Name("Char Kuay Teow"), new Calorie("742"),
                    new Protein("23"), new Carbohydrate("76"), new Fat("38"),
                    getTagSet("GuiltFood")),
            new Food(new Name("Roti Prata"), new Calorie("212"),
                    new Protein("0"), new Carbohydrate("26"), new Fat("10"),
                    getTagSet("Breakfast")),
            new Food(new Name("Mee Rebus"), new Calorie("571"),
                    new Protein("23"), new Carbohydrate("82"), new Fat("17"),
                    getTagSet("Kampung")),
            new Food(new Name("Bandung"), new Calorie("150"),
                    new Protein("4"), new Carbohydrate("17"), new Fat("9"),
                    getTagSet())
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
