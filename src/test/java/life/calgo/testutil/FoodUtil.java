package life.calgo.testutil;

import static life.calgo.logic.parser.CliSyntax.PREFIX_CALORIES;
import static life.calgo.logic.parser.CliSyntax.PREFIX_CARBOHYDRATE;
import static life.calgo.logic.parser.CliSyntax.PREFIX_FAT;
import static life.calgo.logic.parser.CliSyntax.PREFIX_NAME;
import static life.calgo.logic.parser.CliSyntax.PREFIX_PROTEIN;
import static life.calgo.logic.parser.CliSyntax.PREFIX_TAG;

import life.calgo.logic.commands.UpdateCommand;
import life.calgo.model.food.Food;

/**
 * A utility class for Person.
 */
public class FoodUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getUpdateCommand(Food food) {
        return UpdateCommand.COMMAND_WORD + " " + getPersonDetails(food);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Food food) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + food.getName().fullName + " ");
        sb.append(PREFIX_CALORIES + food.getCalorie().value + " ");
        sb.append(PREFIX_PROTEIN + food.getProtein().value + " ");
        sb.append(PREFIX_CARBOHYDRATE + food.getCarbohydrate().value + " ");
        sb.append(PREFIX_FAT + food.getFat().value + " ");
        food.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

}
