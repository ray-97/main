package f11_1.calgo.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import f11_1.calgo.logic.commands.EditCommand.EditFoodDescriptor;
import f11_1.calgo.model.food.Fat;
import f11_1.calgo.model.food.Protein;
import f11_1.calgo.model.food.Food;
import f11_1.calgo.model.food.Name;
import f11_1.calgo.model.food.Calorie;
import f11_1.calgo.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditFoodDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditFoodDescriptor();
    }

    public EditPersonDescriptorBuilder(EditFoodDescriptor descriptor) {
        this.descriptor = new EditFoodDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Food food) {
        descriptor = new EditFoodDescriptor();
        descriptor.setName(food.getName());
        descriptor.setCalorie(food.getCalorie());
        descriptor.setFat(food.getFat());
        descriptor.setProtein(food.getProtein());
        descriptor.setTags(food.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setCalorie(new Calorie(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setFat(new Fat(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setProtein(new Protein(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditFoodDescriptor build() {
        return descriptor;
    }
}
