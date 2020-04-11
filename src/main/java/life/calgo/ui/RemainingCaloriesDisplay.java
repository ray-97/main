package life.calgo.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the stack pane that displays the remaining calories to be consumed by the user.
 */
public class RemainingCaloriesDisplay extends UiPart<Region> {
    private static final String FXML = "RemainingCaloriesDisplay.fxml";

    @FXML
    private TextArea remainingCaloriesDisplay;

    public RemainingCaloriesDisplay() {
        super(FXML);
    }

    public void setCaloriesOfUser(String userRemainingCalories) {
        requireNonNull(userRemainingCalories);
        remainingCaloriesDisplay.setText(userRemainingCalories);
    }
}

