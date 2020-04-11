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
 * This is particularly useful for initialising the App on first time usage with some data to begin with.
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
                    getTagSet("Pink", "Sweet", "Favourite")),
            new Food(new Name("Black Forest Cake Slice"), new Calorie("340"),
                    new Protein("4"), new Carbohydrate("22"), new Fat("8"),
                    getTagSet("Amy", "Birthday")),
            new Food(new Name("Cheeseburger"), new Calorie("450"),
                    new Protein("22"), new Carbohydrate("27"), new Fat("16"),
                    getTagSet("Restaurant")),
            new Food(new Name("Chicken Cutlet Meal"), new Calorie("650"),
                    new Protein("30"), new Carbohydrate("33"), new Fat("19"),
                    getTagSet("Whampoa", "Hawker")),
            new Food(new Name("Fried Chicken Wing"), new Calorie("175"),
                    new Protein("8"), new Carbohydrate("4"), new Fat("4"),
                    getTagSet("Delicious", "Jumbo")),
            new Food(new Name("Pineapple Chicken Rice"), new Calorie("589"),
                    new Protein("7"), new Carbohydrate("35"), new Fat("8"),
                    getTagSet("Sweet")),
            new Food(new Name("Strawberry Milk"), new Calorie("200"),
                    new Protein("6"), new Carbohydrate("17"), new Fat("3"),
                    getTagSet("Sweet", "Pink")),
            new Food(new Name("Sambal Chilli"), new Calorie("100"),
                    new Protein("1"), new Carbohydrate("6"), new Fat("3"),
                    getTagSet("Sauce", "sweet", "Best")),
            new Food(new Name("Hot Dog Bun"), new Calorie("150"),
                    new Protein("4"), new Carbohydrate("12"), new Fat("2"),
                    getTagSet("Bread", "Processed")),
            new Food(new Name("Cheesy Bites"), new Calorie("300"),
                    new Protein("8"), new Carbohydrate("12"), new Fat("5"),
                    getTagSet("Midnight")),
            new Food(new Name("Fried Bee Hoon"), new Calorie("300"),
                    new Protein("2"), new Carbohydrate("17"), new Fat("3"),
                    getTagSet("Grandma")),
            new Food(new Name("Irish Whiskey"), new Calorie("50"),
                    new Protein("0"), new Carbohydrate("3"), new Fat("1"),
                    getTagSet("Bros", "Fuel4Code", "getYourGameON")),
            new Food(new Name("Chocolate Biscuits"), new Calorie("170"),
                    new Protein("1"), new Carbohydrate("13"), new Fat("2"),
                    getTagSet("Favourite", "Chocolatey")),
            new Food(new Name("Salmon Creamy Linguine"), new Calorie("500"),
                    new Protein("10"), new Carbohydrate("27"), new Fat("9"),
                    getTagSet("Italian")),
            new Food(new Name("Stirfried Kai Lan in Oyster Sauce"), new Calorie("200"),
                    new Protein("0"), new Carbohydrate("12"), new Fat("0"),
                    getTagSet("Chinese"))
        };
    }

    public static ReadOnlyFoodRecord getSampleFoodRecord() {
        FoodRecord sampleFr = new FoodRecord();
        for (Food sampleFood : getSampleFoods()) {
            sampleFr.addFood(sampleFood);
        }
        return sampleFr;
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
