package life.calgo.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

/**
 * Responsible for creating a label to display the rating of a food inside Daily Food List.
 */
public class RatingLabel {
    private StackPane portionPlaceholder = new StackPane();
    private Circle circle = new Circle();
    private Label ratingLabel = new Label();

    public RatingLabel(String rating) {
        try {
            double ratingInDouble = Double.parseDouble(rating);
            ratingLabel.setText(rating);
            if (ratingInDouble < 4.0) {
                circle.getStyleClass().add("ratingCircleFilledBad");
            } else if (ratingInDouble > 4.0 && ratingInDouble < 6.0) {
                circle.getStyleClass().add("ratingCircleFilledAverage");
            } else {
                circle.getStyleClass().add("ratingCircleFilledGood");
            }
        } catch (Exception e) {
            ratingLabel.setText("NA");
            circle.getStyleClass().add("ratingCircleEmpty");
        }

        ratingLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 11; -fx-text-fill: black;");
        circle.setRadius(13.0);
        portionPlaceholder.getChildren().addAll(circle, ratingLabel);
    }

    public StackPane getPane() {
        return portionPlaceholder;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RatingLabel // instanceof handles nulls
                && (ratingLabel.getText()
                .equals(((RatingLabel) other).ratingLabel.getText()))); // state check
    }
}
