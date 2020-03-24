package life.calgo.model.food;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import life.calgo.commons.util.CollectionUtil;
import life.calgo.model.tag.Tag;

/**
 * Represents a Food in the food record.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Food implements Comparable<Food> {

    // Identity field
    private final Name name;

    // Data fields
    private final Calorie calorie;
    private final Protein protein;
    private final Carbohydrate carbohydrate;
    private final Fat fat;

    // Fields that can be left empty by user
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Food(Name name, Calorie calorie, Protein protein, Carbohydrate carbohydrate, Fat fat, Set<Tag> tags) {
        CollectionUtil.requireAllNonNull(name, calorie, protein, carbohydrate, fat, tags);
        this.name = name;
        this.calorie = calorie;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.fat = fat;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Calorie getCalorie() {
        return calorie;
    }

    public Protein getProtein() {
        return protein;
    }

    public Carbohydrate getCarbohydrate() {
        return carbohydrate;
    }

    public Fat getFat() {
        return fat;
    }

    public Food copy() {
        return new Food(name, calorie, protein, carbohydrate, fat, tags);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both foods of the same name have the same name and calorie field.
     * This defines a weaker notion of equality between two foods.
     */
    public boolean isSameFood(Food otherFood) {
        if (otherFood == this) {
            return true;
        }

        return otherFood != null
                && otherFood.getName().equals(getName());
    }

    /**
     * Returns true if the current Food object has the same Name as the argument.
     *
     * @param name the name we want to check against the current Food object.
     * @return whether or not the current Food object has the same Name as the given.
     */
    public boolean hasName(Name name) {
        return name.equals(getName());
    }

    /**
     * Returns true if both foods have the same name and data fields.
     * This defines a stronger notion of equality between two foods.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Food)) {
            return false;
        }

        Food otherFood = (Food) other;
        return otherFood.getName().equals(getName())
                && otherFood.getCalorie().equals(getCalorie())
                && otherFood.getProtein().equals(getProtein())
                && otherFood.getCarbohydrate().equals(getCarbohydrate())
                && otherFood.getFat().equals(getFat())
                && otherFood.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, calorie, protein, carbohydrate, fat, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Calories: ")
                .append(getCalorie())
                .append(" Proteins (g): ")
                .append(getProtein())
                .append(" Carbohydrates (g): ")
                .append(getCarbohydrate())
                .append(" Fats (g): ")
                .append(getFat())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Overloaded toString method for report purposes.
     *
     * @param isCalledforReport whether or not current Food is involved in a Report.
     * @return String representation of the Food's Name.
     */
    public String toString(boolean isCalledforReport) {
        return this.getName().toString();
    }

    /**
     * Compares names of Food objects for lexicographic order.
     *
     * @param other the other Food.
     * @return a value representing the lexicopgraphic order.
     */
    @Override
    public int compareTo(Food other) {
        String currentName = this.getName().toString();
        String otherName = other.getName().toString();
        return currentName.compareTo(otherName);
    }

}
