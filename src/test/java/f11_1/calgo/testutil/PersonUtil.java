package f11_1.calgo.testutil;

import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_PROTEIN;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_EMAIL;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_NAME;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_CALORIES;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import f11_1.calgo.logic.commands.AddCommand;
import f11_1.calgo.logic.commands.EditCommand.EditFoodDescriptor;
import f11_1.calgo.model.food.Food;
import f11_1.calgo.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Food food) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(food);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Food food) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + food.getName().fullName + " ");
        sb.append(PREFIX_CALORIES + food.getCalorie().value + " ");
        sb.append(PREFIX_EMAIL + food.getFat().value + " ");
        sb.append(PREFIX_PROTEIN + food.getProtein().value + " ");
        food.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditFoodDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getCalorie().ifPresent(phone -> sb.append(PREFIX_CALORIES).append(phone.value).append(" "));
        descriptor.getFat().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getProtein().ifPresent(address -> sb.append(PREFIX_PROTEIN).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
