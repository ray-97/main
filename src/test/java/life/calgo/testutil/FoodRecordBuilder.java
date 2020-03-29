package life.calgo.testutil;

import life.calgo.model.FoodRecord;
import life.calgo.model.food.Food;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class FoodRecordBuilder {

    private FoodRecord foodRecord;


    public FoodRecordBuilder() {
        foodRecord = new FoodRecord();
    }

    public FoodRecordBuilder(FoodRecord foodRecord) {
        this.foodRecord = foodRecord;
    }

    /**
     * Adds a new {@code Food} to the {@code FoodRecord} that we are building.
     */
    public FoodRecordBuilder withFood(Food food) {
        foodRecord.addFood(food);
        return this;
    }

    public FoodRecord build() {
        return foodRecord;
    }
}
