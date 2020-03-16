package f11_1.calgo.ui;

import f11_1.calgo.model.food.ConsumedFood;
import f11_1.calgo.model.food.Food;
import javafx.scene.layout.Region;

public class ConsumedFoodCard extends UiPart<Region> {

    private static final String FXML = "DailyListCard.fxml";

    public final ConsumedFood consumedFood;

    public ConsumedFoodCard(ConsumedFood consumedFood, int displayedIndex) {
        super((FXML));
        this.consumedFood = consumedFood;

    }

}
