package life.calgo.model.food;

import java.time.LocalDate;

public class ConsumedFood extends Food {
//fOR zx
    private final LocalDate date;
    private final double portion;

    public ConsumedFood(Food food, double portion, LocalDate date) {
        super(food.getName(), food.getCalorie(), food.getProtein(), food.getCarbohydrate(),
                food.getFat(), food.getTags());
        this.date = date;
        this.portion = portion;
    }

    public double getPortion() {
        return this.portion;
    }

    public LocalDate getDate() {
        return this.date;
    }
    // getDateAsString method?
}
