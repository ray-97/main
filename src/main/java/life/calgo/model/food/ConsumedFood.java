package life.calgo.model.food;

import java.time.LocalDate;

/**
 * To represent food objects that have been consumed by the user
 */
public class ConsumedFood extends Food {

    private final LocalDate date;
    private final double portion;
    private final double rating;

    public ConsumedFood(Food food, double portion, double rating, LocalDate date) {
        super(food.getName(), food.getCalorie(), food.getProtein(), food.getCarbohydrate(),
                food.getFat(), food.getTags());
        this.date = date;
        this.portion = portion;
        this.rating = rating;
    }

    public double getPortion() {
        return this.portion;
    }

    public String getRating() {
        return rating == -1 ? "Not available yet" : String.format("%.2f", rating) + "/10";
    }

    @Override
    public int compareTo(Food other) {
        ConsumedFood otherConsumedFood = (ConsumedFood) other;
        return Double.compare(this.rating, otherConsumedFood.rating);
    }

    public LocalDate getDate() {
        return this.date;
    }
}
