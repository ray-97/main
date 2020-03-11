package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Carbohydrate;
import seedu.address.model.person.Fat;
import seedu.address.model.person.Protein;
import seedu.address.model.person.Food;
import seedu.address.model.person.Name;
import seedu.address.model.person.Calorie;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code FoodRecord} with sample data.
 */
public class SampleDataUtil {
    public static Food[] getSampleFoods() {
        return new Food[] {
            new Food(new Name("Apple"), new Calorie("125"),
                new Protein("0"), new Carbohydrate("20"), new Fat("2"),
                getTagSet("Favourite Fruit")),
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
                    getTagSet("Favourite Vegetable"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Food sampleFood : getSampleFoods()) {
            sampleAb.addPerson(sampleFood);
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
