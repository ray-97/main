package life.calgo.testutil;

import static life.calgo.logic.parser.CliSyntax.PREFIX_PROTEIN;
import static life.calgo.logic.parser.CliSyntax.PREFIX_FAT;
import static life.calgo.logic.parser.CliSyntax.PREFIX_CARBOHYDRATE;
import static life.calgo.logic.parser.CliSyntax.PREFIX_NAME;
import static life.calgo.logic.parser.CliSyntax.PREFIX_CALORIES;
import static life.calgo.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import life.calgo.logic.commands.UpdateCommand;
import life.calgo.model.food.Food;
import life.calgo.model.tag.Tag;

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

    // not needed no more EditCommand
//    /**
//     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
//     */
//    public static String getEditPersonDescriptorDetails(EditFoodDescriptor descriptor) {
//        StringBuilder sb = new StringBuilder();
//        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
//        descriptor.getCalorie().ifPresent(phone -> sb.append(PREFIX_CALORIES).append(phone.value).append(" "));
//        descriptor.getFat().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
//        descriptor.getProtein().ifPresent(address -> sb.append(PREFIX_PROTEIN).append(address.value).append(" "));
//        if (descriptor.getTags().isPresent()) {
//            Set<Tag> tags = descriptor.getTags().get();
//            if (tags.isEmpty()) {
//                sb.append(PREFIX_TAG);
//            } else {
//                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
//            }
//        }
//        return sb.toString();
//    }
}
