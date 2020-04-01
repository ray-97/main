package life.calgo.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import life.calgo.model.food.ConsumedFood;

/**
 * Responsible for displaying each consumed food item.
 */
public class ConsumedFoodCard extends UiPart<Region> {

    private static final String FXML = "DailyListCard.fxml";

    private static final String DATE_PATTERN = "E, dd-MMM-yyyy";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public final ConsumedFood consumedFood;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label portion;
    @FXML
    private Label totalCalorie;
    @FXML
    private Label averageRating;
    @FXML
    private Label date;

    public ConsumedFoodCard(ConsumedFood consumedFood, int displayedIndex) {
        super((FXML));
        this.consumedFood = consumedFood;
        id.setText(displayedIndex + ". ");
        name.setText(consumedFood.getName().fullName);
        portion.setText("Portions consumed: " + consumedFood.getPortion());
        double calorieFromPortions = consumedFood.getPortion() * Double.parseDouble(consumedFood.getCalorie().value);
        averageRating.setText("Average rating: " + consumedFood.getRating());
        totalCalorie.setText("Total calories: " + calorieFromPortions);
        date.setText("Date: " + formatter.format(consumedFood.getDate()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ConsumedFoodCard)) {
            return false;
        }

        // state check
        ConsumedFoodCard card = (ConsumedFoodCard) other;
        return id.getText().equals(card.id.getText())
                && consumedFood.equals(card.consumedFood);
    }

}
