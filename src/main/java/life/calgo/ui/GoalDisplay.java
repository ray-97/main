package life.calgo.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the stack pane that displays the daily calorie goal of the user.
 */
public class GoalDisplay extends UiPart<Region> {
    private static final String FXML = "GoalDisplay.fxml";

    @FXML
    private TextArea goalDisplay;

    public GoalDisplay() {
        super(FXML);
    }

    public void setGoalOfUser(String goalOfUser) {
        requireNonNull(goalOfUser);
        goalDisplay.setText(goalOfUser);
    }
}
