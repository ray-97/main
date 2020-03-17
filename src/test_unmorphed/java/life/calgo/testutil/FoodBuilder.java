package f11_1.calgo.testutil;

import java.util.HashSet;
import java.util.Set;

import f11_1.calgo.model.food.Calorie;
import f11_1.calgo.model.food.Carbohydrate;
import f11_1.calgo.model.food.Fat;
import f11_1.calgo.model.food.Protein;
import f11_1.calgo.model.food.Food;
import f11_1.calgo.model.food.Name;
import f11_1.calgo.model.tag.Tag;
import f11_1.calgo.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class FoodBuilder {

    public static final String DEFAULT_NAME = "Test Food";
    public static final String DEFAULT_CALORIE = "1230";
    public static final String DEFAULT_PROTEIN = "123";
    public static final String DEFAULT_CARBOHYDRATE = "456";
    public static final String DEFAULT_FAT = "789";

    private Name name;
    private Calorie calorie;
    private Protein protein;
    private Carbohydrate carbohydrate;
    private Fat fat;
    private Set<Tag> tags;

    public FoodBuilder() {
        name = new Name(DEFAULT_NAME);
        calorie = new Calorie(DEFAULT_CALORIE);
        protein = new Protein(DEFAULT_PROTEIN);
        carbohydrate = new Carbohydrate(DEFAULT_CARBOHYDRATE);
        fat = new Fat(DEFAULT_FAT);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public FoodBuilder(Food foodToCopy) {
        name = foodToCopy.getName();
        calorie = foodToCopy.getCalorie();
        protein = foodToCopy.getProtein();
        carbohydrate = foodToCopy.getCarbohydrate();
        fat = foodToCopy.getFat();
        tags = new HashSet<>(foodToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public FoodBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public FoodBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Calorie} of the {@code Food} that we are building.
     */
    public FoodBuilder withCalorie(String calorie) {
        this.calorie = new Calorie(calorie);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public FoodBuilder withProtein(String protein) {
        this.protein = new Protein(protein);
        return this;
    }

    /**
     * Sets the {@code Carbohydrate} of the {@code Food} that we are building.
     */
    public FoodBuilder withCarbohydrate(String carbohydrate) {
        this.carbohydrate = new Carbohydrate(carbohydrate);
        return this;
    }

    /**
     * Sets the {@code Fat} of the {@code Food} that we are building.
     */
    public FoodBuilder withFat(String fat) {
        this.fat = new Fat(fat);
        return this;
    }


    public Food build() {
        return new Food(name, calorie, protein, carbohydrate, fat, tags);
    }

}
