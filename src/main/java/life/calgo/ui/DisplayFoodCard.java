package life.calgo.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import life.calgo.model.food.DisplayFood;

/**
 * Responsible for displaying each consumed food item.
 */
public class DisplayFoodCard extends UiPart<Region> {

    private static final String FXML = "DailyListCard.fxml";

    private static final String DATE_PATTERN = "E, dd-MMM-yyyy";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public final DisplayFood displayFood;

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

    public DisplayFoodCard(DisplayFood displayFood, int displayedIndex) {
        super((FXML));
        this.displayFood = displayFood;
        id.setText(displayedIndex + ". ");
        name.setText(displayFood.getName().fullName);
        portion.setText("Portions consumed: " + displayFood.getPortion());
        double calorieFromPortions = displayFood.getPortion() * Double.parseDouble(displayFood.getCalorie().value);
        averageRating.setText("Average rating: " + displayFood.getRating());
        totalCalorie.setText("Total calories: " + calorieFromPortions);
        date.setText("Date: " + formatter.format(displayFood.getDate()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DisplayFoodCard)) {
            return false;
        }

        // state check
        DisplayFoodCard card = (DisplayFoodCard) other;
        return id.getText().equals(card.id.getText())
                && displayFood.equals(card.displayFood);
    }

}
