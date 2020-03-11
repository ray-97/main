package f11_1.calgo.testutil;

import f11_1.calgo.model.FoodRecord;
import f11_1.calgo.model.food.Food;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private FoodRecord foodRecord;

    public AddressBookBuilder() {
        foodRecord = new FoodRecord();
    }

    public AddressBookBuilder(FoodRecord foodRecord) {
        this.foodRecord = foodRecord;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Food food) {
        foodRecord.addFood(food);
        return this;
    }

    public FoodRecord build() {
        return foodRecord;
    }
}
