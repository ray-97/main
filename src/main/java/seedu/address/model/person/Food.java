package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Food
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Food {

    // Identity fields
    private final Name name;


    // Data field
    private final int calories; // possible to create class if needed
    private final int protein; // possible to create class if needed
    private final int carbohydrate; // possible to create class if needed
    private final int fats; // possible to create class if needed
    private final double portion; // possible to create class if needed


    /**
     * Every field must be present and not null.
     */
    public Food(Name name, int calories, int protein, int carbohydrate, int fats, double portion) {
        requireAllNonNull(calories, protein, carbohydrate, fats, portion); // make sure all non null
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.fats = fats;
        this.portion = portion;
    }

    public Name getName() {
        return name;
    }


    public int getCalories() {
        return calories;
    }

    public int getProtein() {
        return protein;
    }

    public int getCarbohydrate() {
        return carbohydrate;
    }

    public int getFats() {
        return fats;
    }

    public double getPortion() {
        return portion;
    }


    /**
     * Returns true if both food items are of the same name
     */
    public boolean isSameFood(Food otherFood) {
        if (otherFood == this) {
            return true;
        }

        return otherFood != null
                && otherFood.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own (Person had this)
        return Objects.hash(name, calories, carbohydrate, protein, fats);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Protein: ")
                .append(getProtein())
                .append(" Carbohyrdrates: ")
                .append(getCarbohydrate())
                .append(" Fats: ")
                .append(getFats());
        return builder.toString();
    }

}
